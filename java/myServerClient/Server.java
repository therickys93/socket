import java.net.*;
import java.io.*;

public class Server
{

	public static void main(String[] args)
	{
		ServerSocket ss;
		Socket client;
		try
		{
			ss = new ServerSocket(9090);
			System.out.println("indirizzo " + ss.getInetAddress() + " porta " + ss.getLocalPort());
			client = ss.accept();
			System.out.println("indirizzo " + client.getInetAddress() + " porta " + client.getPort());
			//Thread.sleep(4 * 60 * 1000);
			while(true)
			{
				int dim = 1024;
				byte[] buffer = new byte[dim];
				InputStream fromClient = client.getInputStream();
				int readed = fromClient.read(buffer);
				if(readed >= 0)
				{
					String str = new String(buffer, 0, readed);
					System.out.println("received: " + str);	
				}
				else
				{
					System.out.println("Connection close by Client");
					break;
				}
			} 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}