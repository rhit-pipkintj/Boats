package project;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class Game {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BoatPanel boatPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Game().createGUI());
    }

    private void createGUI() {
        frame = new JFrame("Game with Start Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel startPanel = createStartPanel();
        JPanel gamePanel = createGamePanel();

        mainPanel.add(startPanel, "StartScreen");
        mainPanel.add(gamePanel, "GameScreen");
        
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        cardLayout.show(mainPanel, "StartScreen");
    }

    private JPanel createStartPanel() {
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

    private JPanel createGamePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(20, 180, 220));

        boatPanel = new BoatPanel();
        panel.add(boatPanel, BorderLayout.CENTER);

        return panel;
    }
}
