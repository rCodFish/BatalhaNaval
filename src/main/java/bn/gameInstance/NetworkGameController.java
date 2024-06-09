package bn.gameInstance;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Platform;

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
  private final int CMD_READY_TO_START = 5;
  private final int CMD_HIT_RESPONSE = 6;

  private Thread connectionThread;

  private boolean stopRequested = false;

  public NetworkGameController(GameEngine gameInstance, int selfPort, String otherAddress, int otherPort) {
    super(gameInstance);

    this.selfPort = selfPort;
    this.otherAddress = otherAddress;
    this.otherPort = otherPort;
  }

  private void sendCommand(int cmd) {
    try {
      System.out.println("Sending command " + cmd + " to " + otherAddress + ":" + otherPort);

      Socket socket = new Socket(otherAddress, otherPort);
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      outputStream.writeInt(cmd);
      outputStream.flush();
      outputStream.close();

      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void start() throws Exception {
    connectionThread = new Thread(this);
    connectionThread.start();
  }

  @Override
  public void stop() throws Exception {
    if (connectionThread != null) {
      stopRequested = true;
      connectionThread.interrupt();
    }
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
            case CMD_READY_TO_START:
              receive_otherFinishedPrep();
              break;

            case CMD_PLAY:
              receive_imPlaying();
              break;

            case CMD_WAIT:
              receive_otherPlaying();
              break;

            case CMD_HIT:
              int x = inputStream.readInt();
              int y = inputStream.readInt();

              receive_otherHit(x, y);

              break;

            case CMD_FINISH:
              receive_otherFinishedGame();
              break;
              
            case CMD_HIT_RESPONSE:
              boolean hit = inputStream.readBoolean();
              
              receive_hitResponse(hit);
              
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

//Throwers////////////////////////////////////
  @Override
  public void send_myPlayFinished() {
    sendCommand(CMD_PLAY);
  }

  @Override
  public void send_myPlayStarted() {
    sendCommand(CMD_WAIT);
  }

  @Override
  public void send_myGameFinished() {
    sendCommand(CMD_FINISH);
  }

  @Override
  public void send_myPrepFinished() {
    sendCommand(CMD_READY_TO_START);
  }

  @Override
  public void send_hitOther(int x, int y) {
    try {
      Socket socket = new Socket(otherAddress, otherPort);
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

      System.out.println("Sending command " + CMD_HIT + " to " + otherAddress + ":" + otherPort);

      outputStream.writeInt(CMD_HIT);
      outputStream.writeInt(x);
      outputStream.writeInt(y);
      outputStream.flush();
      outputStream.close();

      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void send_hitResponse(boolean hasHit) {
    try {
      Socket socket = new Socket(otherAddress, otherPort);
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      
      outputStream.writeInt(CMD_HIT_RESPONSE);
      outputStream.writeBoolean(hasHit);
      outputStream.flush();
      outputStream.close();

      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  
//Receivers////////////////////////////////////
  @Override
  public void receive_otherFinishedPrep() {
    Platform.runLater(() -> {
      gameInstance.getUXController().otherRdyToTransition();
    });
  }

  @Override
  public void receive_imPlaying() {
    Platform.runLater(() -> {
      gameInstance.getUXController().imPlaying();
    });
  }

  @Override
  public void receive_otherPlaying() {
    Platform.runLater(() -> {
      gameInstance.getUXController().otherPlaying();
    });
  }

  @Override
  public void receive_otherHit(int x, int y) {
    Platform.runLater(() -> {
      gameInstance.getUXController().otherHit(x, y);
    });
  }

  @Override
  public void receive_otherFinishedGame() {
    Platform.runLater(() -> {
      gameInstance.getUXController().otherPlaying();
    });
  }

  @Override
  public void receive_hitResponse(boolean hit) {
    Platform.runLater(() -> {
      gameInstance.getUXController().otherHitResponse(hit);
    });
  }
}
