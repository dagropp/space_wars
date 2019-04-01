import oop.ex2.GameGUI;

/**
 * This class represents a ship that is controlled by the user. The keys are: left-arrow and right-arrow to turn,
 * up-arrow to accelerate, ’d’ to fire a shot, ’s’ to turn on the shield, ’a’ to teleport. You can assume there will be
 * at most one human controlled ship in a game, but you’re not required to enforce this.
 */
public class ShipHuman extends SpaceShip {
    public ShipHuman() {
        super();
        this.human = true;
    }

    /**
     * Does the specified actions assigned to the human ship, according to keys pressed.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    protected void actions(SpaceWars game) {
        GameGUI gui = game.getGUI(); // Assign var with Game GUI.
        // If teleport button was pressed, attempt to teleport.
        if (gui.isTeleportPressed())
            this.teleport();
        this.moveShip(gui); // Attempt to move ship, with dedicated method that check which buttons were pressed.
        // If shield button was pressed, attempt to turn on shield.
        if (gui.isShieldsPressed())
            this.shieldOn();
        // If shot button was pressed, attempt to fire a shot.
        if (gui.isShotPressed())
            this.fire(game);
    }

    /**
     * Moves ship according to keys pressed (left/right and with or without acceleration).
     *
     * @param gui Graphic interface of the game.
     */
    private void moveShip(GameGUI gui) {
        int direction = 0;
        boolean right = gui.isRightPressed();
        boolean left = gui.isLeftPressed();
        boolean up = gui.isUpPressed();
        // Assign direction number, according to direction pressed (right = 1, left = -1, both = 0).
        if (right && !left)
            direction = 1;
        else if (left && !right)
            direction = -1;
        this.shipPhysics.move(up, direction); // Move to assigned direction, accelerate if 'up' was pressed.
    }
}
