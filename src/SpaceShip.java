import java.awt.*;

import oop.ex2.*;

import javax.swing.*;

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
    /* Class members - variables */
    protected SpaceShipPhysics shipPhysics; // Physics object that controls this ship.
    private int currentEnergy; // Ship's current energy level.
    private int maxEnergy; // Ship's maximum energy level.
    private int health; // Ship's health level.
    private boolean shield; // Shield boolean.
    private int turnCounter; // Game's turn counter, resets on each ship's death.
    private int lastShot; // Last turn ship fired a shot.
    private String imgDefault; // Ship's default image.
    private String imgShield; // Ship's image when shields are up.

    /**
     * Constructor for SpaceShip object. Assign members with initial values.
     */
    public SpaceShip(String imgDefault, String imgShield) {
        this.initMembers(); // Call method to initialize default class members.
        this.imgDefault = imgDefault; // Sets ship's default image.
        this.imgShield = imgShield; // Sets ship's image when shields are up.
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
        String img = this.shield ? this.imgShield : this.imgDefault; // Sets image according to shield's status.
        ImageIcon icon = new ImageIcon(img); // Creates ImageIcon with relevant specified path.
        return icon.getImage(); // Return Image object.
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
        if (this.reduceEnergy(this.SHIELD_COST)) // If energy is sufficient, turns on shield.
            this.shield = true; // Turn shield on, by switching shield var to true.
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (this.reduceEnergy(this.TELEPORT_COST)) // If energy is sufficient, performs teleportation.
            this.shipPhysics = new SpaceShipPhysics(); // Teleport by creating a new SpaceShipPhysics object.
    }

    /**
     * Abstract method, to be implemented by each child class.
     *
     * @param game the game object to which this ship belongs.
     */
    protected abstract void actions(SpaceWars game);

    /**
     * @param game the game object to which this ship belongs.
     * @return The angle to the closest ship.
     */
    protected double closestShipAngle(SpaceWars game) {
        return this.shipPhysics.angleTo(this.fetchClosestShip(game));
    }

    /**
     * @param game the game object to which this ship belongs.
     * @return The distance from the closest ship.
     */
    protected double closestShipDistance(SpaceWars game) {
        return this.shipPhysics.distanceFrom(this.fetchClosestShip(game));
    }

    /**
     * Method for human handled/random child classes. Assign direction number according to triggered/random direction.
     *
     * @param right Trigger boolean (manually/automatically).
     * @param left  Trigger boolean (manually/automatically).
     * @return 1 for right, -1 for left, 0 for straight.
     */
    protected int turnShipManual(boolean right, boolean left) {
        if (right && !left) // Right was triggered, left was not.
            return 1;
        else if (left && !right) // Left was triggered, right was not.
            return -1;
        return 0; // Right and left were triggered together, or none were.
    }

    /**
     * Method for computer handled child classes to turn towards or away from the nearest ship.
     *
     * @param game      the game object to which this ship belongs.
     * @param runFactor 1 if turning towards closest ship, -1 if turning away.
     * @return 1 for right, -1 for left, 0 for straight.
     */
    protected int turnShipAuto(SpaceWars game, int runFactor) {
        int direction = this.closestShipAngle(game) >= 0 ? 1 : -1;
        return runFactor * direction;
    }

    /**
     * Sets ship's default image.
     *
     * @param path Containing path to image.
     */
    protected void setImgDefault(String path) {
        this.imgDefault = path;
    }

    /**
     * Sets ship's image when shields are up.
     *
     * @param path Containing path to image.
     */
    protected void setImgShield(String path) {
        this.imgShield = path;
    }

    /**
     * Assign (or reassign upon ship's death) class members to their initial values.
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

    /**
     * @param game the game object to which this ship belongs.
     * @return The SpaceShipPhysics object of the closest ship.
     */
    private SpaceShipPhysics fetchClosestShip(SpaceWars game) {
        return game.getClosestShipTo(this).getPhysics();
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
}
