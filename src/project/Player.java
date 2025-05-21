package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class Player {
	
	int x, y, dx, dy;
    private int speed;
    private int health;
    private int damage;
    private float hitboxRadius;
    private Rectangle rect;
    
    public Player(int x, int y, int speed, int health, int damage, float hitboxRadius) {
    	
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.damage = damage;
        this.hitboxRadius = hitboxRadius;
        rect = new Rectangle(x, y, 30, 30);

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
    
    void draw(Graphics g, int x, int y) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 30, 30);
    }

	

}

