import java.io.*;
import java.net.*;

public class Server
{
	public static void main(String[] args)
	{
		ServerSocket server;
		Socket client;
		StringCount ss;
		int numThread = 0;
		
		if(args.length == 1)
		{
			try
			{
				int port = Integer.parseInt(args[0]);
				server = new ServerSocket(port);
				System.out.println(server.getInetAddress() + " " + server.getLocalPort());
				ss = new StringCount();
				while(true)
				{
					numThread++;
					client = server.accept();
					System.out.println(client.getInetAddress() + " " + client.getPort());
					Thread t = new Service(client, numThread, ss);
					t.start();
				}
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
