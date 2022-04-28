package view.choice;

import model.Card;
import model.Match;
import model.exception.CardNotFoundException;
import model.exception.NoMoreCardException;
import view.RemoteView;

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
        StringBuilder string=new StringBuilder( "Choose a card id from your deck:\n ");
        for (Card c:availableCards.values()) {
            string.append(c.toString()+"\n");
        }
        return string.toString();
    }

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)) {
            if(availableCards.get(Integer.parseInt(input))!=null) {
                setChosenCard(Integer.parseInt(input));
                return false;
            }else System.out.println("This card isn't in you deck, try again: ");
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match, RemoteView remoteView) throws NoMoreCardException, CardNotFoundException {
        match.chooseCard(this.getChosenCard());
    }
}
