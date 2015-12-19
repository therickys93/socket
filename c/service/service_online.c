/**
 * service_online.c
 *
 * Riccardo Crippa
 * therickys93@gmail.com
 *
 * Insert here your comments
 */
 
 #include <stdio.h>
 #include <netdb.h>
 #include <sys/socket.h>
 #include <netinet/in.h>
 
 int main(int argc, char **argv)
 {
	 if(argc != 4)
	 {
         printf("usage: %s [hostname] [servicename] [protocol]\n", argv[0]);
		 printf("example: %s www.google.com http tcp\n", argv[0]);
         return 1;
	 }
     
     struct sockaddr_in indServer;
     
     struct hostent *hostinfo = gethostbyname(argv[1]);
     if(hostinfo == NULL)
     {
         printf("do not exist that host\n");
         return 2;
     }
     struct servent *service = getservbyname(argv[2], argv[3]);
     if(service == NULL)
     {
         printf("do not exist that service\n");
         return 3;
     }
     indServer.sin_family = AF_INET;
     indServer.sin_port = service->s_port;
     indServer.sin_addr.s_addr = *((in_addr_t *)(hostinfo->h_addr_list[0]));
     printf("port: %u\n", indServer.sin_port);
     printf("server: %u\n", indServer.sin_addr.s_addr);
     return 0;
 }
