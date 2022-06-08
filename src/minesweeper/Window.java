package minesweeper;

import java.awt.FlowLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class Window extends JFrame {
    
    private GamePanel gamePanel;
    
    public Window(String title) {
        super(title);
        gamePanel = new GamePanel();
        setContentPane(gamePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setUpMenuBar();
    }
    
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    
    private void setUpMenuBar() {
        MenuBar mb = new MenuBar();
        Menu gameMenu = new Menu("Game");
        MenuItem newGameItem = new MenuItem("New Game");
        MenuItem exitItem = new MenuItem("Exit");
        gameMenu.add(newGameItem);
        gameMenu.add(exitItem);
        mb.add(gameMenu);
        setMenuBar(mb);
        newGameItem.addActionListener(new MenuListener());
    }
    
    private class MenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog newGameDialog = new JDialog(Window.this, "New Game");
            JLabel rowsLabel = new JLabel("Number of rows: ");
            JLabel columnsLabel = new JLabel("Number of columns: ");
            JLabel minesLabel = new JLabel("Number of mines: ");
            JSpinner rowsSelector = new JSpinner();
            JSpinner columnsSelector = new JSpinner();
            JSpinner minesSelector = new JSpinner();
            JButton okayButton = new JButton("Okay");
            JButton cancelButton = new JButton("Cancel");
            ActionListener buttonListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == okayButton) {
                        gamePanel.startNewGame((int) rowsSelector.getValue(), (int) columnsSelector.getValue(), (int) minesSelector.getValue());
                        newGameDialog.dispose();
                        pack();
                    } else {
                        newGameDialog.dispose();
                    }
                }
                
            };
            okayButton.addActionListener(buttonListener);
            cancelButton.addActionListener(buttonListener);
            rowsSelector.setValue(Minesweeper.DEFAULT_BOARD_ROWS);
            columnsSelector.setValue(Minesweeper.DEFAULT_BOARD_COLUMNS);
            minesSelector.setValue(Minesweeper.DEFAULT_MINES);
            newGameDialog.setLayout(new FlowLayout());
            newGameDialog.getContentPane().add(rowsLabel);
            newGameDialog.getContentPane().add(rowsSelector);
            newGameDialog.getContentPane().add(columnsLabel);
            newGameDialog.getContentPane().add(columnsSelector);
            newGameDialog.getContentPane().add(minesLabel);
            newGameDialog.getContentPane().add(minesSelector);
            newGameDialog.getContentPane().add(okayButton);
            newGameDialog.getContentPane().add(cancelButton);
            newGameDialog.pack();
            newGameDialog.setLocationRelativeTo(Window.this);
            newGameDialog.setVisible(true);
            //gamePanel.startNewGame();
        }
        
    }
}
