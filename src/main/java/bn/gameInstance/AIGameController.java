package bn.gameInstance;

import bn.data.boat.Boat;
import bn.gui.supportingLogic.GridCellHBox;
import bn.utils.Globals;
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Platform;

public class AIGameController extends LogicController {

  int debug = 0;

  private GridCellHBox[][] playerGridBoxes;
  private int[][] myGrid;
  private int[][] otherGrid;

  private static final int UNKNOWN = 0;
  private static final int TRY = 1;
  private static final int BOAT = 2;
  private static final int WATER = 3;

  private ArrayList<Boat> placedBoats;

  private String[][] boatData = Globals.BOAT_DATA;

  private boolean stopRequested = false;

  private int positiveHits = 0;
  private int maxPositiveHits;

  private Random random = new Random();

  private int nextX = -1;
  private int nextY = -1;
  private int lastGoodX = -1;
  private int lastGoodY = -1;

  public AIGameController(GameEngine gameInstance) {
    super(gameInstance);
  }

  private boolean wasFinalHit() {
    return maxPositiveHits == positiveHits;
  }

  private void initializeMaxHits() {
    for (Boat boat : placedBoats) {
      maxPositiveHits += boat.getSize();
    }
  }

  @Override
  public void start() throws Exception {
    try {
      myGrid = new int[Globals.GRID_SIZE][Globals.GRID_SIZE];
      otherGrid = new int[Globals.GRID_SIZE][Globals.GRID_SIZE];

      for (int x = 0; x < myGrid.length; x++) {
        for (int y = 0; y < myGrid.length; y++) {
          myGrid[x][y] = WATER;
          otherGrid[x][y] = UNKNOWN;
        }
      }

      if (debug >= 2) {
        System.out.println("Place boats:");
      }

      for (String[] entry : boatData) {
        if (debug >= 2) {
          System.out.println(entry[0] + ": #" + entry[1] + " size " + entry[2]);
        }
        for (int i = 0; i < Integer.parseInt(entry[1]); i++) {
          placeBattleship(Integer.parseInt(entry[2]));
        }
      }

      if (debug >= 2) {
        System.out.println("My board:");
        printBoard();
      }

    } catch (Exception e) {
      e.printStackTrace();

      throw e;
    }
  }

  @Override
  public void stop() throws Exception {
  }

  public void placeBattleship(int size) {
    boolean placed = false;
    while (!placed) {
      int x = random.nextInt(myGrid.length);
      int y = random.nextInt(myGrid.length);
      boolean horizontal = random.nextBoolean();

      if (canPlaceShip(x, y, size, horizontal)) {
        for (int i = 0; i < size; i++) {
          if (horizontal) {
            myGrid[x + i][y] = BOAT;
          } else {
            myGrid[x][y + i] = BOAT;
          }
        }
        placed = true;
      }
    }
  }

  private boolean canPlaceShip(int x, int y, int size, boolean horizontal) {
    if (horizontal) {
      if (x + size > myGrid.length) {
        return false;
      }
      for (int i = 0; i < size; i++) {
        if (myGrid[x + i][y] != WATER) {
          return false;
        }
      }
    } else {
      if (y + size > myGrid.length) {
        return false;
      }
      for (int i = 0; i < size; i++) {
        if (myGrid[x][y + i] != WATER) {
          return false;
        }
      }
    }
    return true;
  }

  public void printBoard() {
    System.out.print("| ");
    for (int x = 0; x < myGrid.length; x++) {
      System.out.print("|" + x);
    }
    System.out.println("|");

    for (int y = 0; y < myGrid.length; y++) {
      System.out.print("|" + y);
      for (int x = 0; x < myGrid.length; x++) {
        System.out.print(myGrid[x][y] == BOAT ? "|B" : "| ");
      }
      System.out.println("|");
    }
  }

