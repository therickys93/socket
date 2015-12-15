import java.io.*;
import java.net.*;

public class Client
{
	public static void main(String[] args)
	{
		Socket s;
		InetAddress ia;
		InetSocketAddress isa;
		
		s = new Socket();
		try
		{
			ia = InetAddress.getLocalHost();
			isa = new InetSocketAddress(ia, 9090);
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
			
		}
	}
}
