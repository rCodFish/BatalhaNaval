package bn.gameModes.Multiplayer.sameNetworkConnection;

import bn.data.grid.status.StatusObj;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {

  private boolean isHost;
  private int port = 15000;
  private String hostAddress;
  private ServerSocket serverSocket;
  private Socket socket;
  private ObjectInputStream inputStream;
  private ObjectOutputStream outputStream;

  public Connection(boolean isHost, String hostAddress) {
    this.isHost = isHost;
    this.hostAddress = hostAddress;
  }

  public void startConnecting() {
    Thread connectionThread = new Thread(() -> {
      try {
        if (isHost) {

          System.out.println("Host: Waiting for connections...");
          serverSocket = new ServerSocket(port);
          socket = serverSocket.accept();
          System.out.println("Host: Connection established with client!");

        } else {

          System.out.println("Client: Connecting to host...");
          socket = new Socket(hostAddress, port);
          System.out.println("Client: Connected to host!");

        }

        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    connectionThread.start();
  }

  public void sendStatusObj(StatusObj statusObj) {
    try {
      outputStream.writeObject(statusObj);
      outputStream.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public StatusObj receiveStatusObj() {
    try {
      return (StatusObj) inputStream.readObject();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void closeConnection() {
    try {
      if (socket != null) {
        socket.close();
      }
      if (outputStream != null) {
        outputStream.close();
      }
      if (inputStream != null) {
        inputStream.close();
      }
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
