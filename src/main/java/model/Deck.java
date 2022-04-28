//Tonsi
package model;


import model.exception.CardNotFoundException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Deck {
    private final Set<Card> cards;
    private Card currentCard;
    private final Wizard wizard;
    private final JsonImport jsonImport;
    private final static int CARDSNUMBER=10;
    private final static String stringName="Carte.json";
    private int counter=0;

    public Deck(Wizard wizard){
        jsonImport=new JsonImport(stringName);
        cards = new HashSet<>(jsonImport.createCards());
        this.wizard=wizard;
        currentCard=new Card(0,0,0);
    }

    //TESTED
    //Useful for a clone
    public Deck(Deck deck){
        this.cards=new HashSet<>(deck.getCards());
        this.wizard=deck.wizard;
        this.currentCard = new Card(deck.getCurrentCard());

        this.jsonImport=new JsonImport(stringName);
    }





    public int getCounter() {
        return counter;
    }

    public void counterplus(){
        counter++;
    }

    public void resetCounter(){
        counter=0;
    }

    //TESTED
    //It allows to set the card played from the current player, it returns an exception
    //with the absence of the card in the deck. You can give a cloned card as the param
    public void playCard(Card card) throws CardNotFoundException {
        if(cards.contains(card)) {
            currentCard=new Card(card);
            cards.remove(card);
        }else throw new CardNotFoundException("Card not found in the deck");
    }

    //TESTED
    //it returns the current card copy to the caller
    public Card getCurrentCard() {
            return new Card(currentCard);
    }

    //It returns the string version of the wizard (from the enum Wizard) of the deck
    public Wizard getWizard() {
        return wizard;
    }

    //It returns a copy of the cards in the deck to show them in the view
    public Set<Card> getCards(){
        Set<Card> res=new HashSet<Card>();
        for (Card c:cards) {
            res.add(new Card(c));
        }
        return res;
    }

    //TESTED
    //it returns a string with a short description for every card in the deck
    @Override
    public String toString(){
        StringBuilder res= new StringBuilder();
        for (Card c: cards) {
            res.append(c.toString()+"\n");
        }
        return res.toString();
    }

    //TESTED
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deck deck)) return false;

        return deck.getCards().containsAll(((Deck)o).getCards()) &&
                ((Deck)o).getCards().containsAll(deck.getCards())
                && wizard == deck.wizard &&
                ((this.currentCard==null && ((Deck)o).currentCard==null)
                || Objects.equals(currentCard, deck.currentCard));
    }
}
