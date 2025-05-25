package project;
import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

 

public class GameApp {

    private JFrame frame;
	private CardLayout cardLayout;
	private JPanel mainPanel;
	private BoatPanel boatPanel;
	private Timer timer;
	boolean soundPlayed;

 

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new GameApp().createAndShowGUI());

    }

 

    private void createAndShowGUI() {

    	frame = new JFrame("Game with Start Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel startPanel = createStartScreen();
        JPanel gamePanel = createGameScreen();


        mainPanel.add(startPanel, "StartScreen");
        mainPanel.add(gamePanel, "GameScreen");
        
        
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        cardLayout.show(mainPanel, "StartScreen");
        
        timer = new Timer(15, e -> {
            if (!boatPanel.isAlive()) {
                boatPanel.setHealth();
                JPanel endPanel = createEndScreen();
                mainPanel.add(endPanel, "EndScreen");
                cardLayout.show(mainPanel, "EndScreen");
                if (soundPlayed == false) {
                	soundPlayed = true;
                	playGameOverSoundEffect();
                }
               
            }
        });
        timer.start();

    }

 

    private JPanel createStartScreen() {

    	JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("BOATS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        panel.add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(startButton, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "GameScreen");
            SwingUtilities.invokeLater(() -> boatPanel.requestFocusInWindow());
        });

        return panel;

    }

 

    private JPanel createGameScreen() {

    	JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(20, 180, 220));

        boatPanel = new BoatPanel();
        panel.add(boatPanel, BorderLayout.CENTER);

        return panel;

    }
    
    private JPanel createEndScreen() {
    	JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Game Over!    Your Score: " + boatPanel.getScore(), SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        JPanel buttonPanel = new JPanel();
        
        panel.add(title, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
private void playGameOverSoundEffect() {
		
		try {
			AudioInputStream a = AudioSystem.getAudioInputStream(new File("src/sounds/kl-peach-game-over-iii-142453.wav"));
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

 

    
}
