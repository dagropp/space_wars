import oop.ex2.SpaceShipPhysics;

/**
 * This class represents a ship that attempts to run away from the fight. It will always accelerate, and will
 * constantly turn away from the closest ship. If the nearest ship is closer than 0.25 units, and if its angle to the
 * Runner is less than 0.23 radians, the Runner feels threatened and will attempt to teleport.
 */
public class ShipRunner extends SpaceShip {
    private final double THREAT_DISTANCE = 0.25;
    private final double THREAT_ANGLE = 0.23;

    public ShipRunner() {
        super();
    }

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
        this.moveShip(game); // Attempt to move ship with dedicated method.
        // If threat is still there, attempt to turn on shield.
        if (this.isThreatened(game))
            this.shieldOn();
        // If threat is still there, attempt to fire a shot.
        if (this.isThreatened(game))
            this.fire(game);
    }

    /**
     * Checks whether the ship feels threatened, by checking its distance and angle to the closest ship.
     *
     * @param game the game object to which this ship belongs.
     * @return True if is threatened, false otherwise.
     */
    private boolean isThreatened(SpaceWars game) {
        // Checks if angle and distance is smaller than minimum threat levels.
        boolean threatAngle = this.closestShipAngle(game) < this.THREAT_ANGLE;
        boolean threatDistance = this.closestShipDistance(game) < this.THREAT_DISTANCE;
        return threatAngle && threatDistance;
    }

    /**
     * Moves ship in the opposite direction to the closest ship, and always accelerating.
     *
     * @param game the game object to which this ship belongs.
     */
    private void moveShip(SpaceWars game) {
        // Using ternary conditional statement, assign left or right, based on closest ship angle.
        int direction = this.closestShipAngle(game) >= 0 ? 1 : -1;
        this.shipPhysics.move(true, direction); // Move to assigned direction, always accelerate.
    }
}
