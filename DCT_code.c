#include <m8c.h>        // Part specific constants and macros
#include "PSoCAPI.h"    // PSoC API definitions for all User Modules
#include <stdlib.h>		// Prototypes of all string functions
#include <E2PROM.h>		// E2PROM definitions and macros
#include<math.h>
#define SIZE 64
#define N_POINT 512


void Display(float []);
  


#pragma abs_address 0x7FA0
 
#pragma end_abs_address
int i;
int k;
float pi =3.14;
int t,u;
float tempArray[16];
int dimSignal = 64;
int l;
int d=0;
int writeLocation=0;
float testBlockA[SIZE];
double timeelapsed;
int* Status2;
char* FloatString2;

void dct(){
    int s,t,r,z,pos,readpos,pointpos;
	
	float transformed[16];
	float readSignal[16];
	int location = 256;
	int readLocation=0;
	pos=0;
	readpos=0;
	pointpos=0;
	for (r=0;r<N_POINT/16;r++){
    for(s=0;s<16;s++){
        transformed[s]=0;
		pointpos =0;
			for (z=0;z<N_POINT/16;z++){
				E2PROM_E2Read( readLocation+readpos, (BYTE *)&readSignal, sizeof(readSignal) ); 
				for(t=0;t<16;t++){
            			transformed[s] += readSignal[t] *cos((M_PI/((float)N_POINT))*(t+pointpos+1./2)*(s+pos));
       			 }
				pointpos +=16;
				readpos +=16;
			}
			
        transformed[s] = 2*transformed[s];
		E2PROM_bE2Write(location +pos,(BYTE *)&tempArray, sizeof(tempArray), 25);
    }
		location +=16;
		 pos +=16;
	}
}

void writeToFlash(float array1[],int location){
	int pos=0;
	int pos1=16;
		for (t=0;t<4;){
			for (u=0;u<16;u++){
				tempArray[u]= array1[u+ t*pos1];
			}
			t++;
			E2PROM_bE2Write(location + pos,(BYTE *)&tempArray, sizeof(tempArray), 25);
			pos +=16;
		}
}

void main(void)
{	
	LCD_Start();
	PGA_1_Start(PGA_1_HIGHPOWER);
	LPF2_3_Start(LPF2_3_HIGHPOWER);
	DUALADC_Start(DUALADC_HIGHPOWER); // Turn on Analog section
    DUALADC_SetResolution(7);         // Set resolution to 7 Bits
    DUALADC_GetSamples(0);
	
	E2PROM_Start();
	

	 
	for (d=0;d<8;d++) {
      while (i <64)// Main loop 
    {   
	 	if(DUALADC_fIsDataAvailable()) // Wait for data to 
		{   
        	testBlockA[i] = DUALADC_iGetData1();    // Get Data		
			DUALADC_ClearFlag();
			i++;
		}
	}
	i=0;
		writeToFlash(testBlockA,writeLocation);
		writeLocation +=64;
	}
    dct();
	M8C_EnableGInt;
}

void Display(float Data[])
{
	int* Status;
	char* FloatString;
	for (i = 0; i < 8; i++){
	FloatString = ftoa(Data[i],Status);			
	LCD_PrString(FloatString);				
	LCD_Position(1,0);
	}
}