/**
 * This class represents a ship that pursues other ships and tries to fire at them. It will always accelerate, and turn
 * towards the nearest ship. When its angle to the nearest ship is less than 0.21 radians, it will try to fire.
 */
public class ShipAggressive extends SpaceShip {
    private static final String IMG_DEFAULT = "magenta_default.png"; // This ship's default image.
    private static final String IMG_SHIELD = "magenta_shield.png"; // This ship's image when shields are up.
    private final double FIRE_ANGLE = 0.21; // Ship's minimal angle to when it starts to fire.

    /**
     * Constructor that initiates default spaceship values in parent class, and sets this class's images.
     */
    public ShipAggressive() {
        super(ShipAggressive.IMG_DEFAULT, ShipAggressive.IMG_SHIELD); // Call parent constructor with ship's images.
    }

    /**
     * Does the specified actions assigned to the aggressive ship, according to angle to closest ship.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    protected void actions(SpaceWars game) {
        this.getPhysics().move(true, this.turnShipAuto(game, 1)); // Attempt to move ship with dedicated method.
        this.fire(game); // If angle dictates, attempt to fire a shot.
    }

    /**
     * Overrides the fire() method from SpaceShip, to fire only when specified angle is met.
     *
     * @param game the game object.
     */
    @Override
    public void fire(SpaceWars game) {
        if (this.closestShipAngle(game) < this.FIRE_ANGLE)
            super.fire(game); // Once angle is met, call original method from SpaceShip.
    }
}
