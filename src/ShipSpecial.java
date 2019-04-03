/**
 * This class represents a ship that has a unique behavior: it is a bully, picking on the weakest ships. Each game it
 * checks which is the weakest and most coward ship out there by this order (from weakest to strongest): Runner,
 * Drunkard, Human, Basher, Aggressive, Special. Thereafter, it assigns it as its target and attempts to bash and shoot
 * it. It has unlimited teleporting abilities, and if the closest ship is not the target, it will teleport until found.
 */
public class ShipSpecial extends SpaceShip {
    /* Class members - constant variables */
    private static final String IMG_DEFAULT = "earth_default.png"; // This ship's default image.
    private static final String IMG_SHIELD = "earth_shield.png"; // This ship's image when shields are up.
    private final String AGGRESSIVE_SYMBOL = "a"; // Aggressive ship letter symbol.
    private final String BASHER_SYMBOL = "b"; // Basher ship letter symbol.
    private final String DRUNKARD_SYMBOL = "d"; // Drunkard ship letter symbol.
    private final String HUMAN_SYMBOL = "h"; // Human ship letter symbol.
    private final String RUNNER_SYMBOL = "r"; // Runner ship letter symbol.
    private final int TELEPORT_INTERVAL = 10; // Rounds intervals between each teleportation.
    /* Class members - variables */
    private Class target; // This game's target ship's class.
    private int lastTeleport; // Last turn ship teleported.

    /* Constructors */

    /**
     * Constructor that initiates default spaceship values in parent class, and sets this class's images and target.
     *
     * @param ships Passed from SpaceShipFactory: containing this game's SpaceShip representation letters.
     */
    public ShipSpecial(String[] ships) {
        super(IMG_DEFAULT, IMG_SHIELD); // Call parent constructor with ship's images.
        this.target = this.whoToBully(ships); // Assign this game's target with dedicated method.
        this.lastTeleport = -1;
    }

    /* Public instance Methods */

    /**
     * Overrides the teleport method from SpaceShip, to avoid energy cost, thus allowing ship to have unlimited
     * teleporting abilities.
     */
    @Override
    public void teleport() {
        // Attempts to teleport if has not teleported in specified interval.
        if (this.lastTeleport < 0 || (this.getTurnCounter() - this.lastTeleport) > this.TELEPORT_INTERVAL)
            this.resetShipPhysics(); // Teleport by creating a new SpaceShipPhysics object, without cost.
    }

    /* Protected instance Methods */

    /**
     * Does the specified actions assigned to the special ship, according to the target's location.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    protected void actions(SpaceWars game) {
        // Actions to do if closest ship is the target.
        if (this.isTarget(game)) {
            this.getPhysics().move(true, this.turnShipAuto(game, 1)); // Move and accelerate towards the target.
            this.fire(game); // Attempt to fire shots towards the target.
        } else
            this.teleport(); // Closest ship is not target, attempt to teleport.
    }

    /* Private instance Methods */

    /**
     * Checks if closest ship is belongs to the target class.
     *
     * @param game the game object to which this ship belongs.
     * @return True if closest ship is belongs to the target class, false if otherwise.
     */
    private boolean isTarget(SpaceWars game) {
        SpaceShip closestShip = game.getClosestShipTo(this); // Call SpaceWars method to find closest ship object.
        return closestShip.getClass() == this.target; // Compare closest ship class and target class.
    }

    /**
     * Checks which class to assign as target for this game, based on the following hierarchy: (1) Runner (2) Drunkard
     * (3) Human (4) Basher (5) Aggressive (6) Special.
     *
     * @param ships Passed from SpaceShipFactory: containing this game's SpaceShip representation letters.
     * @return Class of ship to bully in this game.
     */
    private Class whoToBully(String[] ships) {
        if (this.inArray(ships, this.RUNNER_SYMBOL))
            return ShipRunner.class;
        else if (this.inArray(ships, this.DRUNKARD_SYMBOL))
            return ShipDrunkard.class;
        else if (this.inArray(ships, this.HUMAN_SYMBOL))
            return ShipHuman.class;
        else if (this.inArray(ships, this.BASHER_SYMBOL))
            return ShipBasher.class;
        else if (this.inArray(ships, this.AGGRESSIVE_SYMBOL))
            return ShipAggressive.class;
        else
            return ShipSpecial.class;
    }

    /**
     * Helper method for whoToBully() that checks if given letter exists in array.
     *
     * @param ships  Passed from SpaceShipFactory: containing this game's SpaceShip representation letters.
     * @param letter The specified letter to check existence of.
     * @return True if letter exists in the array, false otherwise.
     */
    private boolean inArray(String[] ships, String letter) {
        for (String arg : ships)
            if (arg.equals(letter))
                return true;
        return false;
    }
}
