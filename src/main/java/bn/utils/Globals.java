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
  public static final String YELLOW = "#bda404";
  public static final String PINK = "#9e0fab";
  public static final String ORANGE = "#c26f0a";
  public static final String PURPLE = "#6207a3";

  public static final int GRID_SIZE = 10;

  public static final int CARRIER_SIZE = 5;
  public static final int CRUISER_SIZE = 4;
  public static final int DESTROYER_SIZE = 3;
  public static final int SUBMARINE_SIZE = 2;

  public static final String CARRIER_COLOR = YELLOW;
  public static final String CRUISER_COLOR = PINK;
  public static final String DESTROYER_COLOR = ORANGE;
  public static final String SUBMARINE_COLOR = PURPLE;

  public static final String[][] BOAT_DATA = {
    {"Carrier", "1", "5"},
    /*{"Cruiser", "2", "4"},
    {"Destroyer", "3", "3"},
    {"Submarine", "4", "2"}*/
  };

  public static final String[][] ATTACK_DATA = {
    {"Normal attack", "-1"},
    {"Line attack", "1"},
    {"Area attack", "1"}
  };

  public static void errorMessage(String msg) {
    String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    System.err.println("[" + timeStamp + "] ERROR: " + msg);
  }
}
