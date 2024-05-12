package ex4.view.board;

import ex4.controller.Controller;
import ex4.model.ConnectThree;
import ex4.view.board.gameBar.GameBar;
import ex4.view.gridLayout.ColumnBuilder;
import ex4.view.gridLayout.RowBuilder;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class BoardImpl extends JPanel implements MouseInputListener {

    private final BoardListener boardListener;

    public BoardImpl(BoardListener boardListener, Controller controller) {
        this.boardListener = boardListener;

        this.setLayout(new GridBagLayout());

        Set<Tile> tiles = new HashSet<>();
        //TODO
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                tiles.add(new Tile(i, j));
            }
        }

        BoardConstraint boardConstraint = new BoardConstraint();
        GameCostraint gameCostraint = new GameCostraint();

        this.add(new ColumnBuilder(gameCostraint).add(new GameBar(boardListener)).add(new RowBuilder(boardConstraint)
                .add(new ColumnBuilder(boardConstraint)
                        .add(tiles.stream().filter(tile -> tile.getColumn() == 0).collect(Collectors.toUnmodifiableList()))
                        .build())
                .add(new ColumnBuilder(boardConstraint)
                        .add(tiles.stream().filter(tile -> tile.getColumn() == 1).collect(Collectors.toUnmodifiableList()))
                        .build())
                .add(new ColumnBuilder(boardConstraint)
                        .add(tiles.stream().filter(tile -> tile.getColumn() == 2).collect(Collectors.toUnmodifiableList()))
                        .build())
                .add(new ColumnBuilder(boardConstraint)
                        .add(tiles.stream().filter(tile -> tile.getColumn() == 3).collect(Collectors.toUnmodifiableList()))
                        .build()).build()).build(), boardConstraint.getConstraintSetup());

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //TODO
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //TODO
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //TODO
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //TODO
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //TODO
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //TODO
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


}
