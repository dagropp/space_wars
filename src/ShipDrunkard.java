/**
 * This class represents a ship that its pilot had a tad too much to drink. We leave it to your creativity to define
 * the ship’s exact behavior, but it must include randomness and should definitely be amusing to fight against.
 */
public class ShipDrunkard extends SpaceShip {
    private final int ACTIONS_NUM = 4;

    @Override
    protected void actions(SpaceWars game) {
        int action = this.randomAction();
        switch (action) {
            case 1:
                this.teleport();
                break;
            case 2:
                this.moveShip(game);
                break;
            case 3:
                this.shieldOn();
                break;
            case 4:
                this.fire(game);
        }
    }

    private void moveShip(SpaceWars game) {

    }

    private int randomAction() {
        return (int) Math.ceil(Math.random() * this.ACTIONS_NUM);
    }
}
