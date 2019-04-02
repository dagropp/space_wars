/**
 * This class represents a ship that its pilot had a tad too much to drink. We leave it to your creativity to define
 * the ship’s exact behavior, but it must include randomness and should definitely be amusing to fight against.
 */
public class ShipDrunkard extends SpaceShip {
    private static final String IMG_DEFAULT = "avocado_default.png"; // This ship's default image.
    private static final String IMG_SHIELD = "avocado_shield.png"; // This ship's image when shields are up.

    /**
     * Constructor that initiates default spaceship values in parent class, and sets this class's images.
     */
    public ShipDrunkard() {
        super(ShipDrunkard.IMG_DEFAULT, ShipDrunkard.IMG_SHIELD); // Call parent constructor with ship's images.
    }

    /**
     * Does the specified actions assigned to the drunkard ship, according to random booleans.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    protected void actions(SpaceWars game) {
        // Generate random booleans and if any true, then:
        if (this.randomBool())
            this.teleport(); // Attempt to teleport.
        this.moveShip(); // Attempt to move ship.
        if (this.randomBool())
            this.shieldOn(); // Attempt to turn on shield.
        if (this.randomBool())
            this.fire(game); // Attempt to fire a shot.
    }

    /**
     * Moves ship according to random booleans.
     */
    private void moveShip() {
        boolean right = this.randomBool(); // True/false according to random boolean.
        boolean left = this.randomBool(); // True/false according to random boolean.
        boolean accelerate = this.randomBool(); // True/false according to random boolean.
        int direction = this.turnShipManual(right, left); // Assign direction number with dedicated method.
        this.shipPhysics.move(accelerate, direction); // Move to assigned direction, accelerate if true.
    }

    /**
     * Using Math.random, generate random boolean. Generates number in range of 0-1, rounds it to whole number and
     * return its value compared to 1.
     *
     * @return True/false randomly.
     */
    private boolean randomBool() {
        return Math.round(Math.random()) == 1;
    }
}
