package ex4.view.board.gameBar;

import ex4.view.gridLayout.AbstractConstraint;

import java.awt.*;

public class GameBarConstraint extends AbstractConstraint {

    private final static int TOP_MARGIN = 10;
    private final static int LEFT_MARGIN = 30;
    private final static int RIGHT_MARGIN = 30;
    private final static int BOTTOM_MARGIN = 10;

    @Override
    protected GridBagConstraints createConstraints() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(TOP_MARGIN, LEFT_MARGIN, BOTTOM_MARGIN, RIGHT_MARGIN);
        return gridBagConstraints;
    }
}
