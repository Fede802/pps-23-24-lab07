package ex4.view.gridLayout;

import javax.swing.*;
import java.awt.*;

public class RowBuilder {

    private final GridBagConstraints gridBagConstraints;
    private final JPanel panel = new JPanel();

    public RowBuilder(Constraint gridBagConstraints) {
        this.gridBagConstraints = gridBagConstraints.getConstraintSetup();
        this.panel.setLayout(new GridBagLayout());
        this.panel.setOpaque(false);
    }

    public RowBuilder add(JComponent component) {
        this.gridBagConstraints.gridx = this.gridBagConstraints.gridx + 1;
        this.panel.add(component, gridBagConstraints);
        return this;
    }

    public JPanel build() {
        return this.panel;
    }

}
