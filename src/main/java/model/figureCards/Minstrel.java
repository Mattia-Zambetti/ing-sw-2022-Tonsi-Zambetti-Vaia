package model.figureCards;
import controller.choice.MinstrelChoice;
import model.ExpertMatchInterface;

import java.io.Serializable;

public class Minstrel extends FigureCard  implements Serializable {

    private static int PRICECARD=1;


    public Minstrel() throws Exception {
        setPrice(PRICECARD);
        cardId=12;
        actualChoice = new MinstrelChoice();
    }


    @Override
    public void playCard(ExpertMatchInterface expertMatchInterface) throws FigureCardAlreadyPlayedInThisTurnException {
        expertMatchInterface.notifyFigureCard(this);
    }

    @Override
    public String toString() {
        return "Minstrel card"+super.toString();
    }



}