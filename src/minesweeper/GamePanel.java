package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    
    private Minesweeper game;
    
    private JPanel topPanel;
    private BoardPanel boardPanel;
    
    private boolean firstClick = true;
    
    public GamePanel() {
        topPanel = new JPanel();
        
        setLayout(new BorderLayout(5, 5));
        topPanel.setBackground(new Color(63, 63, 63));
        add(topPanel, BorderLayout.NORTH);
    }
    
    public void startDefaultGame() {
        startNewGame(Minesweeper.DEFAULT_BOARD_ROWS, Minesweeper.DEFAULT_BOARD_COLUMNS, Minesweeper.DEFAULT_MINES);
    }
    
    public void startNewGame(int rows, int columns, int numMines) {
        game = new Minesweeper(rows, columns, numMines);
        System.out.println("Rows = " + rows + " Columns = " + columns + " numMines = " + numMines);
        if (boardPanel != null) {
            remove(boardPanel);
        }
        boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);
        boardPanel.addMouseListener(boardPanel);
        firstClick = true;
        game.setRunning(true);
    }
    
    private class BoardPanel extends JPanel implements MouseListener {

        private static final int CELL_SIZE = 32;
        
        private class Cell {
            
            //image == 0 -> draw cell with no image
            //image == 1 -> draw cell with flag image
            //image == 2 -> draw cell with question mark image
            int image;
            
            boolean cleared;
            
            int adjacentMines;
            
            Cell() {
                image = 0;
                cleared = false;
            }
            
            void draw(int x, int y, Graphics2D g) {
                if (!cleared) {
                    g.setColor(Color.DARK_GRAY);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                //draw border
                g.setColor(Color.BLACK);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                
                int row = y / CELL_SIZE;
                int column = x / CELL_SIZE;
                FontRenderContext frc = g.getFontRenderContext();
                Font f = new Font("SansSerif", Font.PLAIN, 32);
                int height = (int) f.getLineMetrics("M", frc).getHeight();
                System.out.println(height);
                
                g.setFont(new Font("SansSerif", Font.PLAIN, 32));
                
                if (cleared && adjacentMines > 0 && !game.getBoard().hasMine(row, column)) {
                    g.drawString("" + adjacentMines, x, y + CELL_SIZE);
                } else if (image == 1) {
                    g.drawString("F", x, y + CELL_SIZE);
                }
                
                if (!game.isRunning()) {
                    if (game.getBoard().hasMine(row, column)) {
                        g.drawString("M", x, y + CELL_SIZE);
                        //System.out.println("test");
                    }
                }
            }
        }
        
        private Cell[][] cells;

        public BoardPanel() {
            int rows = game.getBoard().getRows();
            int columns = game.getBoard().getColumns();
            
            cells = new Cell[rows][columns];
            
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    cells[i][j] = new Cell();
                }
            }
            
            setPreferredSize(new Dimension(CELL_SIZE * columns, CELL_SIZE * rows));
        }
        
        private void calculateAdjacencies() {
            int rows = game.getBoard().getRows();
            int columns = game.getBoard().getColumns();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    cells[i][j].adjacentMines = game.getBoard().countMinesAdjacentTo(i, j);
                }
            }
        }
        
        //clears cells recursively starting with the one at (row, column)
        private void clearCells(int row, int column) {
            cells[row][column].cleared = true;
                    
            if (cells[row][column].adjacentMines > 0) {
                return;
            }
                    
            //clear all surrounding cells
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    if (i < 0 || j < 0) continue;
                    if (i >= game.getBoard().getRows() || j >= game.getBoard().getColumns()) continue;
                    if (i == row && j == column) continue;
                    if (!cells[i][j].cleared) {
                        clearCells(i, j);
                    }
                }
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            //draw board
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g;
            
            int rows = game.getBoard().getRows();
            int columns = game.getBoard().getColumns();
            
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    cells[i][j].draw(j * CELL_SIZE, i * CELL_SIZE, g2d);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!game.isRunning()) return;
            //left click
            if (e.getButton() == 1) {
                //determine the cell that was clicked on
                int x = e.getX();
                int y = e.getY();

                int row = y / CELL_SIZE;
                int column = x / CELL_SIZE;

                //was this the first click?
                if (firstClick) {
                    game.getBoard().generateMines(row, column, game.getNumMines());
                    firstClick = false;
                    calculateAdjacencies();
                } else if (game.getBoard().hasMine(row, column)) {
                    game.setRunning(false);
                }

                clearCells(row, column);
                repaint();
            }
            //right click
            else if (e.getButton() == 3) {
                //determine the cell that was clicked on
                int x = e.getX();
                int y = e.getY();

                int row = y / CELL_SIZE;
                int column = x / CELL_SIZE;
                
                cells[row][column].image++;
                
                if (cells[row][column].image > 2) {
                    cells[row][column].image = 0;
                }
                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
    }
}
