package bn.gameInstance;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkGameController extends LogicController implements Runnable {

  public static final int DEFAULT_SERVER_PORT = 15000;

  private int selfPort = DEFAULT_SERVER_PORT;

  private String otherAddress = "localhost";
  private int otherPort = DEFAULT_SERVER_PORT;

  private ServerSocket serverSocket;
  private final int CMD_PLAY = 1;
  private final int CMD_WAIT = 2;
  private final int CMD_HIT = 3;
  private final int CMD_FINISH = 4;
  private final int CDM_READY_TO_START = 5;

  private Thread connectionThread;

  private boolean stopRequested = false;

  public NetworkGameController(GameEngine gameInstance, int selfPort, String otherAddress, int otherPort) {
    super(gameInstance);

    this.selfPort = selfPort;
    this.otherAddress = otherAddress;
    this.otherPort = otherPort;
  }

  @Override
  public void run() {
    try {
      serverSocket = new ServerSocket(selfPort);

      //Listeners////////////////////////////////////
      while (!stopRequested) {
        Socket socket = null;
        ObjectInputStream inputStream = null;

        try {
          socket = serverSocket.accept();

          inputStream = new ObjectInputStream(socket.getInputStream());

          int cmd = inputStream.readInt();

          System.out.println("Command " + cmd + " received");

          switch (cmd) {
            case CDM_READY_TO_START:
              gameInstance.getLogicController().otherPartyFinishedPrep();
              break;

            case CMD_PLAY:
              gameInstance.getLogicController().imPlaying();
              break;

            case CMD_WAIT:
              gameInstance.getLogicController().otherPlaying();
              break;

            case CMD_HIT:
              int x = inputStream.readInt();
              int y = inputStream.readInt();

              boolean hit = gameInstance.getLogicController().otherPlayHit(x, y);

              ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
              outputStream.writeBoolean(hit);
              outputStream.close();
              break;

            case CMD_FINISH:
              gameInstance.getLogicController().otherPartyFinishedGame();
              break;

            default:
              System.out.println("...Unknown command...");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        if (inputStream != null) {
          try {
            inputStream.close();
          } catch (Exception e) {
          }
        }
        if (socket != null) {
          try {
            socket.close();
          } catch (Exception e) {
          }
        }
      }
      if (serverSocket != null) {
        try {
          serverSocket.close();
        } catch (Exception e) {
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Callers////////////////////////////////////
  public void start() throws Exception {
    connectionThread = new Thread(this);
    connectionThread.start();
  }

  public void stop() throws Exception {
    if (connectionThread != null) {
      stopRequested = true;
      connectionThread.interrupt();
    }
  }

  public void myPlayFinished() {
    sendCommand(CMD_PLAY);

    gameInstance.getUXController().otherPlaying();
  }

  public void myPlayStarted() {
    sendCommand(CMD_WAIT);
    gameInstance.getUXController().imPlaying();
  }

  public void myGameFinished() {
    sendCommand(CMD_FINISH);
  }

  public void myPrepFinished() {
    sendCommand(CDM_READY_TO_START);
    
    gameInstance.getUXController().waitingOtherPartyPrep();
  }

  public boolean hitOther(int x, int y) {
    try {
      Socket socket = new Socket(otherAddress, otherPort);
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

  public void otherPartyFinishedPrep() {
    gameInstance.getUXController().otherPartyFinishedPrep();
  }

  public void imPlaying() {

  }

  public void otherPlaying() {

  }

  public boolean otherPlayHit(int x, int y) {
    return true;
  }

  public void otherPartyFinishedGame() {

  }

  public void otherPartyIsReadyToPlay() {

  }

  private void sendCommand(int cmd) {
    try {
      System.out.println("Sending command " + cmd + " to " + otherAddress + ":" + otherPort);

      Socket socket = new Socket(otherAddress, otherPort);
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      outputStream.writeInt(cmd);
      outputStream.close();
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
