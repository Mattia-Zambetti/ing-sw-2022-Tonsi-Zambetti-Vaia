package model.figureCards;

import model.ExpertMatchInterface;

import java.io.Serializable;

public class Farmer extends FigureCard implements Serializable {
    private static final int PRICECARD=2;

    public Farmer(){
        setPrice(PRICECARD);
        cardId=10;
    }

    public void playCard(ExpertMatchInterface expertMatchInterface) throws FigureCardAlreadyPlayedInThisTurnException {
        expertMatchInterface.setIsFarmer();
    }

    @Override
    public String toString() {
        return "Farmer card"+ super.toString();
    }

    public int getPRICECARD() {
        return PRICECARD;
    }
}
