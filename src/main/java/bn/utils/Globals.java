package bn.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Eduardo Santos
 */
public class Globals {

  public static final String RED = "#360808";
  public static final String BLUE = "#0d3e8c";
  public static final String GREEN = "#0a3608";
  public static final String GREY = "#383b37";
  public static final String YELLOW = "#b5aa2f";
  public static final String PINK = "#7a297d";
  public static final String ORANGE = "#a66f1e";
  public static final String PURPLE = "#531f7d";

  public static final int GRID_SIZE = 10;

  public static final int CARRIER_SIZE = 5;
  public static final int CRUISER_SIZE = 4;
  public static final int DESTROYER_SIZE = 3;
  public static final int SUBMARINE_SIZE = 2;

  public static final int CARRIER_COUNT = 1;
  public static final int CRUISER_COUNT = 2;
  public static final int DESTROYER_COUNT = 3;
  public static final int SUBMARINE_COUNT = 4;

  public static final String CARRIER_COLOR = YELLOW;
  public static final String CRUISER_COLOR = PINK;
  public static final String DESTROYER_COLOR = ORANGE;
  public static final String SUBMARINE_COLOR = PURPLE;

  public static final String[][] BOAT_DATA = {
    {"Carrier", Integer.toString(CARRIER_COUNT), Integer.toString(CARRIER_SIZE)},
    {"Cruiser", Integer.toString(CRUISER_COUNT), Integer.toString(CRUISER_SIZE)},
    {"Destroyer", Integer.toString(DESTROYER_COUNT), Integer.toString(DESTROYER_SIZE)},
    {"Submarine", Integer.toString(SUBMARINE_COUNT), Integer.toString(SUBMARINE_SIZE)} 
  };

  //not implemented
  public static final String[][] ATTACK_DATA = {
    {"Normal attack", "-1"},
    {"Line attack", "1"},
    {"Area attack", "1"}
  };

  public static final int MAX_POSITIVE_HITS = maxPositiveHits();

  private static int maxPositiveHits() {
    int res = 0;
    for (String[] boat : BOAT_DATA) {
      int count = Integer.parseInt(boat[1]);
      int size = Integer.parseInt(boat[2]);
      res += count * size;
    }
    return res;
  }

  public static void errorMessage(String msg) {
    String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    System.err.println("[" + timeStamp + "] ERROR: " + msg);
  }
}
