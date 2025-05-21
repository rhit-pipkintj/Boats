package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class BoatPanel extends JPanel implements ActionListener {
	
	Timer timer;
	int t = 0;
	float mouseX;
	float mouseY;
	Random rand = new Random();
	
	Player player;
	private BufferedImage image;
	private boolean spriteLoaded;
	int x = 585;
	int y = 385;
	int pSpeed = 7;
	int dx = 0;
	int dy = 0;
	int playerDamage = 20;
	
	double enemySpawnRate = 5000;
	double enemySpeed = 1.5;
	double enemyHealth = 40;
	long lastEnemySpawn = System.currentTimeMillis();
	ArrayList<Enemy> enemies;
	
	ArrayList<Projectile> projectiles;
	
	
	
	public BoatPanel() {
		
		setFocusable(true);
        setPreferredSize(getPreferredSize());
        setBackground(new Color(20, 200, 200));
        
        player = new Player(x, y, pSpeed, 100, playerDamage, 15);
        try {
			image = ImageIO.read(new File("src/Images/Boat.pngaaaaaaaaaaa"));
			spriteLoaded = true;
		} catch(IOException e) {
			spriteLoaded = false;
		}
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        
        // Movement
        addKeyListener(new KeyAdapter() {
            @Override
        	public void keyPressed(KeyEvent e) {
        	if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) dx = -pSpeed;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) dx = pSpeed;
            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)  dy = pSpeed;
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) dy = -pSpeed;
            	
                move();
                repaint();
            }
            
            public void keyReleased(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) dx = 0;
        		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_W) dy = 0;
        	}
            
        });
        
		SwingUtilities.invokeLater(() -> requestFocusInWindow());
        
        timer = new Timer(15, new ActionListener() {
        
        	public void actionPerformed(ActionEvent e) {
        	
        		if (System.currentTimeMillis() - lastEnemySpawn > enemySpawnRate) {
        			
        			// Decide which side the enemy starts at
        			int side = rand.nextInt(3);
        			switch (side) {
        				case 0:
        					enemies.add(new Enemy(rand.nextInt(1200), 0, enemySpeed, enemyHealth, 0, 0, 0));
        					break;
        				case 1:
        					enemies.add(new Enemy(1200, rand.nextInt(800), enemySpeed, enemyHealth, 0, 0, 0));
        					break;
        				case 2:
        					enemies.add(new Enemy(rand.nextInt(1200), 800, enemySpeed, enemyHealth, 0, 0, 0));
        					break;
        				case 3:
        					enemies.add(new Enemy(0, rand.nextInt(800), enemySpeed, enemyHealth, 40, 0, 0));
        					break;
        					
        			}
        			
        			lastEnemySpawn = System.currentTimeMillis();
        		}
        		
        		// Make enemies spawn faster and gain more health
        		enemySpawnRate = Math.max(0, enemySpawnRate - Math.pow(2, 0.000000001*t));
        		enemyHealth += t*0.00005;
		
        		for (Enemy enemy : enemies) {
        			enemy.moveTowards(player, x, y);
        			if (enemy.checkCollision(player, x, y)) setBackground(new Color(200, 0, 50));
        		}
        		
        		
        		// Check collisions, enemies take damage and remove dead enemies and projectiles that fly off
        		// screen or hit enemies.
        		
        		ArrayList<Enemy> toRemoveEnemies = new ArrayList<>();
        		ArrayList<Projectile> toRemoveProjectiles = new ArrayList<>();
        		
        		for (Projectile p : projectiles) {
        			p.move();
        			if (p.getX() > 1200 || p.getX() < 0 || p.getY() > 800 || p.getY() < 0) toRemoveProjectiles.add(p);
        			for (Enemy enemy : enemies) {
        				if (p.checkCollision(enemy,  enemy.getX(), enemy.getY())) {
        					enemy.takeDamage(playerDamage);
        					toRemoveProjectiles.add(p);
        				}
        				if (!enemy.isAlive()) toRemoveEnemies.add(enemy);
        			}
        		}

        		enemies.removeAll(toRemoveEnemies);
        		projectiles.removeAll(toRemoveProjectiles);
        		
        		
        		repaint();
        		
        		// Time
        		t++;
        		
        		System.out.println(enemyHealth);
        	}
        });
        
        // Fire when mouse is clicked
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                double angle = Math.atan2(e.getY() - y, e.getX() - x);
                projectiles.add(new Projectile(x, y, angle, 10));
            }
        });
        
        SwingUtilities.invokeLater(() -> timer.start());
        
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (spriteLoaded) {
			g.drawImage(image, x, y, 50, 50, this);
		}
		else {
			player.draw(g, x, y);
		}
		for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
		for (Projectile p : projectiles) {
        	p.draw(g);
        }
	}

	
	public void move() {
    	
    	x += dx;
    	x = Math.max(0, Math.min(x, 1170));
    	y += dy;
    	y = Math.max(0, Math.min(y, 770));
    	
    }
	
	
	public Dimension getPreferredSize() {
	    return new Dimension(1200, 800);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
