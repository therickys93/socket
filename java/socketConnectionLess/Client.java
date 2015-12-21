/**
 * Client.java
 *
 * Riccardo Crippa
 * therickys93@gmail.com
 *
 * Insert here your comments
 */
 
 import java.net.*;
 import java.io.*;
 
 public class Client
 {
     public static void main(String[] args)
     {
         DatagramSocket client;
         if(args.length == 2)
         {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            try
            {
                client = new DatagramSocket();
                System.out.println(client.getLocalAddress()+" "+client.getLocalPort());
                InetSocketAddress isa = new InetSocketAddress("localhost", port);
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                String sentence = br.readLine();
                byte[] buffer = sentence.getBytes();
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                dp.setSocketAddress(isa);
                client.send(dp);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
         }
         else
         {
             System.out.println("usage: java Client [hostname] [port]");
         }
     }
 }
