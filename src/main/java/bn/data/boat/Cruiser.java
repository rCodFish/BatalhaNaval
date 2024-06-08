package bn.data.boat;

import bn.utils.Utils;

/**
 *
 * @author Eduardo Santos
 */
public class Cruiser extends Boat {

  private static final int SIZE = 3;
  private static final String COLOR = Utils.PINK;

  public Cruiser(int xIni, int yIni, int xEnd, int yEnd) throws Exception {
    super(SIZE, xIni, yIni, xEnd, yEnd);
  }

  public static int getSIZE() {
    return SIZE;
  }

  public static String getCOLOR() {
    return COLOR;
  }
}
