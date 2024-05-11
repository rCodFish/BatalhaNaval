package bn.gameModes.Multiplayer.connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Eduardo Santos
 */
public class Host {
    private Socket socket;
    private DataOutputStream out;

    public Host(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to client.");

            out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendData(Object data) {
        // Send data to client
    }

    // Other methods as needed
}
