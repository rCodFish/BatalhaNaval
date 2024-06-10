package bn.data.boat;

import bn.utils.Globals;

/**
 *
 * @author Eduardo Santos & Luís Amândio
 */
public class Cruiser extends Boat {

  private static final int SIZE = Globals.CRUISER_SIZE;
  private static final String COLOR = Globals.CRUISER_COLOR;

  public Cruiser(int xIni, int yIni, int xEnd, int yEnd) throws Exception {
    super(SIZE, COLOR, xIni, yIni, xEnd, yEnd);
  }

  public static int getSIZE() {
    return SIZE;
  }

  public static String getCOLOR() {
    return COLOR;
  }
}
