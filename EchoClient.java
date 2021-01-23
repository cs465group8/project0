import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException 
    {
        // Check command line arguments for host name and port number    
        if (args.length != 2) 
        {
            // Alert user to unentered name or number, then quit
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        // Set host name and port number to entered arguments
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        try 
        {
            // Creating client socket and getting input / output streams
            Socket echoSocket = new Socket(hostName, portNumber);
            DataOutputStream toServer = new DataOutputStream(echoSocket.getOutputStream());
            DataInputStream fromServer = new DataInputStream(echoSocket.getInputStream());
            DataInputStream stdIn = new DataInputStream((System.in));
            
            // Setting variables for user input to server
            String userInput;
            char charBeingSent;

            // loop to get input from user, and then send that input to server
            while ((userInput = stdIn.readLine()) != null) 
            {
                for (int index = 0; index < userInput.length(); index ++)
                {
                	charBeingSent = userInput.charAt(index);
                	toServer.writeByte(charBeingSent);
                }
                
                // Server recieves input, then echoes back to client
                toServer.writeByte('\n');
                System.out.println("echo: "); 
                
                // inner loop: get a byte from the server for each char in the string
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
                System.out.println(""); // just printing a newline
            } //loop end
            
            // Close connections upon server recieving quit sequence
            fromServer.close();
            toServer.close();
            echoSocket.close();
            stdIn.close(); 
        } 
        
        // Exception thrown for unknown host
        catch (UnknownHostException e) 
        {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } 
        
        // Exception thrown for incorrect port numbers
        catch (IOException e) 
        {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
}
