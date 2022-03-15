package model;

enum Wizard{WIZARD1,WIZARD2, WIZARD3,WIZARD4};

public class Deck {
    private Card cards[];
    private Card currentCard;
    public Deck(Card c[]){
        cards = c;
        currentCard = null;
    }


}
