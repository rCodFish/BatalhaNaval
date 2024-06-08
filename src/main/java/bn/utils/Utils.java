
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
  
  static public void errorMessage(String msg) {
    String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    
    System.err.println("[" + timeStamp + "] ERROR: " + msg);
  }
}
