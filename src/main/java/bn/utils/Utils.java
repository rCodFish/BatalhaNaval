
package bn.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Eduardo Santos
 */
public class Utils {
  public static final String RED = "#360808";
  public static final String BLUE = "#0d3e8c";
  public static final String GREEN = "#0a3608";
  public static final String GREY = "#383b37";
  public static final String YELLOW = "#bda404";
  public static final String PINK = "#9e0fab";
  public static final String ORANGE = "#c26f0a";
  public static final String PURPLE = "#6207a3";
  
  static public void errorMessage(String msg) {
    String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    
    System.err.println("[" + timeStamp + "] ERROR: " + msg);
  }
}
