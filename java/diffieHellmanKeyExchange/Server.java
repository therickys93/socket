/**
 * Server.java
 *
 * Riccardo Crippa
 * therickys93@gmail.com
 *
 * Server for the Diffie-Hellman Key Exchange
 */
 
 import java.io.*;
 import java.net.*;
 import java.util.*;
 
 public class Server
 {
	 public static void main(String[] args)
	 {
		 ServerSocket server;
		 Socket client;
		 
		 if(args.length == 1)
		 {
			 try
			 {
				 int port = Integer.parseInt(args[0]);
				 server = new ServerSocket(port);
				 System.out.println(server.getInetAddress()+" "+server.getLocalPort());
				 client = server.accept();
				 System.out.println(client.getInetAddress()+" "+client.getPort());
				 
				 // start the diffie hellman key exchange algorithm
				 int a = 5;
				 int p = 23;
				 
				 Random rand = new Random();
				 int x = rand.nextInt(a) + 1;
				 int numberToServer = (int)(Math.pow(a, x) % p);
				 int dim = 1024;
				 byte[] buffer = new byte[dim];
				 InputStream in = client.getInputStream();
				 int readed = in.read(buffer);
				 String str = new String(buffer, 0, readed);
				 int numberFromClient = Integer.parseInt(str);
				 OutputStream out = client.getOutputStream();
				 String strToClient = ""+numberToServer;
				 out.write(strToClient.getBytes(), 0, strToClient.length());
				 int result = (int)(Math.pow(numberFromClient, x) % p);
				 System.out.println("result: "+result);
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
