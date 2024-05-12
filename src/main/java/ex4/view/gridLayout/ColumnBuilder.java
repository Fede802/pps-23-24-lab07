package ex4.view.gridLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ColumnBuilder {

    private final GridBagConstraints gridBagConstraints;
    private final JPanel panel = new JPanel();
    private final Constraint constraint;

    public ColumnBuilder(Constraint gridBagConstraints) {
        this.constraint = gridBagConstraints;
        this.gridBagConstraints = gridBagConstraints.getConstraintSetup();
        this.panel.setLayout(new GridBagLayout());
        this.panel.setOpaque(false);
    }

    public ColumnBuilder add(JComponent component) {
        this.gridBagConstraints.gridy = this.gridBagConstraints.gridy + 1;
        this.gridBagConstraints.weightx = constraint.getNextWeightX();
        this.gridBagConstraints.weighty = constraint.getNextWeightY();
        this.panel.add(component, gridBagConstraints);
        return this;
    }

    public ColumnBuilder add(Collection<JComponent> component) {
        component.forEach(this::add);
        return this;
    }

    public JPanel build() {
        return this.panel;
    }

}
