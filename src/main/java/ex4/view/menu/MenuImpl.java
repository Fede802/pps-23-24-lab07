package ex4.view.menu;

import ex4.view.gridLayout.ColumnBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuImpl extends JPanel {

    private final MenuListener menuListener;

    public MenuImpl(MenuListener menuListener) {
        this.menuListener = menuListener;
        this.setLayout(new GridBagLayout());
        JButton randomAI = new MenuButton("Play With RandomAI", menuListener);
        randomAI.addActionListener(this.setupGame(GameType.RANDOM));
        JButton smartAI = new MenuButton("Play With SmartAI", menuListener);
        smartAI.addActionListener(this.setupGame(GameType.SMART));
        JButton multiplayer = new MenuButton("Multiplayer", menuListener);
        multiplayer.addActionListener(this.setupGame(GameType.MULTIPLAYER));
        JButton quit = new MenuButton("Quit");
        quit.addActionListener(e -> System.exit(0));


        this.add(new ColumnBuilder(new MenuConstraint())
                .add(randomAI)
                .add(smartAI)
                .add(multiplayer)
                .add(quit).build());

        this.setBackground(Color.RED);
    }

    private ActionListener setupGame(GameType gameType) {
        return e -> {
            //todo
        };
    }

}
