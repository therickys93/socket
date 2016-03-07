import java.io.*;
import java.net.*;

public class Client
{
	public static void main(String[] args)
	{
		Socket s;
		InetAddress ia;
		InetSocketAddress isa;
		
		if(args.length == 2)
		{
			s = new Socket();
			try
			{
				ia = InetAddress.getByName(args[0]);
				int port = Integer.parseInt(args[1]);
				isa = new InetSocketAddress(ia, port);
				s.connect(isa);
				System.out.println(s.getInetAddress() + " " + s.getLocalPort());
				InputStreamReader keyboard = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(keyboard);
				String str;
				while(true)
				{
					str = br.readLine();
					OutputStream toServer = s.getOutputStream();
					toServer.write(str.getBytes(), 0, str.length());
					if(str.equals("close"))
					{
						s.close();
						return;
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
