package minesweeper;

public class Main {
    public static void main(String[] args) {
        Window window = new Window("Minesweeper");
        window.getGamePanel().startDefaultGame();
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
