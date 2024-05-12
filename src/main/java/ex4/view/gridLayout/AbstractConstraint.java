package ex4.view.gridLayout;

import java.awt.*;

public abstract class AbstractConstraint implements Constraint {

    private final GridBagConstraints gridBagConstraints;

    public AbstractConstraint() {
        this.gridBagConstraints = createConstraints();
    }

    protected abstract GridBagConstraints createConstraints();

    @Override
    public GridBagConstraints getConstraintSetup() {
        return (GridBagConstraints) gridBagConstraints.clone();
    }

    @Override
    public double getNextWeightX() {
        return 1;
    }

    @Override
    public double getNextWeightY() {
        return 1;
    }
}
