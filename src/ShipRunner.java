/**
 * This class represents a ship that attempts to run away from the fight. It will always accelerate, and will
 * constantly turn away from the closest ship. If the nearest ship is closer than 0.25 units, and if its angle to the
 * Runner is less than 0.23 radians, the Runner feels threatened and will attempt to teleport.
 */
public class ShipRunner extends SpaceShip {
    /* Class members - constant variables */
    private static final String IMG_DEFAULT = "cyan_default.png"; // This ship's default image.
    private static final String IMG_SHIELD = "cyan_shield.png"; // This ship's image when shields are up.
    private final double THREAT_DISTANCE = 0.25; // Ship's minimal distance to when it starts feeling threatened.
    private final double THREAT_ANGLE = 0.23; // Ship's minimal angle to when it starts feeling threatened.

    /* Constructors */

    /**
     * Constructor that initiates default spaceship values in parent class, and sets this class's images.
     */
    public ShipRunner() {
        super(IMG_DEFAULT, IMG_SHIELD); // Call parent constructor with ship's images.
    }

    /* Protected instance Methods */

    /**
     * Does the specified actions assigned to the runner ship, according to its threat level.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    protected void actions(SpaceWars game) {
        // If ship feels threatened, attempt to teleport.
        if (this.isThreatened(game))
            this.teleport();
        this.getPhysics().move(true, this.turnShipAuto(game, -1)); // Attempt to move ship with dedicated method.
    }

    /* Private instance Methods */

    /**
     * Checks whether the ship feels threatened, by checking its distance and angle to the closest ship.
     *
     * @param game the game object to which this ship belongs.
     * @return True if is threatened, false otherwise.
     */
    private boolean isThreatened(SpaceWars game) {
        // Checks whether angle and distance are smaller than minimum specified threat levels.
        boolean threatAngle = this.closestShipAngle(game) < this.THREAT_ANGLE;
        boolean threatDistance = this.closestShipDistance(game) < this.THREAT_DISTANCE;
        return threatAngle && threatDistance;
    }
}
