package bn.connection;

import bn.data.grid.status.StatusObj;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author Eduardo Santos
 */
public class Incoming implements Runnable {

  private Socket socket;
  private ObjectInputStream inputStream;
  private boolean incomingPending;
  private StatusObj statusObj;

  public Incoming(Socket socket) {
    this.socket = socket;
    incomingPending = false;

    try {
      inputStream = new ObjectInputStream(socket.getInputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void run() {
    while (true/*!stateMachine.conditions()*/) {
      Object receivedObject = null;
      
      try {
        receivedObject = inputStream.readObject();
      } catch (Exception e) {
        e.printStackTrace();
      }

      if (receivedObject instanceof StatusObj) {
        StatusObj receivedStatusObj = (StatusObj) receivedObject;

        synchronized (this) {
          if (!incomingPending) {
            this.statusObj = receivedStatusObj;
            incomingPending = true;
          }
        }

        try {
          Thread.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }

      } else {
        System.err.println("Received object is not of type StatusObj");
      }
    }

  }

  public synchronized boolean isIncomingPending() {
    return incomingPending;
  }

  public synchronized StatusObj getPendingStatusObj() {
    incomingPending = false;
    return statusObj;
  }
}
