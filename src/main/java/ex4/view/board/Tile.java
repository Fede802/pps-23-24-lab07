package ex4.view.board;

import ex4.model.Player;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {

    private final int row;
    private final int column;
    private Player player;

    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        Random random = new Random();
        this.setBackground(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));

    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setPlayer(Player player) {
        this.player = player;
        System.out.println(this.player == Player.X);
        if (this.player == Player.X ){ this.setBackground(Color.GREEN);}else{ this.setBackground(Color.BLUE);}
    }

    @Override
    public String toString() {
        return "Tile{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
