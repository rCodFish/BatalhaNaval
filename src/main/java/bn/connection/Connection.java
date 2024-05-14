package bn.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Runnable {

  private boolean isHost;
  private int port = 15000; // Set default port
  private String hostAddress;
  private ServerSocket serverSocket;
  private Socket socket;

  public Connection(boolean isHost) {
    this.isHost = isHost;
  }

  @Override
  public void run() {
    try {
      if (isHost) {

        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        // Connection established, start sending and receiving threads
        Thread outgoing = new Thread(new Outgoing(socket));
        Thread incoming = new Thread(new Incoming(socket));
        outgoing.start();
        incoming.start();

      } else {

        socket = new Socket(hostAddress, port);
        // Connection established, start sending and receiving threads
        Thread outgoing = new Thread(new Outgoing(socket));
        Thread incoming = new Thread(new Incoming(socket));
        outgoing.start();
        incoming.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
