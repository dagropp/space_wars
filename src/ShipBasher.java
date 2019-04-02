/**
 * This class represents a ship that attempts to collide with other ships. It will always accelerate, and will
 * constantly turn towards the closest ship. If it gets within a distance of 0.19 units (inclusive) from another ship,
 * it will attempt to turn on its shields.
 */
public class ShipBasher extends SpaceShip {
    private static final String IMG_DEFAULT = "violet_default.png"; // This ship's default image.
    private static final String IMG_SHIELD = "violet_shield.png"; // This ship's image when shields are up.
    private final double SHIELD_DISTANCE = 0.19; // Ship's minimal distance to when it turns shield on.

    /**
     * Constructor that initiates default spaceship values in parent class, and sets this class's images.
     */
    public ShipBasher() {
        super(ShipBasher.IMG_DEFAULT, ShipBasher.IMG_SHIELD); // Call parent constructor with ship's images.
    }

    /**
     * Does the specified actions assigned to the basher ship, according to closest ship distance.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    protected void actions(SpaceWars game) {
        this.getPhysics().move(true, this.turnShipAuto(game, 1)); // Attempt to move ship towards the closest ship.
        if (this.closestShipDistance(game) <= this.SHIELD_DISTANCE)
            this.shieldOn(); // If passed minimal distance, attempts to turn on shield.
    }
}
