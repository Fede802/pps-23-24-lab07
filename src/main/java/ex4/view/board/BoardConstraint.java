package ex4.view.board;

import ex4.view.gridLayout.AbstractConstraint;

import java.awt.*;

public class BoardConstraint extends AbstractConstraint {



    @Override
    protected GridBagConstraints createConstraints() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        return gridBagConstraints;
    }


}
