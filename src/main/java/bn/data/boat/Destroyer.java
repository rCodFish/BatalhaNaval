package bn.data.boat;

/**
 *
 * @author Eduardo Santos
 */
public class Destroyer extends Boat {

  private static final int SIZE = 2;

  public Destroyer(int xIni, int yIni, int xEnd, int yEnd) throws Exception {
    super(SIZE, xIni, yIni, xEnd, yEnd);
  }

  public static int getSIZE() {
    return SIZE;
  }
}
