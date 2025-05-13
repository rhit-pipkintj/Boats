package project;

public class Enemy {

    private float x, y;

    private float speed;

    private int health;

    private int damage;

    private int xpReward;

    private float hitboxRadius;

 

    public Enemy(float x, float y, float speed, int health, int damage, int xpReward, float hitboxRadius) {

        this.x = x;

        this.y = y;

        this.speed = speed;

        this.health = health;

        this.damage = damage;

        this.xpReward = xpReward;

        this.hitboxRadius = hitboxRadius;

    }

 

    public void moveTowards(Player player) {

        float dx = player.getX() - this.x;

        float dy = player.getY() - this.y;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

 

        if (distance > 0) {

            this.x += (dx / distance) * speed;

            this.y += (dy / distance) * speed;

        }

    }

 

    public boolean checkCollision(Player player) {

        float dx = player.getX() - this.x;

        float dy = player.getY() - this.y;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance <= this.hitboxRadius + player.getHitboxRadius();

    }

 

    public void onHitPlayer(Player player) {

        if (checkCollision(player)) {

            player.takeDamage(this.damage);

        }

    }

 

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

}