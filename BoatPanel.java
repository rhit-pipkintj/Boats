package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BoatPanel extends JPanel {
	Player player;
	int x = 370;
	int y = 300;
	int speed = 10;
	int dx = 0;
	int dy = 0;
	
	public BoatPanel() {
		
		setFocusable(true);

        setPreferredSize(new Dimension(800, 600));
        setBackground(new Color(20, 180, 220));
        addKeyListener(new KeyAdapter() {
            @Override
        	public void keyPressed(KeyEvent e) {
        	if (e.getKeyCode() == KeyEvent.VK_LEFT) dx = -speed;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) dx = speed;
            if (e.getKeyCode() == KeyEvent.VK_DOWN)  dy = speed;
            if (e.getKeyCode() == KeyEvent.VK_UP) dy = -speed;
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
        player = new Player(x, y, speed, 100, 100, 30);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		player.draw(g, x, y);
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


}
