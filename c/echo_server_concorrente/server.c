#include <stdio.h>
#include <stdlib.h>
#include <netdb.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>

int erogazione_servizio(int socket)
{
	int dim = 1024;
	char buffer[dim];
	int r;
	while(1)
	{
		r = recv(socket, buffer, dim, 0);
		if (r > 0)
		{
			buffer[r - 2] = '\0';
			printf("ricevuta: %s\n", buffer);
			if(strcmp(buffer, "close") == 0)
			{
				return 0;
			}
			send(socket, buffer, r, 0);
		}
		else
		{
			return r;
		}
	}
}

int main(int argc, char **argv)
{
	if(argc != 2)
	{
		printf("usage: %s [port]\n", argv[0]);
		return -1;
	}
	int porta = atoi(argv[1]);
	struct protoent *pe = getprotobyname("tcp");
	int s = socket(AF_INET, SOCK_STREAM, pe->p_proto);
	struct sockaddr_in server;
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = htons(porta);
	if(bind(s,(struct sockaddr *) &server, sizeof(server)) < 0)
	{
		perror("bind");
		return -1;
	}
	listen(s, 128);
	if(porta == 0)
	{
		struct sockaddr_in locale;
		unsigned int dim_locale = sizeof(locale);
		getsockname(s,(struct sockaddr *) &locale, &dim_locale);
		printf("Porta allocata : %d\n", ntohs(locale.sin_port));
	}
	else
	{
		printf("Porta allocata: %d\n", ntohs(server.sin_port));
	}
	struct sockaddr_in chiamante;
	unsigned int dim_chiamante = sizeof(chiamante);
	int socket_dati;
	while(1)
	{
		socket_dati = accept(s, (struct sockaddr *) &chiamante, &dim_chiamante);
		printf("new connection\n");
		if(erogazione_servizio(socket_dati) < 0)
		{
			return -1;
		}
		printf("close connection\n");
		close(socket_dati);
	}
	return 0;
}
