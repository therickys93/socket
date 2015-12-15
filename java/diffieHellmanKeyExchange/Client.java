/**
 * Client.java
 *
 * Riccardo Crippa
 * therickys93@gmail.com
 *
 * client for the Diffie-Hellman Key Exchange
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Client
{
	public static void main(String[] args)
	{
		Socket client;
		InetAddress ia;
		InetSocketAddress isa;
		
		if(args.length == 2)
		{
			client = new Socket();
			try
			{
				int port = Integer.parseInt(args[1]);
				ia = InetAddress.getByName(args[0]);
				isa = new InetSocketAddress(ia, port);
				client.connect(isa);
				System.out.println("port: "+client.getLocalPort());
				System.out.println("address: "+client.getInetAddress());
				
				// start the diffie-hellman key exchange algorithm
				int a = 5;
				int p = 23;
				
				Random rand = new Random();
				int y = rand.nextInt(a) + 1;
				int numberToServer = (int)(Math.pow(a, y) % p);
				String str = ""+numberToServer;
				OutputStream out = client.getOutputStream();
				out.write(str.getBytes(), 0, str.length());
				int dim = 1024;
				byte[] buffer = new byte[dim];
				InputStream in = client.getInputStream();
				int readed = in.read(buffer);
				String strFromServer = new String(buffer, 0, readed);
				int numberFromServer = Integer.parseInt(strFromServer);
				int result = (int)(Math.pow(numberFromServer, y) % p);
				System.out.println("result: "+result);
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