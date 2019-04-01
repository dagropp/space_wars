/**
 * This class represents a ship that pursues other ships and tries to fire at them. It will always accelerate, and turn
 * towards the nearest ship. When its angle to the nearest ship is less than 0.21 radians, it will try to fire.
 */
public class ShipAggressive extends SpaceShip {
    private final double FIRE_ANGLE = 0.21;

    @Override
    protected void actions(SpaceWars game) {
        this.moveShip(game); // Attempt to move ship with dedicated method.
        // If angle dictates, attempt to fire a shot.
        this.fire(game);
    }

    private void moveShip(SpaceWars game) {
        // Using ternary conditional statement, assign left or right, based on closest ship angle.
        int direction = this.closestShipAngle(game) >= 0 ? -1 : 1;
        this.shipPhysics.move(true, direction); // Move to assigned direction, always accelerate.
    }

    @Override
    public void fire(SpaceWars game) {
        if (this.closestShipAngle(game) < this.FIRE_ANGLE)
            super.fire(game);
    }
}
