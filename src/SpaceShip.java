import java.awt.*;

import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game. It is your decision whether SpaceShip.java will be an
 * interface, an abstract class, a base class for the other spaceships or any other option you will choose.
 *
 * @author oop
 */
public abstract class SpaceShip {
    /* Class members - constant variables */
    private final int INITIAL_CURRENT_ENERGY = 190; // Initial ship's current energy level.
    private final int INITIAL_MAX_ENERGY = 210; // Initial ship's maximum energy level.
    private final int MAX_HEALTH = 22; // Initial ship's maximum health level.
    private final int TURN_BOOST = 1; // Energy boost on each turn.
    private final int BASH_BOOST = 18; // Energy boost on each bash, when shields are on.
    private final int SHOT_COST = 19; // Energy reduction for each shot firing.
    private final int TELEPORT_COST = 140; // Energy reduction for each teleportation.
    private final int SHIELD_COST = 3; // Energy reduction for each shield lifting.
    private final int HIT_COST = 10; // Energy reduction for each hit, when shields are off.
    private final int SHOT_INTERVAL = 7; // Rounds intervals between each shot firing.
    private final String IMG_DIR = "/oop.ex2/";
    private final String HUMAN_IMG = "spaceship2.gif";
    private final String HUMAN_IMG_SHIELD = "spaceship2_shield.gif";
    private final String COMPUTER_IMG = "spaceship3.gif";
    private final String COMPUTER_IMG_SHIELD = "spaceship3_shield.gif";
    /* Class members - variables */
    private SpaceShipPhysics shipPhysics; // Physics object that controls this ship.
    private int currentEnergy; // Ship's current energy level.
    private int maxEnergy; // Ship's maximum energy level.
    private int health; // Ship's health level.
    private boolean shield; // Shield boolean.
    private int turnCounter; // Game's turn counter, resets on each ship's death.
    private int lastShot; // Last turn ship fired a shot.
    protected boolean human = false;

    /**
     * Constructor for SpaceShip object. Assign members with initial values.
     */
    public SpaceShip() {
        this.initMembers();
    }

    /**
     * Does the actions of this ship for this round. This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        this.turnCounter++; // Adds 1 to counter on each round.
        this.shield = false; // Turns off shield, in case it got turn on last round.
        this.actions(game);
        this.boostEnergy(this.TURN_BOOST); // Boost energy by specified turn boost.
    }

    protected abstract void actions(SpaceWars game);

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        // Checks whether the shield is up, if boost energy by specified bash boost.
        if (this.shield)
            boostEnergy(this.BASH_BOOST);
        else
            this.unshieldedHit(); // Reduce max energy and health by specified reductions.
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's attributes, and starts it at a new random
     * position.
     */
    public void reset() {
        this.initMembers(); // Call method to reassign class members to their initial values.
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return this.health <= 0; // If health levels are 0 or below, ship is dead (true). False otherwise.
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.shipPhysics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship gets hit by a shot.
     */
    public void gotHit() {
        // Checks whether the shield is up, if so do nothing.
        if (!this.shield)
            this.unshieldedHit(); // Reduce max energy and health by specified reductions.
    }

    /**
     * Gets the image of this ship. This method should return the image of the ship with or without the shield. This
     * will be displayed on the GUI at the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        // This method has not proved to work...
        String path = this.human ? this.humanImgPath() : this.computerImgPath();
        return this.produceRelevantImage(path);
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        // Assigns boolean var to determine if a shot hasn't been fired in the specified interval.
        boolean canFire = this.lastShot < 0 || (this.turnCounter - this.lastShot) > this.SHOT_INTERVAL;
        // Assigns boolean var to determine if energy is sufficient to fire a shot.
        boolean hasEnergy = this.reduceEnergy(this.SHOT_COST);
        // If both tests are true, fire a shot.
        if (canFire && hasEnergy) {
            game.addShot(this.shipPhysics); // Call SpaceWars method addShot().
            this.lastShot = this.turnCounter; // Assigns lastShot var with current turn number.
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        // If energy is sufficient, turns on shield.
        if (this.reduceEnergy(this.SHIELD_COST)) {
            this.shield = true; // Turn shield on, by switching shield var to true.
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        // If energy is sufficient, performs teleportation.
        if (this.reduceEnergy(this.TELEPORT_COST))
            this.shipPhysics = new SpaceShipPhysics(); // Teleport by creating a new SpaceShipPhysics object.
    }

    /**
     * Boosts energy on certain events.
     *
     * @param boost The amount of energy to boost.
     */
    private void boostEnergy(int boost) {
        int result = this.currentEnergy + boost;
        // If boost results in energy being more than max assigns max value, otherwise perform boost.
        this.currentEnergy = result < this.maxEnergy ? result : this.maxEnergy;
    }

    /**
     * Attempts to reduce current energy in order to perform action.
     *
     * @param reduction The amount of energy to reduce.
     * @return True if successful (energy doesn't reduce below 0), false otherwise.
     */
    private boolean reduceEnergy(int reduction) {
        int result = this.currentEnergy - reduction;
        // If reduction leaves current energy above 0, perform change and return true.
        if (result >= 0) {
            this.currentEnergy = result;
            return true;
        }
        return false; // Reduction not performed due to insufficient energy level.
    }

    /**
     * Reduces max energy, each collision or hit when shield is down.
     */
    private void unshieldedHit() {
        this.health--; // Reduces health by 1.
        this.maxEnergy -= this.HIT_COST; // Reduces max energy by specified hit reduction.
        // Updates current energy to fit max energy - if it's higher reduces it, otherwise does nothing.
        if (this.currentEnergy > this.maxEnergy)
            this.currentEnergy = this.maxEnergy;
    }

    /**
     * Moves ship according to key pressed (left/right and with or without acceleration).
     *
     * @param gui Graphic interface of the game.
     */
    protected void moveShip(GameGUI gui) {
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

    /**
     * Assign (or reassign) class members to their initial values.
     */
    private void initMembers() {
        this.currentEnergy = this.INITIAL_CURRENT_ENERGY;
        this.maxEnergy = this.INITIAL_MAX_ENERGY;
        this.health = this.MAX_HEALTH;
        this.shield = false;
        this.turnCounter = 0;
        this.lastShot = -1;
        this.shipPhysics = new SpaceShipPhysics();
    }

    protected Image produceRelevantImage(String path) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getImage(path);
    }

    private String humanImgPath() {
        if (this.shield)
            return this.IMG_DIR + this.HUMAN_IMG_SHIELD;
        return this.IMG_DIR + this.HUMAN_IMG;
    }

    private String computerImgPath() {
        if (this.shield)
            return this.IMG_DIR + this.COMPUTER_IMG_SHIELD;
        return this.IMG_DIR + this.COMPUTER_IMG;
    }
}
