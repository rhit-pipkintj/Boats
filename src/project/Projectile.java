package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Projectile {
	int x, y, dir_x, dir_y;
    Rectangle rect;
    

    Projectile(int x, int y, int dir_x, int dir_y) {
        this.x = x;
        this.y = y;
        rect = new Rectangle(x, y, 5, 5);
    }

    void move() {
        y -= 10;
        rect.setLocation(x, y);
    }

    void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 5, 5);
    }
}
