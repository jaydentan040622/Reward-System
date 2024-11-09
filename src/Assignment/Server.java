package Assignment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends NetworkEntity {
    private BufferedReader serverInput = null;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    public Server(int port) {
        super("localhost", port);
        serverInput = new BufferedReader(new InputStreamReader(System.in));
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            threadPool = Executors.newCachedThreadPool(); // Creates a thread pool
        } catch (IOException e) {
            System.out.println("Error creating server socket: " + e.getMessage());
        }
    }

    @Override
    public void handleConnection() {
        try {
            while (true) {
                System.out.println("Waiting for a client ...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: \n" + clientSocket);

                // Submit the client handling task to the thread pool
                threadPool.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("Error accepting client connection: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            String clientMessage;
            while ((clientMessage = in.readUTF()) != null) {
                System.out.println("Client: " + clientMessage + "\n");
                String response = serverInput.readLine();
                out.writeUTF(response);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("\u001B[31mError handling client: " + e.getMessage() + "\u001B[0m");
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String line;
            while ((line = in.readUTF()) != null) {
                System.out.println("Client: " + line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }


}