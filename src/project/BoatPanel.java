package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * This class creates the instances of each part in the game, player, enemy, projectiles, and puts them on the screen and 
 * handles their interactions.
 */
public class BoatPanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8750425590596977330L;
	/**
	 * 
	 */
	Timer timer;
	int t = 0;
	int score = 0;
	float mouseX;
	float mouseY;
	Random rand = new Random();
	
	Player player;
	private BufferedImage pImage;
	private boolean pSpriteLoaded;
	int x = 585;
	int y = 385;
	int pSpeed = 7;
	int dx = 0;
	int dy = 0;
	double playerDamage = 20;
	int playerHealth = 100;
	int playerXP = 0;
	double boatAngle;
	int playerFireRate = 500;
	boolean isAlive = true;
	long lastFire = System.currentTimeMillis();
	boolean canFire = false;
	int damageUpgrades = 0;
	
	private BufferedImage eImage;
	private boolean eSpriteLoaded;
	double enemySpawnRate = 5000;
	double enemySpeed = 1.5;
	double enemyHealth = 20;
	int enemyDamage = 20;
	double enemyXP = 1;
	long lastEnemySpawn = System.currentTimeMillis();
	ArrayList<Enemy> enemies;
	
	ArrayList<Projectile> projectiles;
	private BufferedImage projImage;
	private boolean projSpriteLoaded;
	
	JLabel scoreLabel;
	JLabel healthLabel;
	JLabel xpLabel;
	JLabel upgradeLabel;
	
	
	
	public BoatPanel() {
		
		setFocusable(true);
        setPreferredSize(getPreferredSize());
        setBackground(new Color(20, 186, 186));
        
        player = new Player(x, y, pSpeed, playerHealth, playerDamage, 30);
        try {
			pImage = ImageIO.read(new File("src/Images/playerboat.png"));
			pSpriteLoaded = true;
		} catch(IOException e) {
			pSpriteLoaded = false;
		}
        
        try {
			eImage = ImageIO.read(new File("src/Images/Enemy Boat.png"));
			eSpriteLoaded = true;
		} catch(IOException e) {
			eSpriteLoaded = false;
		}
        
        try {
			projImage = ImageIO.read(new File("src/Images/Projectile.png"));
			projSpriteLoaded = true;
		} catch(IOException e) {
			projSpriteLoaded = false;
		}
       
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        
        // Movement
        addKeyListener(new KeyAdapter() {
            @Override
        	public void keyPressed(KeyEvent e) {
        	if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
        		dx = -pSpeed;
        		boatAngle = -Math.PI/2;
        	}
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            	dx = pSpeed;
            	boatAngle = Math.PI/2;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            	dy = pSpeed;
            	boatAngle = -Math.PI;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            	dy = -pSpeed;
            	boatAngle = 0;
            }
            	
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
        					enemies.add(new Enemy(rand.nextInt(1200), 0, enemySpeed, enemyHealth, enemyDamage, enemyXP, 0));
        					break;
        				case 1:
        					enemies.add(new Enemy(1200, rand.nextInt(800), enemySpeed, enemyHealth, enemyDamage, enemyXP, 0));
        					break;
        				case 2:
        					enemies.add(new Enemy(rand.nextInt(1200), 800, enemySpeed, enemyHealth, enemyDamage, enemyXP, 0));
        					break;
        				case 3:
        					enemies.add(new Enemy(0, rand.nextInt(800), enemySpeed, enemyHealth, enemyDamage, enemyXP, 0));
        					break;
        					
        			}
        			
        			lastEnemySpawn = System.currentTimeMillis();
        		}
        		
        		// Make enemies spawn faster and gain more health
        		enemySpawnRate = Math.max(0, enemySpawnRate - Math.pow(1.5, 0.000000001*t));
        		enemyHealth += t*0.00002;
        		
        		ArrayList<Enemy> toRemoveEnemies = new ArrayList<>();
        		ArrayList<Projectile> toRemoveProjectiles = new ArrayList<>();
        		
        		//Check enemy/player collisions
        		for (Enemy enemy : enemies) {
        			enemy.moveTowards(player, x, y);
        			if (enemy.checkCollision(player, x, y)) {
        				player.setHealth(player.getHealth() - enemy.getDamage());
        				toRemoveEnemies.add(enemy);
        				playCollisionSoundEffect();
        			}
        		}
        		
        		//Player fire rate
        		if (System.currentTimeMillis() - lastFire > playerFireRate) {
        			canFire = true;
        			}
        		else {
        			canFire = false;
        		}
        		
        		// Check projectile/enemy collisions, enemies take damage and remove dead enemies and projectiles that fly off
        		// screen or hit enemies.
        		
        		for (Projectile p : projectiles) {
        			p.move();
        			if (p.getX() > 1200 || p.getX() < 0 || p.getY() > 800 || p.getY() < 0) toRemoveProjectiles.add(p);
        			for (Enemy enemy : enemies) {
        				if (p.checkCollision(enemy,  enemy.getX(), enemy.getY())) {
        					enemy.takeDamage(playerDamage);
        					toRemoveProjectiles.add(p);
        				}
        				
        			}
        		}
        		
        		for (Enemy enemy : enemies) {
        			if (!enemy.isAlive()) {
    					toRemoveEnemies.add(enemy);
    					playerXP += enemyXP;
        				score += 10;
    				}
        		}
        		
        		enemies.removeAll(toRemoveEnemies);
        		projectiles.removeAll(toRemoveProjectiles);
        		
        		// Time
        		t++;
        		
        		// Score
        		if (t % 200 == 0) score += 1;
        		
        		// Damage upgrade
        		if (playerXP >= 10 && playerXP % 10 == 0 && playerXP / 10 == (damageUpgrades+1)) {
        			playerDamage *= 1.5;
        			damageUpgrades+=1;
        		}
        		
   
        		
        		isAlive();
        		updateScoreLabel();
        		updateHealthLabel();
        		updateXPLabel();
        		updateUpgradeLabel();
        		
        		repaint();
        		
        	}
        });
        
        // Fire when mouse is clicked
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                double angle = Math.atan2(e.getY() - y, e.getX() - x);
                if (canFire) {
                    lastFire = System.currentTimeMillis();
                	projectiles.add(new Projectile(x+15, y+15, angle, 10));
                	playFireSoundEffect();
                }
            }
        });
        
        
        SwingUtilities.invokeLater(() -> timer.start());
        if (!isAlive()) {
        	timer.stop();
        }
        
        scoreLabel = new JLabel("Score: " + score + "      ");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(scoreLabel, BorderLayout.NORTH);
		
		healthLabel = new JLabel("Health: " + playerHealth);
        healthLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(healthLabel, BorderLayout.NORTH);
		
		xpLabel = new JLabel("XP: " + playerXP);
        xpLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(xpLabel, BorderLayout.NORTH);
		
		upgradeLabel = new JLabel("Damage Upgrade: " + playerXP);
        upgradeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(upgradeLabel, BorderLayout.NORTH);
		
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (pSpriteLoaded) {
			g2.drawImage(pImage, x, y, 60, 60, this);
		}
		else {
			player.draw(g2, x, y);
		}
		
		for (Enemy enemy : enemies) {
			if (eSpriteLoaded) {
				g2.drawImage(eImage, enemy.getX(), enemy.getY(), 30, 30, this);
			}
			else {
				g2.setColor(Color.BLACK);
		        g2.fillRect(x, y, 20, 20);
			}
        }
		for (Projectile p : projectiles) {
			if (projSpriteLoaded) {
				g2.drawImage(projImage, p.getX(), p.getY(), 10, 10, this);
			}
			else {
				g.setColor(Color.YELLOW);
		        g.fillRect(x-15, y-15, 5, 5);
			}
        }
	}

	
	public void move() {
    	
    	x += dx;
    	x = Math.max(0, Math.min(x, 1170));
    	y += dy;
    	y = Math.max(0, Math.min(y, 770));
    	
    }
	
	public int getScore() {
		return score;
	}
	
	public void setHealth() {
		playerHealth = 100;
	}
	
	public Dimension getPreferredSize() {
	    return new Dimension(1200, 800);
	}
	
	private void updateScoreLabel() {
		scoreLabel.setText("Score: " + score + "      ");
	}
	
	private void updateHealthLabel() {
		healthLabel.setText("Health: " + player.getHealth() + "      ");
	}
	
	private void updateXPLabel() {
		xpLabel.setText("XP: " + playerXP + "      ");
	}
	private void updateUpgradeLabel() {
		upgradeLabel.setText("XP for Damage Upgrade: " + (10 - (playerXP % 10)));
	}
	
	public boolean isAlive() {
		return player.getHealth() > 0;
	}
	
	private void playCollisionSoundEffect() {
		
		try {
			AudioInputStream a = AudioSystem.getAudioInputStream(new File("src/sounds/dumpster_door_hit.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(a);
			clip.start();
		}
		
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		catch (IOException e ) {
			e.printStackTrace();
		}
		catch (Exception e ) {
			System.err.println("Caught " + e.getMessage());
		}
	}
	
	private void playFireSoundEffect() {
		
		try {
			AudioInputStream a = AudioSystem.getAudioInputStream(new File("src/sounds/laser-gun-280344.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(a);
			clip.start();
		}
		
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		catch (IOException e ) {
			e.printStackTrace();
		}
		catch (Exception e ) {
			System.err.println("Caught " + e.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	


}
