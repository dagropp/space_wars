import oop.ex2.GameGUI;

/**
 * This class represents a ship that is controlled by the user. The keys are: left-arrow and right-arrow to turn,
 * up-arrow to accelerate, ’d’ to fire a shot, ’s’ to turn on the shield, ’a’ to teleport. You can assume there will be
 * at most one human controlled ship in a game, but you’re not required to enforce this.
 */
public class ShipHuman extends SpaceShip {
    private static final String IMG_DEFAULT = "grey_default.png"; // This ship's default image.
    private static final String IMG_SHIELD = "grey_shield.png"; // This ship's image when shields are up.

    /**
     * Constructor that initiates default spaceship values in parent class, and sets this class's images.
     */
    public ShipHuman() {
        super(ShipHuman.IMG_DEFAULT, ShipHuman.IMG_SHIELD); // Call parent constructor with ship's images.
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
        boolean right = gui.isRightPressed(); // True/false according to if right key was pressed.
        boolean left = gui.isLeftPressed(); // True/false according to if left key was pressed.
        boolean accelerate = gui.isUpPressed(); // True/false according to if up key was pressed.
        int direction = this.turnShipManual(right, left); // Assign direction number with dedicated method.
        this.getPhysics().move(accelerate, direction); // Move to assigned direction, accelerate if 'up' was pressed.
    }
}
