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



public class EnemyPanel extends JPanel implements ActionListener{
	
	int enemySpawnRate;
	long lastEnemySpawn = System.currentTimeMillis();
	ArrayList<Enemy> enemies = new ArrayList<>();
	Random rand = new Random();
	
	public EnemyPanel() {
		
        setPreferredSize(new Dimension(800, 600));
        setBackground(new Color(20, 180, 220));
        
        }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (System.currentTimeMillis() - lastEnemySpawn > enemySpawnRate) {
            enemies.add(new Enemy(rand.nextInt(760), 100, 5, 10, 10, 0, 0));
            System.out.println("added");
            lastEnemySpawn = System.currentTimeMillis();
            enemySpawnRate = Math.max(200, enemySpawnRate - 50);
        }
		
		for (Enemy enemy : enemies) {
            enemy.y += 2;
        }
		
		repaint();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
	}
	
	
}
