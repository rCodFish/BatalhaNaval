package bn.gameInstance;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameController implements Runnable {

  private int port = 15000; // Set default port
  private String hostAddress = "193.137.97.169";
  private ServerSocket serverSocket;
  private UXController stateMachine;
  private final int CMD_PLAY = 1;
  private final int CMD_WAIT = 2;
  private final int CMD_HIT = 3;
  private final int CMD_FINISH = 4;

  public GameController(UXController stateMachine) {
    this.stateMachine = stateMachine;
  }

  @Override
  public void run() {
    try {
      serverSocket = new ServerSocket(port);
      
      //Listeners////////////////////////////////////
      while (true) {
        Socket socket = serverSocket.accept();

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        
        int cmd = inputStream.readInt();
        switch (cmd) {
          case CMD_PLAY:
            stateMachine.myPlay();
            break;
          case CMD_WAIT:
            stateMachine.otherPlay();
            System.out.println("wait deu");
            break;
          case CMD_HIT:
            int x = inputStream.readInt();
            int y = inputStream.readInt();
            boolean hit = stateMachine.otherPlayHit(x, y);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeBoolean(hit);
            break;
          case CMD_FINISH:
            stateMachine.OtherEnd();
            break;
        }
        socket.close();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Callers////////////////////////////////////
  public void myPlayFinished() {
    try {

      Socket socket = new Socket(hostAddress, port);
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      outputStream.writeInt(CMD_PLAY);
      socket.close();

      stateMachine.otherPlay();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void myPlayStarted() {
    try {

      Socket socket = new Socket(hostAddress, port);
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      outputStream.writeInt(CMD_WAIT);
      socket.close();

      stateMachine.myPlay();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean otherHit(int x, int y) {
    try {

      Socket socket = new Socket(hostAddress, port);
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

      outputStream.writeInt(CMD_HIT);
      outputStream.writeInt(x);
      outputStream.writeInt(y);

      boolean hasHit = inputStream.readBoolean();

      socket.close();
      return hasHit;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }
  
  public void myFinish() {
    try {

      Socket socket = new Socket(hostAddress, port);
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      outputStream.writeInt(CMD_FINISH);
      socket.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
