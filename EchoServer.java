
import java.net.*;
import java.io.*;



public class EchoServer 
	{
    public static void main(String[] args) throws IOException 
    	{
        
        if (args.length != 1) 
        	{
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        	}
        
        int portNumber = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        //while loop for continuously checking for new client connections
    	//when it detects a connection, create a socket in a thread for that connection
    	//while loop start
        while(true) 
     		{
        	new Thread(new EchoThread(serverSocket.accept())).start();  
     		}
    	}
    
     public static class EchoThread implements Runnable 
     	{ 
        //constructor goes here
         Socket clientSocket; 
         public EchoThread(Socket theClientSocket)
         {
             clientSocket = theClientSocket; 
         }
         
        public void run() 
        	{ 
         
            try 
            	(
                //accept a connection
                //create a thread to deal with the client   
                DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());                   
                DataInputStream fromClient = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));           
            	) 
            	//loop should read a character at a time from the and immediately send it back
            	{ 
                System.out.println("Connection with client established");    
                char charFromClient;
                String userInput;
                int quitProgression = 0, nonCharCount = 0;
                int stringLength, index;
                while ((userInput = fromClient.readLine()) != null && quitProgression < 4) 
                	{
                    //inner loop for going along the length of the string
                    stringLength = userInput.length();
                    for(index = 0; index < stringLength; index++)
                    	{
                        //get the char we want to currently look at
                        charFromClient = userInput.charAt(index);                
                        //if the character is non-alphabetical, do not print it back
                        //if(Character.isLetter(charFromClient))
                        if((charFromClient >= 'a' && charFromClient <= 'z') ||
                             (charFromClient >= 'A' && charFromClient <= 'Z'))
                        	{
                            toClient.writeChar(charFromClient);
                            //check if the letters 'q' 'u' 'i' 't' have been typed in succession
                            //skip over if non alphabetic char                       
                            if(quitProgression == 0)
                            	{
                                if(charFromClient == 'q' || charFromClient == 'Q')
                                	{
                                    quitProgression += 1;
                                	}
                                else
                                	{
                                    quitProgression = 0;  
                                	}
                            	}
                            else if(quitProgression == 1)
                            	{
                                if(charFromClient == 'u' || charFromClient == 'U')
                                	{
                                    quitProgression += 1;
                                	}
                                else
                                	{
                                    quitProgression = 0; 
                                	}                        
                            	}
                            else if(quitProgression == 2)
                            	{
                                if(charFromClient == 'i' || charFromClient == 'I')
                                	{
                                    quitProgression += 1;
                                	}
                                else
                                	{
                                    quitProgression = 0; 
                                	}                        
                            	}
                            else
                            	{
                                if(charFromClient == 't' || charFromClient == 'T')
                                	{
                                    quitProgression += 1;
                                    System.out.println("Quit sequence initiated" + quitProgression);
                                	}
                                else
                                	{
                                    quitProgression = 0; 
                                	}                        
                            	}
                        	}
                        else
                        	{
                        	toClient.writeChar(' '); 
                        	}
                    	}
                	}
            } catch (IOException e)
                {
                System.out.println("Exception caught when trying to listen on port "
                    + 3347 + " or listening for a connection");
                System.out.println(e.getMessage());
                }
            }//while loop end
        }
	}	

