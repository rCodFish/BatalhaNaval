package bn.data.boat;

/**
 *
 * @author Eduardo Santos
 */
public abstract class Boat {

  private int size;
  private int[] startingCoordinates = new int[2];
  private int[] endingCoordinates = new int[2];

  public Boat(int size, int xIni, int yIni, int xEnd, int yEnd) throws Exception {
    this.size = size;

    if (1 == 0/*(xIni + size - 1) != xEnd && (yIni + size - 1) != yEnd*/) {
      throw new Exception("Invalid coordinates");
    }

    startingCoordinates[0] = xIni;
    startingCoordinates[1] = yIni;
    endingCoordinates[0] = xEnd;
    endingCoordinates[1] = yEnd;
  }

  public int getSize() {
    return size;
  }

  public boolean isVertical() {
    return startingCoordinates[0] == endingCoordinates[0];
  }

  public int[] getStartingCoordinates() {
    return startingCoordinates;
  }

  public int[] getEndingCoordinates() {
    return endingCoordinates;
  }
}
