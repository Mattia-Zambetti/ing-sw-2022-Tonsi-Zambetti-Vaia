package model.figureCards;

import model.ExpertMatchInterface;

import java.io.Serializable;

public class Knight extends FigureCard  implements Serializable {
    private static int PRICECARD = 2;

    public Knight() {
        setPrice(PRICECARD);
        cardId=3;
    }

    public void playCard(ExpertMatchInterface expertMatchInterface) {
        expertMatchInterface.setIsKnight();
    }

    @Override
    public String toString() {
        return "Knight card"+super.toString();
    }

}