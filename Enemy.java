package project;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {

    int x, y;

    private int speed;

    private int health;

    private int damage;

    private int xpReward;

    private int hitboxRadius;


    public Enemy(int x, int y, int speed, int health, int damage, int xpReward, int hitboxRadius) {

        this.x = x;

        this.y = y;

        this.speed = speed;

        this.health = health;

        this.damage = damage;

        this.xpReward = xpReward;

        this.hitboxRadius = hitboxRadius;

    }

    public void moveTowards(Player player, int playerX, int playerY) {

        float dx = playerX - this.x;

        float dy = playerY - this.y;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        
        if (distance > 0) {

            this.x += (dx / distance) * speed;

            this.y += (dy / distance) * speed;

        }

    }

    public boolean checkCollision(Player player, int playerX, int playerY) {

        float dx = playerX - this.x;

        float dy = playerY - this.y;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance <= this.hitboxRadius + player.getHitboxRadius();

    }

//    public void onHitPlayer(Player player) {
//
//        if (checkCollision(player)) {
//
//            player.takeDamage(this.damage);
//
//        }
//
//    }

    public void takeDamage(int amount) {

        this.health -= amount;

        System.out.println("Enemy took " + amount + " damage. Health now: " + this.health);

    }

    public boolean isAlive() {

        return this.health > 0;

    }

    public int getXpReward() {

        return this.xpReward;

    }

    public float getX() {

        return this.x;

    }

    public float getY() {

        return this.y;

    }
    
    public void draw(Graphics g) {
    	g.setColor(Color.BLACK);
        g.fillRect(x, y, 20, 20);
    }

