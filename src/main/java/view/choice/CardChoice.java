package view.choice;

import model.Card;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CardChoice extends Choice{
    private int chosenCard;
    private final Map<Integer,Card> availableCards;

    public CardChoice(Set<Card> availableCards){
        this.availableCards =new HashMap<>();
        for (Card c:availableCards) {
            this.availableCards.put(c.getId(), c);
        }

    }

    public void setChosenCard(int chosenCard) {
        this.chosenCard = chosenCard;
    }

    public Card getChosenCard() {
        return availableCards.get(chosenCard);
    }


    @Override
    public String toString() {
        return "Choose a card id from your deck:\n";
    }
}
