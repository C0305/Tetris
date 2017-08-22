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

import javax.swing.JFrame;

/**
 *
 * @author C0305 <c0305@cobos.xyz>
 */
public class Window {
    public static final int WIDTH = 307, HEIGHT = 630;
    JFrame window;
    private Board board;
    
    public Window(){
        
        window = new JFrame("Tetris Gamer");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        
        board = new Board();
        
        window.add(board);
        window.addKeyListener(board);
        window.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        new Window();
    }
}
