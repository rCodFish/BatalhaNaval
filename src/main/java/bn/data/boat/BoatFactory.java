package bn.data.boat;

/**
 *
 * @author Eduardo Santos & Luís Amândio
 */
public class BoatFactory {

  public static Boat createBoat(String boatType, int xIni, int yIni, int xEnd, int yEnd) {
    try {
      switch (boatType) {
        case "Carrier":
          return new Carrier(xIni, yIni, xEnd, yEnd);
        case "Cruiser":
          return new Cruiser(xIni, yIni, xEnd, yEnd);
        case "Destroyer":
          return new Destroyer(xIni, yIni, xEnd, yEnd);
        case "Submarine":
          return new Submarine(xIni, yIni, xEnd, yEnd);
        default:
          throw new IllegalArgumentException("Invalid boat type");
      }
    } catch (Exception e) {
      System.out.println("Error creating boat: " + e.getMessage());
      return null;
    }
  }
}
