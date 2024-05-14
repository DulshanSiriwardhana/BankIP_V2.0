# Setting Up BankIP Communication

Before running the BankIP system, ensure the following steps are completed:

### 1. Check Port Availability
   Verify that port `12345` is not in use on your PC(s). This can be done using a variety of methods depending on your operating system.

### 2. Configure Firewall (If Using Multiple PCs)
   If you are running BankIP on multiple PCs, configure your firewall to allow communication through port `12345`. This ensures seamless communication between the server and clients.

### 3. Set Server IPv4 Address
   In the client files, set the IPv4 address of the server to match the server PC's IPv4 address. This ensures that clients can connect to the correct server.

### 4. Run the Server
   Run the `Server.java` file on the designated server PC. This initializes the BankIP server and prepares it to accept client connections.

### 5. Run Client Files
   For each client, run the respective client file (`Client.java`). This establishes a connection between the client and the server, allowing clients to interact with the BankIP system.

By following these steps, you can set up and run the BankIP system smoothly, enabling secure communication between clients and the server. If you encounter any issues during setup or execution, refer to the troubleshooting section in the documentation or seek assistance from your network administrator.
