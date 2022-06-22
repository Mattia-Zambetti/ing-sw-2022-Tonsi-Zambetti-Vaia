package model.figureCards;

import controller.choice.ThiefChoice;
import model.ExpertMatchInterface;

import java.io.Serializable;

public class Thief extends FigureCard implements Serializable {
    private static final int PRICECARD=3;



    public Thief(){
        setPrice(PRICECARD);
        cardId=9;
        actualChoice = new ThiefChoice();
    }


    public void playCard(ExpertMatchInterface expertMatchInterface) throws FigureCardAlreadyPlayedInThisTurnException {
        expertMatchInterface.notifyFigureCard(this);
    }

    @Override
    public String toString() {
        return "Thief card"+ super.toString();
    }

    public int getPRICECARD() {
        return PRICECARD;
    }
}
