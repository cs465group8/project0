/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.net.*;
import java.io.*;



public class EchoServer {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
        
        int portNumber = Integer.parseInt(args[0]);
     //   while(true) //while loop for continuously checking for new client connections
            //when it detects a connection, create a socket in a thread for that connection
    //    {//while loop start
        try (
                //accept a connection
                //create a thread to deal with the client
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            Socket clientSocket = serverSocket.accept();   
                
            DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());                   
            DataInputStream fromClient = 
                    new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));           
        ) { //loop should read a character at a time from the and immediately send it back
            System.out.println("Connection with client established");    
            char charFromClient;
            String userInput;
            int quitProgression = 0;
            int stringLength, index;
            while ((userInput = fromClient.readLine()) != null && quitProgression < 4) {
                //inner loop for going along the length of the string
                stringLength = userInput.length();
                for(index = 0; index < stringLength; index++)
                {
                    //get the char we want to currently look at
                    charFromClient = userInput.charAt(index);
                                    
                    //if the character is non-alphabetical, do not print it back
                   // if(Character.isLetter(charFromClient))
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
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
            }
     //   }//while loop end
    }
    
    
    private class EchoThread implements Runnable { 
        
        public void run() 
        { 
            System.out.println(Thread.currentThread().getName() 
                             + ", executing run() method!"); 
        } 
    }
}
