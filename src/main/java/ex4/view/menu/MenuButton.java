package ex4.view.menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuButton extends JButton {

    private static final List<JButton> buttons = new ArrayList<>();
    private static double width = 0;

    public MenuButton(String text, MenuListener listener) {
        super(text);
        this.updateDimensions();
        if(listener != null){
            this.addActionListener(e -> listener.openBoard());
        }
    }

    public MenuButton(String text) {
        this(text, null);
    }

    private void updateDimensions() {
        buttons.add(this);
        if(width < this.getPreferredSize().getWidth()) {
            width = this.getPreferredSize().getWidth();
            buttons.forEach(button -> button.setPreferredSize(new Dimension((int) width, (int) this.getPreferredSize().getHeight())));
        }else {
            this.setPreferredSize(new Dimension((int) width, (int) this.getPreferredSize().getHeight()));
        }

    }
}
