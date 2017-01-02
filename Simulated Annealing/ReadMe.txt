
ReadMe
------------------------------

*File Details:
 SimulatedAnnealing.java
 txtfile1.txt

*Instructions to Run:
 The text file and SimulatedAnnealing.java must be int the same directory

*Functions & Uses:
 1.	Read the ReadFiles():  Reads the Process Graph given as Input.
 2.	Public int Cost1(List<Node> NodeList), int Cost2(List<Node> NodeList), int Cost3(List<Node> NodeList),  int Cost4(List<Node> NodeList) these functions calculate each term of the cost function.
 3.	List<Node> randomPartitoning(List<Node> NodeList): Randomly partitions the current solution
 4.	public countPartitioning(List<Node> NodeList): counts the partition of the nodes 
 5.	main() : this function implements the complete simulated annealing algorithm.

*Structure of Class
class Node {
	   int no;
	   int computationalloadhw;
	   int computationalloadsw;
	   int partition;
	   boolean macUsage;
	   boolean memory;
	   int communicationweight;
	   public Node (int no, int cmpldhw, int cmpldsw, boolean   
          macUsage, boolean mem, int commweight) {
		         this.no = no;
			  this.computationalloadhw = cmpldhw;
			  this.computationalloadsw = cmpldsw;
			  this.macUsage = macUsage;
			  this.memory = mem;
			  this.communicationweight = commweight;
	       }
 } 
