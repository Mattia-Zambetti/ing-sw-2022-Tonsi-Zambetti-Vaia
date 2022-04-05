//Tonsi
package model;


import model.exception.CardNotFoundException;
import java.util.HashSet;
import java.util.Set;

enum Wizard{WIZARD1,WIZARD2, WIZARD3,WIZARD4}

public class Deck {
    private Set<Card> cards;
    private Card currentCard;
    private Wizard wizard;
    private JsonImport jsonImport;
    private final static int CARDSNUMBER=10;
    private final static String stringName="Carte.json";

    public Deck(Wizard wizard){
        jsonImport=new JsonImport(stringName);
        cards = new HashSet<Card>(jsonImport.createCards());
        currentCard = null;
        this.wizard=wizard;
    }

    //Useful for a clone
    public Deck(Deck deck){
        this.cards=deck.getCards();
        this.wizard=deck.wizard;
        this.currentCard= deck.getCurrentCard();
        this.jsonImport=new JsonImport(stringName);
    }


    public void playCard(Card card) throws CardNotFoundException {
        if(cards.contains(card)) {
            currentCard=card;
            cards.remove(card);
        }else throw new CardNotFoundException("carta non trovata");
    }

    public Card getCurrentCard() {
        return new Card(currentCard.getValue(), currentCard.getMovementValue(), currentCard.getId());
    }

    public String getWizard() {
        return wizard.toString();
    }

    public Set<Card> getCards(){
        return new HashSet<Card>(cards);
    }


}