  private void sendHit() {
    try {
      Thread.sleep(500);
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (debug >= 2) {
      System.out.println("AIGameController.send_myPlayFinished: (" + lastGoodX + ", " + lastGoodY + ")");
    }

    boolean autoDetect = true;
    if (lastGoodX != -1) {
      int tmpNextX = nextY;
      int tmpNextY = nextX;

      boolean mayL2R = false;
      boolean mayR2L = false;
      boolean mayT2D = false;
      boolean mayD2T = false;

      if (lastGoodX >= 0 && lastGoodX < (myGrid.length - 1)) {
        if (otherGrid[lastGoodX + 1][lastGoodY] == UNKNOWN) {
          mayL2R = true;
        }
      }
      if (lastGoodX > 0) {
        if (otherGrid[lastGoodX - 1][lastGoodY] == UNKNOWN) {
          mayR2L = true;
        }
      }
      if (lastGoodY >= 0 && lastGoodY < (myGrid.length - 1)) {
        if (otherGrid[lastGoodX][lastGoodY + 1] == UNKNOWN) {
          mayT2D = true;
        }
      }
      if (lastGoodY > 0) {
        if (otherGrid[lastGoodX][lastGoodY - 1] == UNKNOWN) {
          mayD2T = true;
        }
      }

      if (mayL2R) {
        autoDetect = false;

        nextX = lastGoodX + 1;
        nextY = lastGoodY;
      } else if (mayR2L) {
        autoDetect = false;

        nextX = lastGoodX - 1;
        nextY = lastGoodY;
      } else if (mayT2D) {
        autoDetect = false;

        nextX = lastGoodX;
        nextY = lastGoodY + 1;
      } else if (mayD2T) {
        autoDetect = false;

        nextX = lastGoodX;
        nextY = lastGoodY - 1;
      }

      if (debug >= 3) {
        System.out.println("AIGameController.send_myPlayFinished: (mayL2R=" + mayL2R + ",mayR2L=" + mayR2L + ",mayT2D=" + mayT2D + ",mayD2T=" + mayD2T + ")");
      }
    }

    if (autoDetect) {
      int tries = 10;
      do {
        nextX = random.nextInt(myGrid.length);
        nextY = random.nextInt(myGrid.length);
      } while (tries-- > 0 && otherGrid[nextX][nextY] != UNKNOWN);
    }

    otherGrid[nextX][nextY] = TRY;

    if (debug >= 3) {
      System.out.println("AIGameController.send_myPlayFinished: Simulate other party hit (" + nextX + ", " + nextY + ")");
    }

    receive_otherHit(nextX, nextY);
  }

//Throwers////////////////////////////////////
  @Override
  public void send_myPlayFinished() {
    if (debug >= 2) {
      System.out.println("AIGameController.send_myPlayFinished");
    }

    sendHit();
  }

  @Override
  public void send_myPlayStarted() {
    if (debug >= 2) {
      System.out.println("AIGameController.send_myPlayStarted");
    }
  }

  @Override
  public void send_myGameFinished() {
    if (debug >= 2) {
      System.out.println("AIGameController.send_myGameFinished");
    }
  }

  @Override
  public void send_myPrepFinished() {
    if (debug >= 2) {
      System.out.println("AIGameController.send_myPrepFinished");
    }

    try {
      Thread.sleep(500);
    } catch (Exception e) {
      e.printStackTrace();
    }

    receive_otherFinishedPrep();
  }

  @Override
  public void send_hitOther(int x, int y) {
    if (myGrid[x][y] == BOAT) {
      if (debug >= 2) {
        System.out.println("AIGameController.send_hitOther (" + x + ", " + y + ")=true");
      }

      boolean win = false;

      receive_hitResponse(true, win);
    } else {
      if (debug >= 2) {
        System.out.println("AIGameController.send_hitOther (" + x + ", " + y + ")=false");
      }
      receive_hitResponse(false, false);
    }
  }

  @Override
  public void send_hitResponse(boolean hasHit, boolean win) {
    if (debug >= 2) {
      System.out.println("AIGameController.send_hitResponse(" + hasHit + ")[" + nextX + "," + nextY + "]");
    }

    otherGrid[nextX][nextY] = hasHit ? BOAT : WATER;
    if (!hasHit) {
      receive_imPlaying();
    } else {
      lastGoodX = nextX;
      lastGoodY = nextY;

      sendHit();
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
  public void receive_hitResponse(boolean hit, boolean win) {
    Platform.runLater(() -> {
      gameInstance.getUXController().otherHitResponse(hit, win);
    });
  }
}
