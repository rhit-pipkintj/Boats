package project;

/**
 * This class controls everything that happens to the enemy boats.
 */
public class Enemy {

    int x, y;
    private double speed;
    private double health;
    private int damage;
    private double xpReward;
    private int hitboxRadius;

    public Enemy(int x, int y, double speed, double health, int damage, double xpReward, int hitboxRadius) {

        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.damage = damage;
        this.xpReward = xpReward;
        this.hitboxRadius = hitboxRadius;
        
       
    }

    public void moveTowards(Player player, int playerX, int playerY) {

        float dx = (playerX+30) - this.x;

        float dy = (playerY+30) - this.y;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        
        if (distance > 0) {

            this.x += (dx / distance) * speed;

            this.y += (dy / distance) * speed;

        }

    }

    public boolean checkCollision(Player player, int playerX, int playerY) {

        float dx = (playerX+30) - (this.x+15);

        float dy = (playerY+30) - (this.y+15);

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance <= this.hitboxRadius + player.getHitboxRadius();

    }


    public void takeDamage(double amount) {

        this.health -= amount;

        System.out.println("Enemy took " + amount + " damage. Health now: " + this.health);

    }

    public boolean isAlive() {

        return this.health > 0;

    }

    public double getXpReward() {

        return this.xpReward;

    }

    public int getX() {

        return this.x;

    }

    public int getY() {

        return this.y;

    }
    
    public int getDamage() {
    	return this.damage;
    }
    
    public double getHealth() {
    	return this.health;
    }
    
    

}
