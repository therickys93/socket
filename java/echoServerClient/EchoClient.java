import java.net.*;
import java.io.*;

public class EchoClient
{
	public static void main(String[] args)
	{
		InetAddress ia;
		InetSocketAddress isa;
		Socket client;
		boolean isConnectionOpen;

		// testo se da riga di comando ho ricevuto sia l indirizzo che la porta
		if(args.length == 2)
		{
			// inizializzo il client
			client = new Socket();
			try
			{
				// prendo l indirizzo del server da riga di comando
				String server = args[0];
				int port = Integer.parseInt(args[1]);
				ia = InetAddress.getByName(server);
				// crea una socket all indirizzo e alla porta
				isa = new InetSocketAddress(ia, port);
				// connette la socket al client
				client.connect(isa);
				// stampo informazioni sul client appena creato
				System.out.println("Porta " + client.getLocalPort());
				System.out.println("Indirizzo " + client.getInetAddress() + " porta " + client.getPort());
				System.out.println("if you want to close the connection type: close");
				// settare valore isConnectionOpened
				isConnectionOpen = true;
				// inizializzo la tastiera come input si caratteri
				InputStreamReader keyboard = new InputStreamReader(System.in);
				// dico di iniziare a leggere da tastiera
				BufferedReader br = new BufferedReader(keyboard);
				do
				{
					// prendo tutto quello che c'e nel buffer della tastiera
					String str = br.readLine();
					// creo il canale di uscita col server
					OutputStream out = client.getOutputStream();
					// invio la stringa al server
					out.write(str.getBytes(), 0, str.length());
					// se la stringa inviata e uguale a close
					if(str.equals("close"))
					{
						// termino la socket del client
						client.close();
						// dico che non voglio piu rimanere nel ciclo
						isConnectionOpen = false;
					}
					else
					{
						// dimensione buffer
						int dim = 1024;
						// creazione del buffer in byte
						byte[] buffer = new byte[dim];
						// prendere l imput dal server
						InputStream fromClient = client.getInputStream();
						// lunghezza della stringa inviata dal server
						int readed = fromClient.read(buffer);
						// creo la stringa e la stampo a schermo
						String strFromServer = new String(buffer, 0, readed);
						System.out.println(strFromServer);
					}
				}
				while (isConnectionOpen == true);
			}
			catch(Exception e)
			{
				// stampo lo stack del programma per debugging
				e.printStackTrace();
			}
		}
		else
		{
			// stampa come deve essere fatto il comando per terminale
			System.out.println("usage: java EchoClient [address] [port]");
		}
	}
}