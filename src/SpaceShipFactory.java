import oop.ex2.*;

/**
 *
 */
public class SpaceShipFactory {
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] result = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "h":
                    result[i] = new ShipHuman();
                    break;
                case "r":
                    result[i] = new ShipRunner();
                    break;
                case "b":
                    result[i] = new ShipBasher();
                    break;
                case "a":
                    result[i] = new ShipAggressive();
                    break;
                case "d":
                    result[i] = new ShipDrunkard();
                    break;
                case "s":
                    result[i] = new ShipSpecial();
                    break;
            }
        }
        return result;
    }
}
