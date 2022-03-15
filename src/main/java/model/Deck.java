package model;

import java.util.ArrayList;
import java.util.List;

enum Wizard{WIZARD1,WIZARD2, WIZARD3,WIZARD4}

public class Deck {
    private List<Card> cards;
    private Card currentCard;
    private Wizard wizard;
    public Deck(List<Card> c, String wizard){
        cards = new ArrayList<Card>(c);
        currentCard = null;
        this.wizard=Wizard.valueOf(wizard);
    }

    public void playCard(Card card) throws CardNotFoundException{
        if(cards.contains(card)) {
            currentCard=card;
            cards.remove(card);
        }else throw new CardNotFoundException("carta non trovata");
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public String getWizard() {
        return wizard.toString();
    }
}
