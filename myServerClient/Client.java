import java.net.*;
import java.io.*;

public class Client
{

	public static void main(String[] args)
	{
		Socket client;
		InetAddress ia;
		InetSocketAddress isa;

		client = new Socket();
		try
		{
			ia = InetAddress.getLocalHost();
			isa = new InetSocketAddress(ia, 9090);
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

}