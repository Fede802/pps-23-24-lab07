package ex4.view.gridLayout;

import java.awt.*;

public interface Constraint {

    GridBagConstraints getConstraintSetup();

    double getNextWeightX();

    double getNextWeightY();

}
