/**
 * Server.java
 *
 * Riccardo Crippa
 * therickys93@gmail.com
 *
 * Insert here your comments
 */
 
 import java.io.*;
 import java.net.*;
 
 public class Server 
 {
     public static void main(String[] args)
     {
         DatagramSocket server;
         if(args.length == 1)
         {
            int port = Integer.parseInt(args[0]);
            try
            {
                server = new DatagramSocket(port);
                System.out.println(server.getLocalAddress()+" "+server.getLocalPort());
                int dimBuffer = 1024;
                byte[] buffer = new byte[dimBuffer];
                DatagramPacket dp = new DatagramPacket(buffer, dimBuffer);
                server.receive(dp);
                String sentence = new String(buffer, 0, dp.getLength());
                System.out.println("received: " + sentence);
                InetAddress ia = dp.getAddress();
                int remotePort = dp.getPort();
                System.out.println(ia.getHostAddress()+" "+remotePort);
                server.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
         }
         else
         {
             System.out.println("usage: java Server [port]");
         }
     }
 }
