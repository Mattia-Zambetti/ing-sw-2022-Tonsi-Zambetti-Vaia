package model.FigureCards;

import model.ExpertMatchInterface;

public class Witch extends FigureCard {
    private static final int PRICECARD = 2;
    private static int blockCard = 4;


    public Witch() {
        setPrice(PRICECARD);
        cardId=8;
    }


    public void playCard(ExpertMatchInterface expertMatchInterface) {

    }

    @Override
    public String toString() {
        return "I'm the witch";
    }



}
