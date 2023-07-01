
import java.io.*;
import java.net.*;

public class ClientB {
    public static void main(String[] args) throws IOException {

        Socket BankClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int BankSocketNumber = 4545;
        String BankServerName = "localhost";
        String BankClientID = "BankClientB";

        try {
            BankClientSocket = new Socket(BankServerName, BankSocketNumber);
            out = new PrintWriter(BankClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(BankClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ BankSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + BankClientID + " client and IO connections");
       
        while (true) {
            
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println(BankClientID + ":" + " " + fromUser);
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println(fromServer);
        }
            
    }
}
