import java.net.*;
import java.io.*;

/*
* stringhe da inviare per scaturire qualcosa
* close --> chiude la connessione col client e rimane in attesa di un altro client
*/
public class EchoServer
{
	public static void main(String[] args)
	{
		ServerSocket ss;
		Socket client;
		boolean isConnectionOpen;

		if(args.length == 1)
		{
			try
			{
				// prende il numero di porta che gli viene passato come parametro
				int port = Integer.parseInt(args[0]);
				// crea una server socket e la inizializza alla porta passata come parametro
				ss = new ServerSocket(port);
				// stampa l indirizzo a cui il server e inizializzato e
				// la porta in cui e in ascolto
				System.out.println(ss.getInetAddress()+" "+ss.getLocalPort());
				System.out.println("terminate server with ctrl+c");
				do
				{
					// stampa che e pronto per una nuova connessione
					System.out.println("Ready for a new connection");
					// accetta una socket proveniente dall esterno
					client = ss.accept();
					// settare valore di isConnectionOpened
					isConnectionOpen = true;
					// stampa l indirizzo del client e la porta del client
					System.out.println(client.getInetAddress()+" "+client.getPort());
					// dialogo con il cliente
					do
					{
						// dimensione buffer
						int dim = 1024;
						// creazione del buffer in byte
						byte[] buffer = new byte[dim];
						// prendere l imput dal client
						InputStream fromClient = client.getInputStream();
						// lunghezza della stringa inviata dal client
						int readed = fromClient.read(buffer);
						// string inviata dal client
						String str = new String(buffer, 0, readed);
						System.out.println("received: " + str);
						// se la stringa ricevuta e uguale a 0 chiude la connessione
						if(str.equals("close"))
						{
							// chiude connessione col client
							client.close();
							// setta il valore per uscire dal ciclo
							isConnectionOpen = false;
						}	
						else
						{
							// aggiungo qualcosa alla stringa in modo tale che sia diversa
							str = "echo: ".concat(str);
							// invia la stringa modificata al client
							OutputStream out = client.getOutputStream();
							out.write(str.getBytes(), 0, str.length());
						}
					}
					while (isConnectionOpen == true);
				}
				while(true);
			}
			catch(Exception e)
			{
				// stampa lo stack solo per debugging
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("usage: java EchoServer [port]");
		}
	}
}