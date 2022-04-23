package view.choice;

import model.Card;

public class CardChoice implements Choice{
    private Card chosenCard;

    public CardChoice(){

    }

    public void setChosenCard(Card chosenCard) {
        this.chosenCard = chosenCard;
    }

    public Card getChosenCard() {
        return chosenCard;
    }
}
