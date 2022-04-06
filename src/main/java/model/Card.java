//Tonsi
package model;

public class Card {
    private int value;
    private int movementValue;
    private int id;

    public Card(int value, int movementValue, int id) {
        this.value = value;
        this.movementValue = movementValue;
        this.id = id;
    }

    //TESTED
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

    //TESTED
    //Useful to obtain a representation of a card in string format
    public String toString() {
        return "Card "+id+":\n" +
                "value: " + value +"\n"+
                "movementValue: " + movementValue +"\n";
    }

    //
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return id == card.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
