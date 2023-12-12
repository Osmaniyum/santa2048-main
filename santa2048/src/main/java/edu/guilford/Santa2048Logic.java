package edu.guilford;

import java.util.Random;

import javax.swing.JLabel;

public class Santa2048Logic {
    private static final int SIZE = 4;
    private int[][] newBoard;
    private int[][] board;
    private Random random;
    private int score = 0;

    public Santa2048Logic() {
        board = new int[SIZE][SIZE];
        newBoard = new int[SIZE][SIZE];
        random = new Random();
        score = 0;
        addRandomTile(); // First random tile
        addRandomTile(); // Second random tile
        // resetGame();
    }

    public int[][] getBoard() {
        return board;
    }

    public int[][] getNewBoard() {
        return newBoard;
    }

    public int getScore() {
        return score;
    }

    public void addRandomTile() {
        int emptyTiles = 0;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    emptyTiles++;
                }
            }
        }

        if (emptyTiles == 0) {
            // Game over logic
            // System.out.println("Game Over");
            return;
        }

        int position = random.nextInt(emptyTiles) + 1;
        emptyTiles = 0;
        // what is going on here?
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    emptyTiles++;
                    if (board[i][j] != newBoard[i][j]) {
                        System.out.println("Invalid move");
                    }
                    if (emptyTiles == position) {
                        board[i][j] = (random.nextInt(2) + 1) * 2; // 2 or 4
                        return;
                    }
                }
            }
        }
        repaint();
    }

    private void slideUp() {
        // starting on the right column, going left
        for (int col = SIZE - 1; col >= 0; col--) {
            // starting on top row, going down rows
            for (int row = 0; row < SIZE; row++) {
                // if value is 0...
                if (board[row][col] == 0) {
                    // ...perform this for
                    //
                    for (int i = row; i < SIZE; i++) {
                        // if the value of
                        if (board[i][col] != 0) {

                            board[row][col] = board[i][col];
                            board[i][col] = 0;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void combineUp() {
        // starting on the right column
        for (int col = SIZE - 1; col >= 0; col--) {
            // starting on top row, skipping last
            for (int row = 0; row < SIZE - 1; row++) {
                if (board[row][col] == board[row + 1][col] && board[row][col] != 0) {
                    board[row][col] += board[row + 1][col];
                    score += board[row + 1][col];
                    board[row + 1][col] = 0;
                }
            }
        }
    }

    public void moveUp() {
        slideUp();
        combineUp();
        slideUp();
        // if (int
        repaint();
    }

    private void slideDown() {
        // starting on the right column
        for (int col = SIZE - 1; col >= 0; col--) {
            // starting on bottom row
            for (int row = SIZE - 1; row >= 0; row--) {
                // if the value is 0
                if (board[row][col] == 0) {
                    // starting
                    for (int i = row; i >= 0; i--) {
                        //
                        if (board[i][col] != 0) {
                            //
                            board[row][col] = board[i][col];
                            //
                            board[i][col] = 0;
                            break;
                        }
                    }
                }
            }
        }
    }
    // i and j = grid number
    // row and col = value in grid

    private void combineDown() {
        // starting on the right column
        for (int col = SIZE - 1; col >= 0; col--) {
            // starting on bottom row, skipping first
            for (int row = SIZE - 1; row >= 1; row--) {
                if (board[row][col] == board[row - 1][col] && board[row][col] != 0) {
                    board[row][col] += board[row - 1][col];
                    score += board[row - 1][col];
                    board[row - 1][col] = 0;
                }
            }
        }
    }

    public void moveDown() {
        slideDown();
        combineDown();
        slideDown();
        repaint();
    }

    private void slideLeft() {
        // starting on the top row
        for (int row = 0; row < SIZE; row++) {
            // starting on left column
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int i = col; i < SIZE; i++) {
                        if (board[row][i] != 0) {
                            board[row][col] = board[row][i];
                            board[row][i] = 0;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void combineLeft() {
        // starting on the top row
        for (int row = 0; row < SIZE; row++) {
            // starting on left column, skipping last
            for (int col = 0; col < SIZE - 1; col++) {
                if (board[row][col] == board[row][col + 1] && board[row][col] != 0) {
                    board[row][col] += board[row][col + 1];
                    score += board[row][col + 1];
                    board[row][col + 1] = 0;
                }
            }
        }
    }

    public void moveLeft() {
        slideLeft();
        combineLeft();
        slideLeft();
        repaint();
    }

    private void slideRight() {
        // starting on the top row
        for (int row = 0; row < SIZE; row++) {
            // starting on right column
            for (int col = SIZE - 1; col >= 0; col--) {
                if (board[row][col] == 0) {
                    for (int i = col; i >= 0; i--) {
                        if (board[row][i] != 0) {
                            board[row][col] = board[row][i];
                            board[row][i] = 0;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void combineRight() {
        // starting on the top row
        for (int row = 0; row < SIZE; row++) {
            // starting on right column, skipping first
            for (int col = SIZE - 1; col >= 1; col--) {
                if (board[row][col] == board[row][col - 1] && board[row][col] != 0) {
                    board[row][col] += board[row][col - 1];
                    score += board[row][col - 1];
                    board[row][col - 1] = 0;
                }
            }
        }
    }

    public void moveRight() {
        slideRight();
        combineRight();
        slideRight();
        repaint();
    }

    public boolean isGameOver() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    return false; // Game not over if there is an empty tile
                }
                if (j < SIZE - 1 && board[i][j] == board[i][j + 1]) {
                    return false; // Game not over if adjacent horizontal tiles are the same
                }
                if (i < SIZE - 1 && board[i][j] == board[i + 1][j]) {
                    return false; // Game not over if adjacent vertical tiles are the same
                }
            }
        }
        return true; // No possible moves, game over
    }

    public boolean isGameWon() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 2048) {
                    return true; // Found a tile with the value of 2048, game won
                }
            }
        }
        return false; // No tile with the value of 2048 found, game not won
    }

    public void resetGame() {
        // Reset the board to its initial state (all zeros)
        board = new int[SIZE][SIZE];
        // Reset the score
        score = 0;
        // Add initial tiles (if needed)
        addRandomTile();
        addRandomTile();
    }

    private void repaint() {
    }
}