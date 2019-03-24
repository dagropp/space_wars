import java.awt.Image;

import oop.ex2.*;

import javax.swing.*;

/**
 * The API spaceships need to implement for the SpaceWars game. It is your decision whether SpaceShip.java will be an
 * interface, an abstract class, a base class for the other spaceships or any other option you will choose.
 *
 * @author oop
 */
public class SpaceShip {
    private SpaceShipPhysics physics; //
    private final int INITIAL_CURRENT_ENERGY = 190;
    private final int INITIAL_MAX_ENERGY = 210;
    private final int MAX_HEALTH = 22;
    private final int BASH_BOOST = 18;
    private final int TURN_BOOST = 1;
    private final int SHOT_COST = 19;
    private final int TELEPORT_COST = 140;
    private final int SHIELD_COST = 3;
    private final int HIT_COST = 10;
    private final int SHOT_INTERVAL = 7;
    private int currentEnergy;
    private int maxEnergy;
    private int health;
    private boolean shield;
    private int turnCounter;
    private int lastShot;

    public SpaceShip() {
        this.turnCounter = 0;
        this.initMembers();
    }

    /**
     * Does the actions of this ship for this round. This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        this.turnCounter++;
        this.shield = false;
        GameGUI gui = game.getGUI();
        if (gui.isTeleportPressed())
            this.teleport();
        this.moveShip(gui);
        if (gui.isShieldsPressed())
            this.shieldOn();
        if (gui.isShotPressed())
            this.fire(game);
        this.boostEnergy(this.TURN_BOOST);
    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        if (!this.shield) {
            boostEnergy(this.BASH_BOOST);
        } else {
            this.health--;
            this.reduceMaxEnergy(this.HIT_COST);
        }
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's attributes, and starts it at a new random
     * position.
     */
    public void reset() {
        this.initMembers();
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.physics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship gets hit by a shot.
     */
    public void gotHit() {
        if (!this.shield) {
            this.health--;
            this.reduceMaxEnergy(this.HIT_COST);
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the ship with or without the shield. This
     * will be displayed on the GUI at the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        return null;
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        boolean canFire = this.lastShot < 0 || (this.turnCounter - this.lastShot) > this.SHOT_INTERVAL;
        boolean hasEnergy = this.reduceEnergy(this.SHOT_COST);
        if (canFire && hasEnergy) {
            game.addShot(physics);
            this.lastShot = this.turnCounter;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (this.reduceEnergy(this.SHIELD_COST)) {
            this.shield = true;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (this.reduceEnergy(this.TELEPORT_COST)) {
            this.physics = new SpaceShipPhysics();
        }
    }

    private void boostEnergy(int boost) {
        int result = this.currentEnergy + boost;
        this.currentEnergy = result < this.maxEnergy ? result : this.maxEnergy;
    }

    private boolean reduceEnergy(int reduction) {
        int result = this.currentEnergy - reduction;
        if (result >= 0) {
            this.currentEnergy = result;
            return true;
        }
        return false;
    }

    private void reduceMaxEnergy(int reduction) {
        this.maxEnergy -= reduction;
        if (this.currentEnergy > this.maxEnergy)
            this.currentEnergy = this.maxEnergy;
    }

    private void moveShip(GameGUI gui) {
        int direction = 0;
        if (gui.isRightPressed())
            direction = 1;
        else if (gui.isLeftPressed())
            direction = -1;
        this.physics.move(gui.isUpPressed(), direction);
    }

    private void initMembers() {
        this.currentEnergy = this.INITIAL_CURRENT_ENERGY;
        this.maxEnergy = this.INITIAL_MAX_ENERGY;
        this.health = this.MAX_HEALTH;
        this.shield = false;
        this.lastShot = -1;
        this.physics = new SpaceShipPhysics();
    }
}
