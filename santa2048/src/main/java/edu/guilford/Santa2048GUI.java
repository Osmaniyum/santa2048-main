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

    private JPanel endScreen;
    private JPanel winScreen;

    public Santa2048GUI() {
        // Creating game end screen
        endScreen = new JPanel();
        endScreen.setPreferredSize(new Dimension(800, 800));
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
    }

    private class RestartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            hasGameEnded = false;
            System.out.println("Game Over");
            endScreen.setVisible(false);
            gameLogic.resetGame();

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

    // Methods for game end, win, and restart
    private void showEndScreen() {
        System.out.println("Displaying end screen");
        // removeAll();
        // Display end screen with the specified message
        endScreen.setVisible(true);
        endScreen.removeAll(); // Clear any previous components
        Font font = new Font("Aharoni", Font.BOLD, 20);
        JLabel endMessageLabelLoss = new JLabel(
                "<html>Oh no! You lost.<br/>" + "Restart the game to feed Santa and save Christmas.<html>",
                SwingConstants.CENTER);
        JLabel endMessageLabelWin = new JLabel(
                "You fed Santa, and now he has enough energy to get through Christmas. Yay!");
        // Customize endMessageLabel as needed

        // Add end message label to the center of the end screen
        endScreen.add(endMessageLabelLoss);

        JButton restartButton = new JButton("Restart Game");
        // restartButton.setLocation(350, 100);
        restartButton.setSize(120, 50);
        endScreen.add(restartButton);
        restartButton.addActionListener(new RestartButtonListener());

        // winScreen.add(endMessageLabelWin, BorderLayout.CENTER);
        // winScreen.add(restartButton, BorderLayout.SOUTH);

        // Add the end screen to the main panel
        // add(endScreen);

        repaint();
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
                return new Color(255, 0, 0);
            case 8:
                return new Color(0, 255, 0);
            case 16:
                return new Color(255, 0, 0);
            case 32:
                return new Color(0, 255, 0);
            case 64:
                return new Color(255, 0, 0);
            case 128:
                return new Color(0, 255, 0);
            case 256:
                return new Color(255, 0, 0);
            case 512:
                return new Color(0, 255, 0);
            case 1024:
                return new Color(255, 0, 0);
            case 2048:
                return new Color(0, 255, 0);
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