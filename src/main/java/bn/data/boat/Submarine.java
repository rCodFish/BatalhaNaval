package bn.data.boat;

import bn.utils.Utils;

/**
 *
 * @author Eduardo Santos
 */
public class Submarine extends Boat {

  private static final int SIZE = 2;
  private static final String COLOR = Utils.PURPLE;

  public Submarine(int xIni, int yIni, int xEnd, int yEnd) throws Exception {
    super(SIZE, xIni, yIni, xEnd, yEnd);
  }

  public static int getSIZE() {
    return SIZE;
  }

  public static String getCOLOR() {
    return COLOR;
  }
}
