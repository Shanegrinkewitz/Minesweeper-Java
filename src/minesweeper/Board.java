package minesweeper;

import java.util.Random;

public class Board {
    
    private boolean[][] boardData;
    
    public Board(int rows, int columns) {
        boardData = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boardData[i][j] = false;
            }
        }
    }
    
    public int getRows() {
        return boardData.length;
    }
    
    public int getColumns() {
        return boardData[0].length;
    }
    
    public boolean hasMine(int row, int column) {
        return boardData[row][column];
    }
    
    public void generateMines(int row, int column, int numMines) {
        Random random = new Random();
        
        int selectedRow = -1;
        int selectedColumn = -1;
        
        for (int i = 0; i < numMines; i++) {
            do {
                selectedRow = random.nextInt(getRows());
                selectedColumn = random.nextInt(getColumns());
            } while ((selectedRow == row && selectedColumn == column) || hasMine(selectedRow, selectedColumn));
            boardData[selectedRow][selectedColumn] = true;
            System.out.println("Placed mine at " + selectedRow + " " + selectedColumn);
        }
    }
    
    public int countMinesAdjacentTo(int row, int column) {
        System.out.println(row + " " + column);
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i < 0 || j < 0) continue;
                else if (i >= getRows() || j >= getColumns()) continue;
                if (i == row && j == column) continue;
                if (hasMine(i, j)) {
                    count++;
                    System.out.println("count = " + count);
                }
            }
        }
        return count;
    }
}
