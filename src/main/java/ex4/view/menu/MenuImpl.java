package ex4.view.menu;

import ex4.view.gridLayout.ColumnBuilder;
import ex4.controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class MenuImpl extends JPanel {

    private final MenuListener menuListener;
    private final Controller controller;

    public MenuImpl(MenuListener menuListener, Controller controller) {
        this.menuListener = menuListener;
        this.controller = controller;
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
            this.controller.setupGame(gameType);
        };
    }

}
