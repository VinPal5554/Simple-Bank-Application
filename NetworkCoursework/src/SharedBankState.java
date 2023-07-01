
import java.net.*;
import java.io.*;
import java.util.Scanner;


public class SharedBankState{
	
	//private SharedBankState mySharedObj;
	//private String myThreadName;
	
	private double clientABalance;
	private double clientBBalance;
	private double clientCBalance;
	
  
	private boolean accessing=false; 
	private int threadsWaiting=0; 

	SharedBankState(double balanceA, double balanceB, double balanceC) {
	
		clientABalance = balanceA;
		clientBBalance = balanceB;
		clientCBalance = balanceC;
		
	}

//Attempt to acquire a lock
	
	  public synchronized void acquireLock() throws InterruptedException{
	        Thread me = Thread.currentThread(); // get a ref to the current thread
	        System.out.println(me.getName()+" is attempting to acquire a lock!");	
	        ++threadsWaiting;
		    while (accessing) {  // while someone else is accessing or threadsWaiting > 0
		      System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
		      //wait for the lock to be released - see releaseLock() below
		      wait();
		    }
		    // nobody has got a lock so get one
		    --threadsWaiting;
		    accessing = true;
		    System.out.println(me.getName()+" got a lock!"); 
		  }

		  // Releases a lock to when a thread is finished
		  
