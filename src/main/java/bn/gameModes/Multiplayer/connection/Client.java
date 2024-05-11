package bn.gameModes.Multiplayer.connection;

import bn.data.grid.status.StatusObj;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Eduardo Santos
 */
public class Client {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to host.");

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendData(Object data) {
        // Send data to host
    }

    public Object receiveData() {
        // Receive data from host
        return null; // Replace with actual received data
    }

    // Other methods as needed
}