package bn.data.boat;

import bn.utils.Globals;

/**
 *
 * @author Eduardo Santos
 */
public class Carrier extends Boat {

  private static final int SIZE = Globals.CARRIER_SIZE;
  private static final String COLOR = Globals.CARRIER_COLOR;

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
