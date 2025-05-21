package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Projectile {
	int x, y, speed;
	double angle;
    Rectangle rect;
    

    Projectile(int x, int y, double angle, int speed) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = speed;
        rect = new Rectangle(x, y, 4, 4);
    }

    void move() {
        x += speed*(Math.cos(angle));
        y += speed*(Math.sin(angle));
        rect.setLocation(x, y);
    }
    
    boolean checkCollision(Enemy enemy, int enemyX, int enemyY) {
    	float dx = (enemyX + 10) - this.x - 2;
        float dy = (enemyY + 10) - this.y - 2;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance <=  15;
    }

    void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 5, 5);
    }
    
    int getX() {
    	return this.x;
    }
    
    int getY() {
    	return this.y;
    }
}
