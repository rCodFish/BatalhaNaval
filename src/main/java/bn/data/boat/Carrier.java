package bn.data.boat;

import bn.utils.Utils;

/**
 *
 * @author Eduardo Santos
 */
public class Carrier extends Boat {

  private static final int SIZE = 5;
  private static final String COLOR = Utils.YELLOW;

  public Carrier(int xIni, int yIni, int xEnd, int yEnd) throws Exception {
    super(SIZE, xIni, yIni, xEnd, yEnd);
  }

  public static int getSIZE() {
    return SIZE;
  }
  
  public static String getCOLOR() {
    return COLOR;
  }
}
