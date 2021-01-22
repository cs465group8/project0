import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        try {
            Socket echoSocket = new Socket(hostName, portNumber);
            DataOutputStream toServer = new DataOutputStream(echoSocket.getOutputStream());
            DataInputStream fromServer = new DataInputStream(echoSocket.getInputStream());
            DataInputStream stdIn = new DataInputStream((System.in));
            
            String userInput;
            char charBeingSent;

            //loop to get input from user, and then send that input to server
            while ((userInput = stdIn.readLine()) != null) {
                for (int index = 0; index < userInput.length(); index ++)
                	{
                	charBeingSent = userInput.charAt(index);
                	toServer.writeByte(charBeingSent);
                	}
                toServer.writeByte('\n');
                System.out.println("echo: "); 
                
                //inner loop: get a byte from the server for each char in the string
                int index;
                char tempChar;
                for(index = 0; index < userInput.length(); index++)
                	{
                	tempChar = fromServer.readChar();
                	if(tempChar != ' ')
                		{
                		System.out.print(tempChar);
                		}
                	}
                System.out.println(""); //just printing a newline
            }//loop end
            fromServer.close();
            toServer.close();
            echoSocket.close();
            stdIn.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
}
