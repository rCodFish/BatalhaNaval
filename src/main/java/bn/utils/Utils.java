
package bn.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Eduardo Santos
 */
public class Utils {
  static public void errorMessage(String msg) {
    String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    
    System.err.println("[" + timeStamp + "] ERROR: " + msg);
  }
}
