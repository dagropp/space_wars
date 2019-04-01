/**
 * This class represents a ship that attempts to collide with other ships. It will always accelerate, and will
 * constantly turn towards the closest ship. If it gets within a distance of 0.19 units (inclusive) from another ship,
 * it will attempt to turn on its shields.
 */
public class ShipBasher extends SpaceShip {
    private final double SHIELD_DISTANCE = 0.19;

    @Override
    protected void actions(SpaceWars game) {
        this.moveShip(game); // Attempt to move ship with dedicated method.
        // If threat is still there, attempt to turn on shield.
        this.shieldOn(game);
    }

    private void moveShip(SpaceWars game) {
        // Using ternary conditional statement, assign left or right, based on closest ship angle.
        int direction = this.closestShipAngle(game) >= 0 ? -1 : 1;
        this.shipPhysics.move(true, direction); // Move to assigned direction, always accelerate.
    }

    public void shieldOn(SpaceWars game) {
        if (this.closestShipDistance(game) <= this.SHIELD_DISTANCE)
            super.shieldOn();
    }
}
