/**
 * This class is used by the driver to create all the spaceship objects according to the command line arguments.
 */
public class SpaceShipFactory {
    /* Class members - static constant variables */
    private static final String AGGRESSIVE_SYMBOL = "a"; // Aggressive ship letter symbol.
    private static final String BASHER_SYMBOL = "b"; // Basher ship letter symbol.
    private static final String DRUNKARD_SYMBOL = "d"; // Drunkard ship letter symbol.
    private static final String HUMAN_SYMBOL = "h"; // Human ship letter symbol.
    private static final String RUNNER_SYMBOL = "r"; // Runner ship letter symbol.
    private static final String SPECIAL_SYMBOL = "s"; // Special ship letter symbol.

    /* Static Methods */

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
                case AGGRESSIVE_SYMBOL:
                    result[i] = new ShipAggressive();
                    break;
                case BASHER_SYMBOL:
                    result[i] = new ShipBasher();
                    break;
                case DRUNKARD_SYMBOL:
                    result[i] = new ShipDrunkard();
                    break;
                case HUMAN_SYMBOL:
                    result[i] = new ShipHuman();
                    break;
                case RUNNER_SYMBOL:
                    result[i] = new ShipRunner();
                    break;
                case SPECIAL_SYMBOL:
                    result[i] = new ShipSpecial(args);
                    break;
                default:
                    return null;
            }
        return result;
    }
}
