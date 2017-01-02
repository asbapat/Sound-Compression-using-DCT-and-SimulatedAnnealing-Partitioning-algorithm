import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
 
/** 
 * Node Details 
 */
class Node {
	   int no;
	   int computationalloadhw;
	   int computationalloadsw;
	   int partition;
	   boolean macUsage;
	   int  srammemory;
	   int  eprommemory;
	   int communicationweight;
	   public Node(int no, int cmpldhw, int cmpldsw, boolean macUsage, int srmem,int ermem,
			   int commweight) {
		      this.no = no;
			  this.computationalloadhw = cmpldhw;
			  this.computationalloadsw = cmpldsw;
			  this.macUsage = macUsage;
			  this.srammemory = srmem;
			  this.eprommemory = ermem;
			  this.communicationweight = commweight;
	   }
}  
 
public class SimulatedAnealingProject {

  static List<Node> NodeList = new ArrayList<Node>();	
  int size  = 0;	
  
  /**
   * change Temperature Function
   */
  static double changeTemperature(double temperature) {
	  if(temperature > 3000) {
		  temperature = 0.8 * temperature;
	  }else if( temperature <=3000 && temperature > 200){
		  temperature = 0.95 * temperature;
	  }else if(temperature < 200){
		  temperature = 0.7 * temperature;
	  }else if(temperature < 1.5) {
		  temperature = 0.1* temperature;
	  }
	  return temperature;
  }	

  
    /**
	 *    Extract Elements from Process Graph
	 */
	public void readFile()throws FileNotFoundException  {
		Scanner Infile = new Scanner(new File("C:\\SimulatedAnealing\\textfile3.txt"));
		while(Infile.hasNextLine())	{
			String temp =Infile.nextLine();
			String[] col =temp.split(" "); 
			addTransactions(col);
		}
		size = NodeList.size();
	}
	
	/**
	 *  Create an Node.
	 */
	public void addTransactions(String[] col) {
           int no = Integer.parseInt(col[0]);
		   int computationalloadhw = Integer.parseInt(col[1]);
		   int computationalloadsw = Integer.parseInt(col[2]);
		   boolean macUsage = false;
		   if(Integer.parseInt(col[3])==1){
		      macUsage = true;
		   }
		   int srammem = Integer.parseInt(col[4]);
		   int eprommem = Integer.parseInt(col[5]);
		   int commweight = Integer.parseInt(col[6]);
		   Node element = new Node(no,computationalloadhw,computationalloadsw,
				   macUsage,srammem,eprommem,commweight);
		   NodeList.add(element);
   }
  
	
	
	/**
	 *  Random Partitioning of the Nodes.
	 */	
  public List<Node> intialsoftwarePartitoning(List<Node> NodeList) {
	  for(int i = 0 ;i< size;i++) {
		  int random = (int) (Math.random()*size);
		  NodeList.get(i).partition = 1;
			
	   } 
	  return NodeList;
  }
  
	
	
	/**
	 *  Random Partitioning of the Nodes.
	 */	
  public List<Node> randomPartitoning(List<Node> NodeList) {
	  for(int i = 0 ;i< size;i++) {
		  int random = (int) (Math.random()*size);
		  if(NodeList.get(i).macUsage==true){
			  NodeList.get(i).partition = 0;
		  }else {
		  
			  	if(random > 7) {
			  		NodeList.get(i).partition = 1;
			  	} else {
			  		NodeList.get(i).partition = 0;
			  	}
		   }
		
	   } 
	  return NodeList;
  }
  
  
  /**
   *   Counting Partitioning of the Nodes.
   */
  public void countPartitioning(List<Node> NodeList) {
	  float swcount = 0, hwcount = 0;
	  for(int i = 0 ;i< size;i++){
		 if(NodeList.get(i).partition==0){
			 hwcount++;
		 } else {
			 swcount++;
		 }
	  }
	  float swpercent = (swcount*100)/size;
	  float hwpercent = (hwcount*100)/size;
	  System.out.println("Percentange of Partitioning Assignment:");
	  System.out.println("softwarepercent : "+ swpercent + " "+"hardwarepercent: " +" "+hwpercent);
	  System.out.println("No of Hardware Nodes Assigned:");
	  for(int i = 0 ;i< size;i++){
			 if(NodeList.get(i).partition==0) {
				 System.out.print(" "+ NodeList.get(i).no);
			 }
	  }
	  System.out.println("\nNo of Software Nodes Assigned:");
	  for(int i = 0 ;i< size;i++) {
		  if(NodeList.get(i).partition==1) {
				 System.out.print(" " +NodeList.get(i).no);
			 }		
	  }
	  
  }
  
  
  /**
   * cost 1 (Hardware computation Load)
   */
   public double cost1(List<Node> NodeList) {
	  double hadwareload = 0;
	  for(int i = 0 ;i< size;i++) {
		 if(NodeList.get(i).partition==0) {
				hadwareload+=NodeList.get(i).computationalloadhw; 
	     }
		 
	  } 
	  double cost = 0.98 *hadwareload;
	  return cost;
  }
  
