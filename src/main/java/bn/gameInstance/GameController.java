package bn.gameInstance;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameController implements Runnable {
  
  private int otherPort = 15000;
  private int selfPort = 15001; // Set default port
  private String hostAddress = "localhost";
  private ServerSocket serverSocket;
  private UXController uXController;
  private final int CMD_PLAY = 1;
  private final int CMD_WAIT = 2;
  private final int CMD_HIT = 3;
  private final int CMD_FINISH = 4;
  private final int CDM_PREPSTART = 5;
  private final int CDM_PREPEND = 6;

  public GameController(UXController uXController) {
    this.uXController = uXController;
  }

  @Override
  public void run() {
    try {
      System.out.println("vroom");
      serverSocket = new ServerSocket(selfPort);
      
      //Listeners////////////////////////////////////
      while (true) {
        Socket socket = serverSocket.accept();

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        
        int cmd = inputStream.readInt();
        System.out.println("Received command: " + cmd);
        switch (cmd) {
          case CDM_PREPSTART:
            
            System.out.println("wait deu");
            break;
          case CMD_PLAY:
            uXController.myPlay();
            break;
          case CMD_WAIT:
            //stateMachine.otherPlay();
            System.out.println("wait deu");
            break;
          case CMD_HIT:
            int x = inputStream.readInt();
            int y = inputStream.readInt();
            boolean hit = uXController.otherPlayHit(x, y);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeBoolean(hit);
            outputStream.close();
            break;
          case CMD_FINISH:
            uXController.OtherEnd();
            break;
        }
        inputStream.close();
        socket.close();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Callers////////////////////////////////////
  public void myPlayFinished() {
    sendCommand(CMD_PLAY);
    uXController.otherPlay();
  }

  public void myPlayStarted() {
    sendCommand(CMD_WAIT);
    uXController.myPlay();
  }
  
  public void myFinish() {
    sendCommand(CMD_FINISH);
  }

  public boolean otherHit(int x, int y) {
    try {

      Socket socket = new Socket(hostAddress, otherPort);
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
      
      sendCommand(CMD_HIT);
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
  
  private void sendCommand(int cmd) {
    try {
      Socket socket = new Socket(hostAddress, otherPort);
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      outputStream.writeInt(cmd);
      outputStream.close();
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void prepPhaseStarted(){
    sendCommand(CDM_PREPSTART);
  }

}
