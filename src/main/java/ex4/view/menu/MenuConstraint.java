package ex4.view.menu;

import ex4.view.gridLayout.AbstractConstraint;

import java.awt.*;

public class MenuConstraint extends AbstractConstraint {

    private final static int TOP_MARGIN = 15;

    @Override
    protected GridBagConstraints createConstraints() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(TOP_MARGIN,0,0,0);
        return gridBagConstraints;
    }

}
