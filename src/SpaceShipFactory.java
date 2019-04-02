/**
 * This class is used by the driver to create all the spaceship objects according to the command line arguments.
 */
public class SpaceShipFactory {
    /**
     * Creates all the spaceship objects according to the letters passed in the command line arguments.
     *
     * @param args Containing SpaceShip representation letters, passed in the command line arguments.
     * @return Array with this game's SpaceShip objects.
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] result = new SpaceShip[args.length]; // Assign empty SpaceShip array for results.
        // Assign each field to object of given letter.
        for (int i = 0; i < args.length; i++)
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
                    result[i] = new ShipSpecial(args);
                    break;
                default:
                    return null;
            }
        return result;
    }
}
