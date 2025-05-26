package project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class controls what happens with the player boat.
 */
public class Player {
	
	int x, y, dx, dy;
    private int speed;
    private int health;
    private double damage;
    private double xp;
    private float hitboxRadius;
    int boatAngle;
    
    public Player(int x, int y, int speed, int health, double damage, float hitboxRadius) {
    	
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.damage = damage;
        this.hitboxRadius = hitboxRadius;

    }
    
    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
    
    public void takeDamage(int damage) {
    	health -= damage;
    }
    
    public float getHitboxRadius() {
    	return this.hitboxRadius;
    }
    
    void draw(Graphics2D g2, int x, int y) {
        g2.setColor(Color.RED);
        g2.fillRect(x, y, 30, 30);
    }
    
    
    public int getHealth() {
    	return this.health;
    }
    
    public boolean isAlive() {

        return this.health > 0;

    }
    public void setHealth(int newHealth) {
    	this.health = newHealth;
    }

	public double getXP() {
		
		return this.xp;
	}

	

}
