package bn.data.boat;

import bn.utils.Globals;

/**
 *
 * @author Eduardo Santos
 */
public class Submarine extends Boat {

  private static final int SIZE = Globals.SUBMARINE_SIZE;
  private static final String COLOR = Globals.SUBMARINE_COLOR;

  public Submarine(int xIni, int yIni, int xEnd, int yEnd) throws Exception {
    super(SIZE, COLOR, xIni, yIni, xEnd, yEnd);
  }

  public static int getSIZE() {
    return SIZE;
  }

  public static String getCOLOR() {
    return COLOR;
  }
}
