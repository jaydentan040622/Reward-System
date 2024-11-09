package Assignment;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class NetworkEntity {
    protected Socket socket = null;
    protected DataInputStream input = null;
    protected DataOutputStream out = null;

    public NetworkEntity(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to " + address + " on port " + port);

            // Initialize input and output streams
            input = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println("Unknown host: " + u.getMessage());
        } catch (IOException i) {
            System.out.println("I/O Exception: " + i.getMessage());
        }
    }

    public abstract void handleConnection();

    protected void closeConnections() {
        try {
            if (input != null)
                input.close();
            if (out != null)
                out.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            System.out.println("Error closing connections: " + e.getMessage());
        }
    }
}
