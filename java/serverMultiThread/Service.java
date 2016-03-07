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
				String s = new String(buffer, 0, letti);
				total++;
				if(s.equals("close"))
				{
					this.s.close();
					System.out.println("total: "+this.ss.increment(total));
					return;
				}
				else
				{
                    OutputStream output = this.s.getOutputStream();
                    String toClient = "number: "+total;
                    output.write(toClient.getBytes(), 0, toClient.length());
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
