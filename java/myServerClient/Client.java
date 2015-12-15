import java.net.*;
import java.io.*;

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
				ia = InetAddress.getByName(args[0]);
				int port = Integer.parseInt(args[1]);
				isa = new InetSocketAddress(ia, port);
				client.connect(isa);
				System.out.println("Porta " + client.getLocalPort());
				System.out.println("Indirizzo " + client.getInetAddress() + " porta " + client.getPort());
				//Thread.sleep(2 * 60 * 1000);
	
				InputStreamReader keyboard = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(keyboard);
				while(true)
				{
					String str = br.readLine();
					OutputStream out = client.getOutputStream();
					out.write(str.getBytes(), 0, str.length());
					if(str.equals("close"))
					{
						client.close();
						break;
					}
				}
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