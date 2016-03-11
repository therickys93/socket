#include <stdio.h>
#include <stdlib.h>
#include <netdb.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>

int fruizione_servizio(int socket)
{
	int dim = 1024;
	char buffer[dim];
	char *n1;
	int r;
	while(1)
	{
		if(fgets(buffer, dim, stdin) == NULL)
		{
			perror("fgets");
			return -1;
		}
		if(strstr(buffer, "\r\n") == NULL)
		{
			n1 = strrchr(buffer, '\n');
			sprintf(n1, "\r\n");
		}
		r = strlen(buffer);
		if(strcmp(buffer, "close") == 0) return 0;
		send(socket, buffer, r, 0);
		r = recv(socket, buffer, dim, 0);
		if(r == 0) return 0;
		buffer[r - 2] = '\0';
		printf("%s\n", buffer);
	}
}

int main(int argc, char **argv)
{
	if(argc != 3)
	{
		printf("usage: %s [hostname] [port]\n", argv[0]);
		return -1;
	}
	struct protoent *pe = getprotobyname("tcp");
	int s = socket(AF_INET, SOCK_STREAM, pe->p_proto);
	char *nome_host = argv[1];
	int porta = atoi(argv[2]);
	struct hostent *he = gethostbyname(nome_host);
	in_addr_t indirizzo = *((in_addr_t *)(he->h_addr_list[0]));
	struct sockaddr_in server;
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = indirizzo;
	server.sin_port = htons(porta);
	connect(s, (struct sockaddr *) &server, sizeof(server));
	printf("connection to %s at port %d\n", nome_host, porta);
	if(fruizione_servizio(s) < 0)
	{
		return -1;
	}
	printf("close connection\n");
	close(s);
	return 0;
}
