import java.util.Scanner;

import Barriers.FirstBarrier;
import Barriers.IBarrier;
import Barriers.SecondBarrier;

public class Barrier {
	
	public static void main(String[] args) {
		int totalThreadCount = 0;
        String barrierType = "";
        boolean printOutput = false;
		if(args.length < 2){
			Scanner scn = new Scanner(System.in);
			while(barrierType == ""){
				System.out.println("Enter the type of barrier to be tested:"
									+ System.lineSeparator() +  "1. First Barrier" 
									+ System.lineSeparator() +  "2. Second Barrier");
				barrierType = scn.nextLine();
				if(barrierType.toLowerCase().equals("first") || barrierType.toLowerCase().equals("firstbarrier") 
						|| barrierType.toLowerCase().equals("first barrier") || barrierType.equals("1")){
					barrierType = "first";
				}
				else if(barrierType.toLowerCase().equals("second") || barrierType.toLowerCase().equals("secondbarrier") 
						|| barrierType.toLowerCase().equals("second barrier") || barrierType.equals("2")){
					barrierType = "second";
				}
				else
					barrierType = "";
			}
			while(totalThreadCount == 0){
				System.out.println("Enter total number of threads:");
				try
				{
					totalThreadCount = Integer.parseInt(scn.nextLine());
				}
				catch(Exception ex){
					System.out.println("Enter a valid integer!");
				}
			}
			
			System.out.println("Enter true to print output or anything else for skip.");
			try
			{
				printOutput = Boolean.parseBoolean(scn.nextLine());
			}
			catch(Exception ex)
			{
				printOutput = false;
				System.out.println("No output will be printed.");						
			}
			
			System.out.println("**********************************************");
		}
        else{
        	barrierType = args[0];
        	if(barrierType.toLowerCase() == "first" || barrierType.toLowerCase() == "firstbarrier" 
					|| barrierType.toLowerCase() == "first barrier" || barrierType.toLowerCase() == "1"){
				barrierType = "first";
			}
			else if(barrierType.toLowerCase() == "second" || barrierType.toLowerCase() == "secondbarrier" 
					|| barrierType.toLowerCase() == "second barrier" || barrierType.toLowerCase() == "2"){
				barrierType = "second";
			}
			else
				barrierType = "";
            totalThreadCount = Integer.parseInt(args[1]);
            if(args.length > 2){
            	printOutput = Boolean.parseBoolean(args[2]);
            }
        }		
		
		final IBarrier [] barrierArray;
		if(barrierType == "first")
			barrierArray = new FirstBarrier[totalThreadCount];
		else
			barrierArray = new SecondBarrier[totalThreadCount];
		
		for(Integer i=0; i<totalThreadCount; i++){
			IBarrier barrier;
			if(barrierType.equals("first"))
				barrier = new FirstBarrier(totalThreadCount, printOutput, i);
			else
				barrier = new SecondBarrier(totalThreadCount, printOutput, i);
			barrierArray[i] = barrier;		
		}
		
		for(Integer i=0; i<totalThreadCount; i++){
			((Thread)barrierArray[i]).start();
		}
		
		long totalTime = 0;
		for(int t=0; t<barrierArray.length; t++) {	
			try {
				((Thread)barrierArray[t]).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			totalTime += barrierArray[t].getElapsedTime();
		}

		System.out.println("Average time per thread is " + totalTime/totalThreadCount + "ms.");
	}

}