   /**
    * cost 2 (Software computation Load)
    */  
   public double cost2(List<Node> NodeList) {
	  double swload = 0;
	  for(int i = 0 ;i< size;i++) {
		 if(NodeList.get(i).partition==1) {
			swload+=NodeList.get(i).computationalloadsw; 
	     }
		 
	  } 
	  double cost = 3.28 *swload;
	  return cost;
  }	  
  
   /**
    * cost 3 (Memory Cost)
    */    
  public double cost3(List<Node> NodeList) {
	  double memorycost  = 0;
	  float epromcount = 0, sramcount = 0;
	  for(int i = 0 ;i< size;i++){
		     epromcount+=NodeList.get(i).eprommemory;
			 sramcount= NodeList.get(i).srammemory;
		 
	  }
      double cost = .87*(sramcount - epromcount);
      return cost;
   }
  
  /**
   * cost 4 (Communication Cost)
   */  
  public double cost4(List<Node> NodeList){
	  double commweight  = 0;
      for(int i = 0 ;i< size;i++) {
		 if(NodeList.get(i).communicationweight == 1) {
		   commweight++;	
	     }
		 
      }		 
      double cost = commweight*1.30;
      return cost;
  }
  
  /**
   * Implementation of the Algorithm.
   */
  public static void main(String[] args) throws FileNotFoundException {
	  double temperature = 8000;			  ;
	  double prevcost = 0, bestcost = 99999999, globalbest = 0,
	  costchange = 0, newcost,intialcost = 0;
	  int count = 0;
	  SimulatedAnealingProject sl = new SimulatedAnealingProject();
	  sl.readFile();
	  List<Node> xnow = sl.randomPartitoning(NodeList);
      double icost1 = sl.cost1(xnow);
      double icost2 = sl.cost2(xnow);
      double icost3 = sl.cost3(xnow);
      double icost4 = sl.cost4(xnow);
      prevcost  = icost1 + icost2 + icost3 + icost4;  
      List<Node> xfinal = null;
	  int[] costbackup = new int[3];
	  costbackup[count] =(int) prevcost; 
	  while(temperature>= 0.1)  {
		  List<Node> xt = sl.randomPartitoning(xnow); //RandomPlacement();
		  double cost1 = sl.cost1(xt);
		  double cost2 = sl.cost2(xt);
		  double cost3 = sl.cost3(xt);
		  double cost4 = sl.cost4(xt);
		  newcost=cost1 +cost2 +cost3+ cost4;//calculating cost
		  costchange=newcost-prevcost;//detla cost-(change in cost)
		 
		  if(costchange<0) {//delta cost < 0...i.e gives local minima of cost
		     xnow = xt;
		     if(bestcost > prevcost ){
		    	 bestcost= prevcost;
		     }
		  } else {

		     double r= Math.random();
			 double param, result;
			 param=(costchange/temperature);
			 double exp1= Math.exp(-param);
			 if(r<exp1) {
			    xnow = xt;
			 }
		  }
		  
	      temperature = changeTemperature(temperature);//
		   icost1 = sl.cost1(xnow);
	       icost2 = sl.cost2(xnow);
	       icost3 = sl.cost3(xnow);
	       icost4 = sl.cost4(xnow);
	       prevcost  = icost1 + icost2 + icost3+ icost4;   
	       count=count+1;
	       if(count <=2){
		     costbackup[count] = (int) prevcost;   
			 if(costbackup[0]== costbackup[1] && costbackup[1]==costbackup[2]){
				 xfinal = xnow;
				 break;
			 } 
		   }else {
			 count = 0;
			 costbackup[count] = (int) prevcost; 
			 if(costbackup[1]== costbackup[2] && costbackup[1]==costbackup[2]){
				 xfinal = xnow;
				 break;
			 } 
		   }
		  
	 }
	 System.out.println("\n******Final Partition Result: ***** ");
	 if(xfinal != null){
		 sl.countPartitioning(xfinal);
	 }else {
		 sl.countPartitioning(xnow);
     }
 
  }
  
}
