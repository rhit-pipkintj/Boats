package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class BoatPanel extends JPanel implements ActionListener {
	
	Timer timer;
	Player player;
	int x = 370;
	int y = 300;
	int pSpeed = 7;
	int dx = 0;
	int dy = 0;
	
	int enemySpawnRate = 2000;
	int enemySpeed = 2;
	long lastEnemySpawn = System.currentTimeMillis();
	ArrayList<Enemy> enemies;
	Random rand = new Random();
	
	public BoatPanel() {
		
		setFocusable(true);

        setPreferredSize(new Dimension(800, 600));
        setBackground(new Color(20, 180, 220));
        
        // Movement
        addKeyListener(new KeyAdapter() {
            @Override
        	public void keyPressed(KeyEvent e) {
        	if (e.getKeyCode() == KeyEvent.VK_LEFT) dx = -pSpeed;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) dx = pSpeed;
            if (e.getKeyCode() == KeyEvent.VK_DOWN)  dy = pSpeed;
            if (e.getKeyCode() == KeyEvent.VK_UP) dy = -pSpeed;
//            if (e.getKeyCode() == KeyEvent.VK_SPACE) (new Projectile(player.x + 10, player.y));
            	
                move();
                repaint();
            }
            
            public void keyReleased(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) dx = 0;
        		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) dy = 0;
        	}
            
        });
        
		SwingUtilities.invokeLater(() -> requestFocusInWindow());
        player = new Player(x, y, pSpeed, 100, 100, 15);
        enemies = new ArrayList<>();
        
        timer = new Timer(15, new ActionListener() {
        
        	public void actionPerformed(ActionEvent e) {
        	
        		if (System.currentTimeMillis() - lastEnemySpawn > enemySpawnRate) {
        			
        			// Decide which side the enemy starts at
        			int side = rand.nextInt(3);
        			switch (side) {
        				case 0:
        					enemies.add(new Enemy(rand.nextInt(780), 0, enemySpeed, 10, 10, 0, 0));
        					break;
        				case 1:
        					enemies.add(new Enemy(800, rand.nextInt(580), enemySpeed, 10, 10, 0, 0));
        					break;
        				case 2:
        					enemies.add(new Enemy(rand.nextInt(760), 600, enemySpeed, 10, 10, 0, 0));
        					break;
        				case 3:
        					enemies.add(new Enemy(0, rand.nextInt(580), enemySpeed, 10, 10, 0, 0));
        					break;
        					
        			}
        			
        			// Make enemies spawn quicker
        			lastEnemySpawn = System.currentTimeMillis();
        			enemySpawnRate = Math.max(200, enemySpawnRate - 50);
        		}
		
        		for (Enemy enemy : enemies) {
        			enemy.moveTowards(player, x, y);
        			if (enemy.checkCollision(player, x, y)) setBackground(new Color(200, 0, 50));
        		}
        		
        		repaint();
        	}
        });
        
        SwingUtilities.invokeLater(() -> timer.start());
        
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		player.draw(g, x, y);
		for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
	}

	
	public void move() {
    	
    	x += dx;
    	x = Math.max(0, Math.min(x, 770));
    	y += dy;
    	y = Math.max(0, Math.min(y, 570));
    	
    }
	
	
	public Dimension getPreferredSize() {
	    return new Dimension(800, 600);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
