package minesweeper;

public class Minesweeper {

    public static final int DEFAULT_BOARD_ROWS = 10;
    public static final int DEFAULT_BOARD_COLUMNS = 10;
    public static final int DEFAULT_MINES = 10;
    
    private Board board;
    private int numMines;
    private boolean running;

    public Minesweeper(int boardRows, int boardColumns, int numMines) {
        board = new Board(boardRows, boardColumns);
        this.numMines = numMines;
    }
    
    public Board getBoard() {
        return board;
    }
    
    public int getNumMines() {
        return numMines;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public boolean isRunning() {
        return running;
    }
}
