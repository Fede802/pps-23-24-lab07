package ex4.view.board.gameBar;

import ex4.view.board.BoardListener;
import ex4.view.gridLayout.RowBuilder;

import javax.swing.*;

public class GameBar extends JPanel {

    public GameBar(BoardListener boardListener){
        JLabel label = new JLabel("PLAYER: ");
        JButton menuButton = new JButton("MENU");
        menuButton.addActionListener(e -> boardListener.openMenu());
        this.add(new RowBuilder(new GameBarConstraint())
                .add(label)
                .add(menuButton).build());
    }

}
