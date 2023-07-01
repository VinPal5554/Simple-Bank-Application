
import java.net.*;
import java.io.*;

public class BankServer {
  public static void main(String[] args) throws IOException {

	ServerSocket BankServerSocket = null;
    boolean listening = true;
    String BankServerName = "BankServer";
    int BankServerNumber = 4545;
    
    double clientABalance = 1000;
    double clientBBalance = 1000;
    double clientCBalance = 1000;

    // Shared object created globally 
    
    SharedBankState ourSharedBankStateObject = new SharedBankState(clientABalance, clientBBalance, clientCBalance);
        
    // Set up server socket

    try {
      BankServerSocket = new ServerSocket(BankServerNumber);
    } catch (IOException e) {
      System.err.println("Could not start " + BankServerName + " specified port.");
      System.exit(-1);
    }
    System.out.println(BankServerName + " started");
    
    while (listening){
      new BankServerThread(BankServerSocket.accept(), "BankServerThreadA", ourSharedBankStateObject).start();
      new BankServerThread(BankServerSocket.accept(), "BankServerThreadB", ourSharedBankStateObject).start();
      new BankServerThread(BankServerSocket.accept(), "BankServerThreadC", ourSharedBankStateObject).start();
     // new BankServerThread(BankServerSocket.accept(), "BankServerThread4", ourSharedBankStateObject).start();
      System.out.println("New " + BankServerName + " thread started.");
    }
    BankServerSocket.close();
  }
}
