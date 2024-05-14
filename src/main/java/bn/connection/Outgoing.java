package bn.connection;

import bn.data.grid.status.StatusObj;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Outgoing implements Runnable {

  private Socket socket;
  private ObjectOutputStream outputStream;
  private StatusObj statusObjToSend;

  public Outgoing(Socket socket) {
    this.socket = socket;

    try {
      outputStream = new ObjectOutputStream(socket.getOutputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setStatusObjToSend(StatusObj statusObj) {
    this.statusObjToSend = statusObj;
  }

  @Override
  public void run() {
    try {
      while (!Thread.currentThread().isInterrupted()) {
        if (statusObjToSend != null) {
          outputStream.writeObject(statusObjToSend);
          outputStream.flush(); 
          statusObjToSend = null;
        }

        Thread.sleep(1000);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        outputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
