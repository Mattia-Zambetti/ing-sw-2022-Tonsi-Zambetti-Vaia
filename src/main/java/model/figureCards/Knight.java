package model.figureCards;

import model.ExpertMatchInterface;

public class Knight extends FigureCard {
    private static final int PRICECARD = 2;

    public Knight() {
        setPrice(PRICECARD);
        cardId=3;
    }

    public void playCard(ExpertMatchInterface expertMatchInterface) {
        expertMatchInterface.setIsKnight();
    }

    @Override
    public String toString() {
        return "I'm the knight";
    }

}