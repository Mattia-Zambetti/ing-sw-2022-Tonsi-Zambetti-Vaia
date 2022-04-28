//Tonsi, TESTED
package model;

import java.io.Serializable;

public class Card implements Serializable {
    private final int value;
    private final int movementValue;
    private final int id;

    public Card(int value, int movementValue, int id) {
        this.value = value;
        this.movementValue = movementValue;
        this.id = id;
    }

    //Used to obtain a copy of the card
    public Card(Card card){
        this.value = card.value;
        this.movementValue = card.movementValue;
        this.id = card.id;
    }

    public int getValue() {
        return value;
    }

    public int getMovementValue() {
        return movementValue;
    }

    public int getId() {
        return id;
    }

    //Useful to obtain a representation of a card in string format
    public String toString() {
        return "Card "+id+":\n" +
                "value: " + value +"\n"+
                "movementValue: " + movementValue +"\n";
    }

    //It returns true if two cards have the same id
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return id == card.id;
    }

    //Every card is identified by the id with this method in a collection like the HashSet
    @Override
    public int hashCode() {
        return id;
    }
}
