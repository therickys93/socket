/**
 * service_local.c
 *
 * Riccardo Crippa
 * therickys93@gmail.com
 *
 * Insert here your comments
 */
 
 #include <stdio.h>
 #include <netdb.h>
 #include <unistd.h>
 #include <string.h>
 
 int main(int argc, char **argv)
 {
     char name[128];
     struct servent *service;
     
     gethostname(name, sizeof(name));
     printf("My hostname %s\n", name);
     
     if(argc != 3)
     {
         printf("usage: %s [servicename] [protocol]\n", argv[0]);
         printf("example: %s http tcp\n", argv[0]);
         return 1;
     }
     else
     {
         service = getservbyname(argv[1], argv[2]);
         if(service == NULL)
         {
             printf("no service available\n");
             return 2;
         }
         printf("port: %u\n", service->s_port);
         printf("port ok: %u\n", (ntohs(service->s_port)));
     }
     return 0;
 }
 
