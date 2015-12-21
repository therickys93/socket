/**
 * client.c
 *
 * Riccardo Crippa
 * therickys93@gmail.com
 *
 * server with connectionless service
 */
 
 #include <stdio.h>
 #include <sys/socket.h>
 #include <netdb.h>
 #include <string.h>
 #include <unistd.h>
 
 int main(int argc, char **argv)
 {
     int dominio = AF_INET;
     int tipo = SOCK_DGRAM;
     int protocollo = 0;
     
     int mysock;
     struct protoent *pe;
     struct sockaddr_in indirizzo;
     int r;
     
     pe = getprotobyname("udp");
     if(pe == NULL)
     {
         printf("Error in the protocol\n");
         return 1;
     }
     protocollo = pe->p_proto;
     
     mysock = socket(dominio, tipo, protocollo);
     if(mysock < 0)
     {
         printf("Error in the socket\n");
         return 2;
     }
     
     indirizzo.sin_family = dominio;
     indirizzo.sin_addr.s_addr = INADDR_ANY;
     indirizzo.sin_port = htons(0);
     
     r = bind(mysock, (struct sockaddr *)&indirizzo, sizeof(indirizzo));
     if(r < 0)
     {
         printf("Error in the bind\n");
         return 3;
     }
     
     struct sockaddr_in ind_locale;
     unsigned int indLength = sizeof(ind_locale);
     r = getsockname(mysock, (struct sockaddr *)&ind_locale, (socklen_t *)&indLength);
     if(r < 0)
     {
         printf("Error in the getsock\n");
         return 4;
     }
     printf("indirizzo locale: %d, %d\n", ind_locale.sin_addr.s_addr, ntohs(ind_locale.sin_port));
     
     char stringa[100];
     bzero(stringa, 100);
     struct sockaddr_in indFrom;
     indLength = sizeof(indFrom);
     r = recvfrom(mysock, stringa, sizeof(stringa), 0, (struct sockaddr *)&indFrom, (socklen_t *)&indLength);
     if(r < 0)
     {
         printf("Error in the recvfrom\n");
         return 5;
     }
     printf("ricevuta stringa %s da indirizzo %d, %d\n", stringa, indFrom.sin_addr.s_addr, htons(indFrom.sin_port));
     r = sendto(mysock, stringa, r, 0, (struct sockaddr *)&indFrom, (socklen_t)indLength);
     if(r < 0)
     {
         printf("Error in the sendto\n");
         return 6;
     }
     r = close(mysock);
     if(r < 0)
     {
         printf("Error in the close\n");
         return 7;
     }
     return 0;
 }
