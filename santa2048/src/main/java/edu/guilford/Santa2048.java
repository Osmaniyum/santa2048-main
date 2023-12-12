package edu.guilford;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Santa2048 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Santa 2048 Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        // if (gameLogic.isGameOver()) {
        // }

        Santa2048GUI gui = new Santa2048GUI();
        // Manually set the location of the panel in the center
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int panelWidth = gui.getPreferredSize().width;
        int panelHeight = gui.getPreferredSize().height;

        int x = (frameWidth - panelWidth) / 2;
        int y = (frameHeight - panelHeight) / 2;

        gui.setBounds(x, y, panelWidth, panelHeight);

        frame.add(gui);

        frame.setContentPane(gui);

        frame.addKeyListener(gui);

        // frame.add(gameOverLabel);

        frame.setVisible(true);
    }
}