		  public synchronized void releaseLock() {
			  //release the lock and tell everyone
		      accessing = false;
		      notifyAll();
		      Thread me = Thread.currentThread(); // get a ref to the current thread
		      System.out.println(me.getName()+" released a lock!");
		  }
	
		  
	public synchronized String processInput(String myThreadName, String theInput) {
    	System.out.println(myThreadName + " received "+ theInput);
    	String theOutput = null;
    	
    	char inputArr[] = theInput.toCharArray();
    	int amountIndex = 0;
    	String amount = "";
    	int parameterCounter = 0;
    	
    	for(int i = 0; i < inputArr.length -  1; ++i) {
    		
    		if(inputArr[i] == ',') {
    			++parameterCounter;
    			amountIndex = i;
    			
    			if(parameterCounter == 1 && theInput.contains("Transfer_money")) { // transfer has 3 parameters: if command is 'transfer' skip second parameter to check third
    				amountIndex = 0;
    				continue;
    			}
    			else if(parameterCounter == 2) {
    				parameterCounter = 0; // reset counter for other requests
    			}
    		}
    		if(amountIndex != 0) {
    			
    			for(int j = amountIndex + 1; j <inputArr.length - 1; ++j) {
    				
    				amount += inputArr[j];
    				amountIndex = 0;
    			}
    			
    			
    		}
    	}
    	
    	int integerValue = Integer.parseInt(amount);
    	
    	// Logic for depositing money
    	
    	if(theInput.equals("Add_money" + "(" + "clientABalance" + "," + amount + ")")) {
    		
    		clientABalance += integerValue;
    		System.out.println("Added " + integerValue + " to clientABalance");
    		theOutput = "You have successfully deposited " + amount + " to clientABalance. Total: " + clientABalance;
    	}
    	
    	else if(theInput.equals("Add_money" + "(" + "clientBBalance" + "," + amount + ")")) {
    		
    		clientBBalance += integerValue;
    		System.out.println("Added " + integerValue + " to clientBBalance");
    		theOutput = "You have successfully deposited " + amount + " to clientBBalance. Total: " + clientBBalance;
    	}

    	else if(theInput.equals("Add_money" + "(" + "clientCBalance" + "," + amount + ")")) {
	
    		clientCBalance += integerValue;
    		System.out.println("Added " + integerValue + " to clientCBalance");
    		theOutput = "You have successfully deposited " + amount + " to clientCBalance. Total: " + clientCBalance;
    	}
    	
    	// Logic for withdrawing money
    	
    	else if(theInput.equals("Subtract_money" + "(" + "clientABalance" + "," + amount + ")")) {
    		
    		clientABalance -= integerValue;
    		System.out.println("Subtracted " + integerValue + " from clientABalance");
    		theOutput = "You have successfully withdrawn " + amount + " from clientABalance. Total: " + clientABalance;
    	}
    	
    	else if(theInput.equals("Subtract_money" + "(" + "clientBBalance" + "," + amount + ")")) {
    		
    		clientBBalance -= integerValue;
    		System.out.println("Subtracted " + integerValue + " from clientBBalance");
    		theOutput = "You have successfully withdrawn " + amount + " from clientBBalance. Total: " + clientBBalance;
    	}
    	
    	else if(theInput.equals("Subtract_money" + "(" + "clientCBalance" + "," + amount + ")")) {
    		
    		clientCBalance -= integerValue;
    		System.out.println("Subtracted " + integerValue + " from clientCBalance");
    		theOutput = "You have successfully withdrawn " + amount + " from clientCBalance. Total: " + clientCBalance;
    	}
    	   	
    	// Logic for transferring between accounts
    	
    	else if(theInput.equals("Transfer_money" + "(" + "clientABalance" + "," + "clientBBalance" + "," + amount + ")")) {
    		
    		clientABalance -= integerValue;
    		clientBBalance += integerValue;
    		System.out.println("Transferred " + integerValue + " from clientABalance to clientBBalance");
    		theOutput = "You have transferred " + amount + " from clientABalance to clientBBalance. Total: " + clientABalance;
	
    	}
    	
    	else if(theInput.equals("Transfer_money" + "(" + "clientABalance" + "," + "clientCBalance" + "," + amount + ")")) {
    		
    		clientABalance -= integerValue;
    		clientCBalance += integerValue;
    		System.out.println("Transferred " + integerValue + " from clientABalance to clientCBalance");
    		theOutput = "You have transferred " + amount + " from clientABalance to clientCBalance. Total: " + clientABalance;
    		
    	}
    	
    	else if(theInput.equals("Transfer_money" + "(" + "clientBBalance" + "," + "clientABalance" + "," + amount + ")")) {
    		
    		clientBBalance -= integerValue;
    		clientABalance += integerValue;
    		System.out.println("Transferred " + integerValue + " from clientBBalance to clientABalance");
    		theOutput = "You have transferred " + amount + " from clientBBalance to clientABalance. Total: " + clientBBalance;
    	}
    	
    	else if(theInput.equals("Transfer_money" + "(" + "clientBBalance" + "," + "clientCBalance" + "," + amount + ")")) {
    		
    		clientBBalance -= integerValue;
    		clientCBalance += integerValue;
    		System.out.println("Transferred " + integerValue + " from clientBBalance to clientCBalance");
    		theOutput = "You have transferred " + amount + " from clientBBalance to clientCBalance. Total: " + clientBBalance;
    	}
    	
    	else if(theInput.equals("Transfer_money" + "(" + "clientCBalance" + "," + "clientABalance" + "," + amount + ")")) {
    		
    		clientCBalance -= integerValue;
    		clientABalance += integerValue;
    		System.out.println("Transferred " + integerValue + " from clientCBalance to clientABalance");
    		theOutput = "You have transferred " + amount + " from clientCBalance to clientABalance. Total: " + clientCBalance;
    	}
    	
    	else if(theInput.equals("Transfer_money" + "(" + "clientCBalance" + "," + "clientBBalance" + "," + amount + ")")) {
    		
    		clientCBalance -= integerValue;
    		clientBBalance += integerValue;
    		System.out.println("Transferred " + integerValue + " from clientCBalance to clientBBalance");
    		theOutput = "You have transferred " + amount + " from clientCBalance to clientBBalance. Total: " + clientCBalance;
    	}
    	
     	//Return the output message to the BankServer
    	System.out.println(theOutput);
    	return theOutput;
    	
	
	}
	
	
}
