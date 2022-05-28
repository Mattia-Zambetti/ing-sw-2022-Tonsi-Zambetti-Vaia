package model.figureCards;

import controller.choice.HeraldChoice;
import model.ExpertMatchInterface;

import java.io.Serializable;

public class Herald extends FigureCard implements Serializable {
    private static final int PRICECARD=3;



    public Herald(){
        setPrice(PRICECARD);
        cardId=11;
        actualChoice = new HeraldChoice();
    }


    public void playCard(ExpertMatchInterface expertMatchInterface) throws FigureCardAlreadyPlayedInThisTurnException {
        expertMatchInterface.notifyFigureCard(this);
    }

    @Override
    public String toString() {
        return "Herald card"+ super.toString();
    }

}
