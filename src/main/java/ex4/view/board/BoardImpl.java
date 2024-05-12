package ex4.view.board;

import ex4.view.board.gameBar.GameBar;
import ex4.view.gridLayout.ColumnBuilder;
import ex4.view.gridLayout.RowBuilder;

import javax.swing.*;
import java.awt.*;

public class BoardImpl extends JPanel {

    private final BoardListener boardListener;

    public BoardImpl(BoardListener boardListener) {
        this.boardListener = boardListener;

        this.setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.GREEN);
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.BLUE);
        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.YELLOW);
        JPanel panel5 = new JPanel();
        panel5.setBackground(Color.CYAN);
        JPanel panel6 = new JPanel();
        panel6.setBackground(Color.MAGENTA);
        JPanel panel7 = new JPanel();
        panel7.setBackground(Color.PINK);
        JPanel panel8 = new JPanel();
        panel8.setBackground(Color.ORANGE);
        JPanel panel9 = new JPanel();
        panel9.setBackground(Color.LIGHT_GRAY);
        JPanel panel10 = new JPanel();
        panel10.setBackground(Color.DARK_GRAY);
        JPanel panel11 = new JPanel();
        panel11.setBackground(Color.GRAY);
        JPanel panel12 = new JPanel();
        panel12.setBackground(Color.BLACK);
        JPanel panel13 = new JPanel();
        panel13.setBackground(Color.WHITE);
        JPanel panel14 = new JPanel();
        panel14.setBackground(Color.BLUE);
        JPanel panel15 = new JPanel();
        panel15.setBackground(Color.RED);
        JPanel panel16 = new JPanel();
        panel16.setBackground(Color.GREEN);

        BoardConstraint boardConstraint = new BoardConstraint();

        this.add(new ColumnBuilder(boardConstraint).add(new GameBar()).add(new RowBuilder(boardConstraint)
                .add(new ColumnBuilder(boardConstraint)
                        .add(panel)
                        .add(panel2)
                        .add(panel3)
                        .add(panel4)
                        .build())
                .add(new ColumnBuilder(boardConstraint)
                        .add(panel5)
                        .add(panel6)
                        .add(panel7)
                        .add(panel8)
                        .build())
                .add(new ColumnBuilder(boardConstraint)
                        .add(panel9)
                        .add(panel10)
                        .add(panel11)
                        .add(panel12)
                        .build())
                .add(new ColumnBuilder(boardConstraint)
                        .add(panel13)
                        .add(panel14)
                        .add(panel15)
                        .add(panel16)
                        .build()).build()).build(), boardConstraint.getConstraintSetup());

    }
}
