package edu.guilford;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Santa2048GUI extends JPanel implements KeyListener {
    private static final int SIZE = 4;
    private static final int TILE_SIZE = 100;
    private Santa2048Logic gameLogic;
    private JLabel imageLabel;
    public static JLabel gameOverLabel;
    static BufferedImage santa;
    boolean hasGameEnded = false;
    public JPanel titleScreen;

    private JPanel endScreen;
    private JPanel winScreen;

    public Santa2048GUI() {
        // Creating game title screen
        titleScreen = new JPanel();
        titleScreen.setPreferredSize(new Dimension(800, 650));
        titleScreen.setBackground(new Color(71, 135, 30));
        titleScreen.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("      2048");
        titleLabel.setFont(new Font("Aharoni", Font.BOLD, 100));
        titleLabel.setForeground(Color.RED);
        titleScreen.add(titleLabel, BorderLayout.NORTH);

        JLabel instructionsLabel = new JLabel(
                "How to play:\n Combine like tiles using arrow keys to form the 2048 tile!                                                                 ");
        instructionsLabel.setFont(new Font("Aharoni", Font.PLAIN, 15));
        instructionsLabel.setForeground(Color.WHITE);
        // instructionsLabel.setAlignmentX(200);
        // instructionsLabel.setAlignmentY(300);
        titleScreen.add(instructionsLabel, BorderLayout.AFTER_LINE_ENDS);

        // Set title screen initially visible
        titleScreen.setVisible(true);
        add(titleScreen);

        // Creating game end scree
        endScreen = new JPanel();
        endScreen.setPreferredSize(new Dimension(SIZE * TILE_SIZE, SIZE * TILE_SIZE));
        endScreen.setBackground(new Color(187, 73, 60));
        // endScreen.setLayout(new BorderLayout());

        // Set end screen initially invisible
        endScreen.setVisible(false);
        add(endScreen);

        gameLogic = new Santa2048Logic();
        setPreferredSize(new Dimension(SIZE * TILE_SIZE, SIZE * TILE_SIZE));

        winScreen = new JPanel();
        winScreen.setPreferredSize(new Dimension(SIZE * TILE_SIZE, SIZE * TILE_SIZE));
        winScreen.setBackground(new Color(187, 213, 160));
        winScreen.setLayout(new BorderLayout());

        winScreen.setVisible(false);
        setBackground(new Color(0, 0, 0));
        // JTextField gameOver = new JTextField("over")

        setFocusable(true);
        addKeyListener(this);

        showTitleScreen();
    }

    private class StartButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            titleScreen.setVisible(false);

            // int value = 3;

        }
    }

    private class titleButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            hasGameEnded = false;
            titleScreen.setVisible(true);
            endScreen.setVisible(false);
            gameLogic.resetGame();
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
    }

    private class RestartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            hasGameEnded = false;
            System.out.println("Game Over");
            endScreen.setVisible(false);
            gameLogic.resetGame();

            // Santa2048GUI.setVisible(true);

            // Request focus for the game panel
            Santa2048GUI.this.requestFocus();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!gameLogic.isGameOver() && !gameLogic.isGameWon()) {
            drawBoard(g);
            drawTiles(g);
            drawScore(g);
            drawTitle(g);
            // showWinScreen();
        }
    }

    private void drawTitle(Graphics g) {
        // Draw the score at the top of the board
        g.setColor(Color.GREEN);
        Font font = new Font("Aharoni", Font.BOLD, 80);
        g.setFont(font);
        g.drawString("2", 215, 150);
        g.setColor(Color.RED);
        g.drawString("0", 315, 150);
        g.setColor(Color.GREEN);
        g.drawString("4", 415, 150);
        g.setColor(Color.RED);
        g.drawString("8", 515, 150);
    }

    private void showTitleScreen() {
        titleScreen.setVisible(true);
        // titleScreen.removeAll();
        JButton startButton = new JButton("Start Game");
        startButton.setSize(120, 30);
        titleScreen.add(startButton, BorderLayout.SOUTH);
        startButton.addActionListener(new StartButtonListener());
        // JLabel instructionsLabel = new JLabel("How to play:");

    }

    // Methods for game end, win, and restart
    private void showEndScreen() {
        System.out.println("Displaying end screen");
        // removeAll();
        // Display end screen with the specified message
        endScreen.setVisible(true);
        endScreen.removeAll(); // Clear any previous components
        Font font = new Font("Aharoni", Font.BOLD, 20);
        JLabel endMessageLabelLoss = new JLabel(
                "<html>Oh no! You lost.<br/>" + "Restart the game to reach 2048 and save Christmas!<html>",
                SwingConstants.CENTER);
        // Customize endMessageLabel as needed

        // Add end message label to the center of the end screen
        endScreen.add(endMessageLabelLoss);

        JButton restartButton = new JButton("Restart game");
        // restartButton.setLocation(350, 100);
        restartButton.setSize(120, 50);
        endScreen.add(restartButton);
        restartButton.addActionListener(new RestartButtonListener());

        JButton titleButton = new JButton("Back to title screen");
        titleButton.setSize(120, 50);
        endScreen.add(titleButton);
        titleButton.addActionListener(new titleButtonListener());

        // winScreen.add(endMessageLabelWin, BorderLayout.CENTER);
        // winScreen.add(restartButton, BorderLayout.SOUTH);

        // Add the end screen to the main panel
        // add(endScreen);

        repaint();
    }

    private void showWinScreen() {
        winScreen.setVisible(true);
        JLabel endMessageLabelWin = new JLabel(
                "You did it! You reached the 2048 tile and you win!", SwingConstants.CENTER);
        winScreen.add(endMessageLabelWin);
        JButton titleButton = new JButton("Back to title screen");
        titleButton.setSize(120, 50);
        endScreen.add(titleButton);
        titleButton.addActionListener(new titleButtonListener());

    }

    private void drawScore(Graphics g) {
        // Draw the score at the top of the board
        g.setColor(Color.RED);
        Font font = new Font("Aharoni", Font.BOLD, 16);
        g.setFont(font);
        g.drawString("Score: " + gameLogic.getScore(), 370, 640);
    }

    private void drawBoard(Graphics g) {
        // Calculate the starting position to center the game board
        int boardStartX = (getWidth() - SIZE * TILE_SIZE) / 2;
        int boardStartY = (getHeight() - SIZE * TILE_SIZE) / 2;

        // Draw the background of the game board
        g.setColor(new Color(197, 193, 160)); // Board color
        g.fillRect(boardStartX, boardStartY, SIZE * TILE_SIZE, SIZE * TILE_SIZE);

        // Draw grid lines
        g.setColor(Color.BLACK);
        for (int i = 0; i <= SIZE; i++) {
            g.drawLine(boardStartX + i * TILE_SIZE, boardStartY, boardStartX + i * TILE_SIZE,
                    boardStartY + SIZE * TILE_SIZE);
            g.drawLine(boardStartX, boardStartY + i * TILE_SIZE, boardStartX + SIZE * TILE_SIZE,
                    boardStartY + i * TILE_SIZE);
        }
    }

    // Generating tiles with numerical values
    private void drawTiles(Graphics g) {
        int[][] board = gameLogic.getBoard();

        // Draw tiles with values on the board
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int value = board[i][j];
                if (value != 0) {
                    drawTile(g, j *
                            TILE_SIZE, i * TILE_SIZE, value);
                }
            }
        }
    }

    // For loading images in
    private void drawTile(Graphics g, int x, int y, int value) {
        // Calculate the position relative to the centered game board
        int boardStartX = (getWidth() - SIZE * TILE_SIZE) / 2;
        int boardStartY = (getHeight() - SIZE * TILE_SIZE) / 2;
        int tileX = boardStartX + x;
        int tileY = boardStartY + y;

        // Draw a colored tile with the given value
        g.setColor(getTileColor(value));
        g.fillRect(tileX, tileY, TILE_SIZE, TILE_SIZE);

        // Draw the value in the center of the tile
        g.setColor(Color.BLACK);
        Font font = new Font("Aharoni", Font.BOLD, 24);
        g.setFont(font);
        String valueStr = String.valueOf(value);
        FontMetrics fontMetrics = g.getFontMetrics();
        int textX = tileX + (TILE_SIZE - fontMetrics.stringWidth(valueStr)) / 2;
        int textY = tileY + (TILE_SIZE + fontMetrics.getAscent()) / 2;
        g.drawString(valueStr, textX, textY);

        /*
         * // Add image
         * // layer = ImageIO.read(Santa2048GUI.class.getResource("/" + imagePath));
         * 
         * String imagePath = "santa" + value + "resized.png";
         * ImageIcon icon = new ImageIcon(imagePath);
         * 
         * if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
         * System.out.println("Image loaded successfully.");
         * Image image = icon.getImage();
         * g.drawImage(image, x, y, TILE_SIZE, TILE_SIZE, null);
         * } else {
         * // In case of error
         * System.out.println("Image loading failed.");
         * // g.setColor(Color.RED);
         * // g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
         * // g.setColor(Color.WHITE);
         * // g.drawString("Error", x + TILE_SIZE / 2 - 15, y + TILE_SIZE / 2);
         * }
         */
    }

    //

    private Color getTileColor(int value) {
        // Provide different colors for different tile values
        switch (value) {
            case 2:
                return new Color(0, 255, 0);
            case 4:
                return new Color(40, 180, 0);
            case 8:
                return new Color(80, 150, 0);
            case 16:
                return new Color(100, 100, 0);
            case 32:
                return new Color(140, 80, 0);
            case 64:
                return new Color(180, 40, 0);
            case 128:
                return new Color(200, 30, 0);
            case 256:
                return new Color(255, 0, 0);
            case 512:
                return new Color(255, 0, 50);
            case 1024:
                return new Color(255, 0, 100);
            case 2048:
                return new Color(255, 0, 190);
            default:
                return Color.BLACK;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                gameLogic.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                gameLogic.moveRight();
                break;
            case KeyEvent.VK_UP:
                gameLogic.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                gameLogic.moveDown();
                break;
        }
        gameLogic.addRandomTile();
        if ((gameLogic.isGameOver() || gameLogic.isGameWon()) && !hasGameEnded) {
            hasGameEnded = true;
            showEndScreen();
        } else {
            showWinScreen();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}