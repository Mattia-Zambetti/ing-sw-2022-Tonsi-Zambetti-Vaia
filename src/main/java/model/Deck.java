package model;


import model.exception.CardNotFoundException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Deck implements Serializable {
    private final Set<Card> cards;
    private Card currentCard;
    private final Wizard wizard;

    public Deck(Wizard wizard){
        //jsonImport=new JsonImport(stringName);
        cards = new HashSet<>(){{
            add(new Card(1,1,1));
            add(new Card(2,1,2));
            add(new Card(3,2,3));
            add(new Card(4,2,4));
            add(new Card(5,3,5));
            add(new Card(6,3,6));
            add(new Card(7,4,7));
            add(new Card(8,4,8));
            add(new Card(9,5,9));
            add(new Card(10,5,10));
        }};

        this.wizard=wizard;
        currentCard=new Card(0,0,0);
    }

    /**Used to create a clone of the deck*/
    public Deck(Deck deck){
        this.cards=new HashSet<>(deck.getCards());
        this.wizard=deck.wizard;
        this.currentCard = new Card(deck.getCurrentCard());
    }



    /**It allows to set the card played from the current player; it throws a CardNotFoundException
    *if the card isn't in the deck. it can take a cloned card as param*/
    public void playCard(Card card) throws CardNotFoundException {
        if(cards.contains(card)) {
            currentCard=new Card(card);
            cards.remove(card);
        }else throw new CardNotFoundException("Card not found in the deck");
    }

    /**it returns the current card copy to the caller*/
    public Card getCurrentCard() {
            return new Card(currentCard);
    }

    /**It returns the string version of the wizard (from the enum Wizard) of the deck*/
    public Wizard getWizard() {
        return wizard;
    }

    /**It returns a copy of the cards in the deck to show them in the view*/
    public Set<Card> getCards(){
        Set<Card> res=new HashSet<Card>();
        for (Card c:cards) {
            res.add(new Card(c));
        }
        return res;
    }

    /**it returns a string with a short description for every card in the deck*/
    @Override
    public String toString(){
        StringBuilder res= new StringBuilder();
        for (Card c: cards) {
            res.append(c.toString()+"\n");
        }
        return res.toString();
    }

    /**it returns true if the object o is a Deck and if o and this have the same cards, the same wizard and the same current card*/
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
