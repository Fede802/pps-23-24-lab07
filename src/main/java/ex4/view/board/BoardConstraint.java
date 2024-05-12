package ex4.view.board;

import ex4.view.gridLayout.AbstractConstraint;

import java.awt.*;

public class BoardConstraint extends AbstractConstraint {


    private double weightY = 0.2;

    @Override
    protected GridBagConstraints createConstraints() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        return gridBagConstraints;
    }

    @Override
    public double getNextWeightY() {
        double weightY = this.weightY;
        this.weightY = 1.0;
        return weightY;
    }

}
