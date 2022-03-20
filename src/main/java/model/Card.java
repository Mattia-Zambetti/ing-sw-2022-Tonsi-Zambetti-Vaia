//Tonsi
package model;

public class Card {
    private int value;
    private int movementValue;
    private int Id;

    public Card(int v, int m, int I) {
        value = v;
        movementValue = m;
        Id = I;
    }

    public int getValue() {
        return value;
    }

    public int getMovementValue() {
        return movementValue;
    }

    public int getId() {
        return Id;
    }
}
