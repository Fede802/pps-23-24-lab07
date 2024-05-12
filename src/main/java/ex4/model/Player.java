package ex4.model;

public enum Player {
    X, O;
    
    public Player other() {
        return this == X ? O : X;
    }
    
}
