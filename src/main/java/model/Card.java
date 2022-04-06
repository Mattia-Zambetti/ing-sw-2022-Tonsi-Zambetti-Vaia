//Tonsi
package model;

public class Card {
    private int value;
    private int movementValue;
    private int id;

    public Card(){

    }
    public Card(int value, int movementValue, int id) {
        this.value = value;
        this.movementValue = movementValue;
        this.id = id;
    }
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

    @Override
    public String toString() {
        return "Card "+id+":\n" +
                "value=" + value +"\n"+
                "movementValue=" + movementValue +"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return id == card.id ||(o==null && this==null);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
