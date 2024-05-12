package ex4.view;

import ex4.view.board.BoardImpl;
import ex4.view.board.BoardListener;
import ex4.view.menu.MenuImpl;
import ex4.view.menu.MenuListener;

import javax.swing.*;

public class FrameImpl extends JFrame implements Frame, MenuListener, BoardListener {

    private final MenuImpl menu;
    private final BoardImpl board;

    public FrameImpl() {
        this.menu = new MenuImpl(this);
        this.board = new BoardImpl(this);
        this.getContentPane().add(board);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setVisible(true);
    }

    @Override
    public void openMenu() {
        this.getContentPane().remove(board);
        this.getContentPane().add(menu);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void openBoard() {
        this.getContentPane().remove(menu);
        this.getContentPane().add(board);
        this.revalidate();
        this.repaint();
    }
}
