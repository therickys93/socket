/**
 * nslookup.c
 *
 * Riccardo Crippa
 * therickys93@gmail.com
 *
 * Insert here your comments
 */
 
 #include <netdb.h>
 #include <arpa/inet.h>
 #include <stdio.h>
 #include <string.h>
 
 int main(int argc, char **argv)
 {
	 u_long address;
	 if(argc != 3)
	 {
		 printf("Error: number of parameters incorrect\n");
		 return 1;
	 }
	 else if(strcmp(argv[1], "s") == 0)
	 {
		 char *hostname = argv[2];
		 struct hostent* hostinfo;
		 hostinfo = gethostbyname(hostname);
		 if(hostinfo == NULL)
		 {
			 printf("Error: No host found with that name\n");
			 return 2; 
		 }
		 struct in_addr castDot;
		 castDot.s_addr = *((u_long *) (hostinfo->h_addr_list[0]));
		 printf("address: %u\n", castDot.s_addr);
	 }
	 else if(strcmp(argv[1], "d") == 0)
	 {
		 address = inet_addr(argv[2]);
		 if(address == INADDR_NONE)
		 {
			 printf("Error: inet_address\n");
			 return 3;
		 }
		 printf("address: %lu\n", address);
	 }
	 else
	 {
		 printf("usage: %s s [www.example.com]\n", argv[0]);
		 printf("usage: %s d [192.168.1.4]\n", argv[0]);
		 return 4;
	 }
	 return 0;
 }
