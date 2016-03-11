import java.net.*;
import java.io.*;

public class Service extends Thread
{
	private Socket s;
	private int id;
	private StringCount ss;
	
	public Service(Socket s, int id, StringCount ss)
	{
		this.s = s;
		this.id = id;
		this.ss = ss;
	}
	
	public void run()
	{
		int dim_buffer = 1024;
		byte[] buffer = new byte[dim_buffer];
		int total = 0;
		while(true)
		{
			try
			{
				InputStream input = this.s.getInputStream();
				int letti = input.read(buffer);
				String str = new String(buffer, 0, letti);
				total++;
				if(str.equals("close"))
				{
                    String toClient = "total: "+this.ss.increment(total);
					System.out.println(toClient);
                    OutputStream output = this.s.getOutputStream();
                    output.write(toClient.getBytes(), 0, toClient.length());
                    this.s.close();
					return;
				}
				else
				{
                    String toClient = "total: "+this.ss.increment(total);
					System.out.println(toClient);
                    OutputStream output = this.s.getOutputStream();
                    output.write(str.getBytes(), 0, str.length());
					System.out.println(s);
				}
			}
			catch(Exception e)
			{
					e.printStackTrace();
			}
		}
	}
}
