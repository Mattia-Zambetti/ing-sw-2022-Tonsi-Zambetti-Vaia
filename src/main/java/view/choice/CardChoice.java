package view.choice;

public class CardChoice implements Choice{
    private int chosenCard;

    public CardChoice(){

    }

    public void setChosenCard(int chosenCard) {
        this.chosenCard = chosenCard;
    }

    public int getChosenCard() {
        return chosenCard;
    }


    @Override
    public String toString() {
        return "Choose a card from your deck, insert the id: ";
    }
}
