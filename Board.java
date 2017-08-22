/*
 * Copyright (C) 2017 C0305 <c0305@cobos.xyz>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tetris;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author C0305 <c0305@cobos.xyz>
 */
public class Board extends JPanel implements KeyListener {

    private BufferedImage blocks;

    private final int blockSize = 30;

    private final int boardWidth = 10, boardHeight = 20;

    private int[][] board = new int[boardHeight][boardWidth];

    private Shape[] shapes = new Shape[7];

    private Shape currentShape;
    

    private Timer timer;

    private final int FPS = 60;

    private final int delay = 1000 / FPS;
    
    private boolean gameOverBaby = false;

    // Shapes
    public Board() {

        try {
            blocks = ImageIO.read(Board.class.getResource("tiles.png"));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }

        });

        timer.start();
        //I
        shapes[0] = new Shape(blocks.getSubimage(0, 0, blockSize, blockSize), new int[][]{
            {1, 1, 1, 1}
        }, this, 1);
        // Z
        shapes[1] = new Shape(blocks.getSubimage(blockSize, 0, blockSize, blockSize), new int[][]{
            {1, 1, 0},
            {0, 1, 1}
        }, this, 2);
        //
        shapes[2] = new Shape(blocks.getSubimage(blockSize * 2, 0, blockSize, blockSize), new int[][]{
            {0, 1, 1},
            {1, 1, 0}
        }, this, 3);
        shapes[3] = new Shape(blocks.getSubimage(blockSize * 3, 0, blockSize, blockSize), new int[][]{
            {1, 1, 1},
            {0, 0, 1}
        }, this, 4);
        shapes[4] = new Shape(blocks.getSubimage(blockSize * 4, 0, blockSize, blockSize), new int[][]{
            {1, 1, 1},
            {1, 0, 0}
        }, this, 5);
        shapes[5] = new Shape(blocks.getSubimage(blockSize * 5, 0, blockSize, blockSize), new int[][]{
            {1, 1, 1},
            {0, 1, 0}
        }, this, 6);
        shapes[6] = new Shape(blocks.getSubimage(blockSize * 6, 0, blockSize, blockSize), new int[][]{
            {1, 1},
            {1, 1}
        }, this, 7);
        setNextShape();
    }

    public void update() {
        currentShape.update();
        if(gameOverBaby)
            timer.stop();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        currentShape.render(g);

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != 0) {
                    g.drawImage(blocks.getSubimage((board[row][col]-1)*blockSize, 0, blockSize, blockSize), col * blockSize, row * blockSize, null);
                    
                }
            }
        }

        // No more 4 u doge g.drawImage(blocks, 0, 0, null);
        for (int i = 0; i < boardHeight; i++) {
            g.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
        }

        for (int j = 0; j < boardWidth; j++) {
            g.drawLine(j * blockSize, 0, j * blockSize, boardHeight * blockSize);
        }
    }

    public void setNextShape() {
        int index = (int) (Math.random() * shapes.length);

        Shape newShape = new Shape(shapes[index].getBlock(), shapes[index].getCoords(), this, shapes[index].getColor());

        currentShape = newShape;
        
        for(int row = 0; row < currentShape.getCoords().length; row++)
            for(int col=0;col<currentShape.getCoords()[row].length;col++)
                if(currentShape.getCoords()[row][col]!=0 )
                    if(board[row][col+3]!= 0)
                        gameOverBaby = true;
    }

    public int getBlockSize() {
        return blockSize;
    }
    
    public int[][] getBoard(){
        return board;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentShape.setDeltaX(-1);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentShape.setDeltaX(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedDown();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentShape.rotate();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.normalSpeed();
        }
    }

}
