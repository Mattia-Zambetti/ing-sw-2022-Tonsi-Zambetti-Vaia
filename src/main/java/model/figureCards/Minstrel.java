package model.figureCards;
import controller.choice.MerchantChoice;
import controller.choice.MinstrelChoice;
import model.Bag;
import model.ExpertMatchInterface;

import java.io.Serializable;

public class Minstrel extends FigureCard  implements Serializable {

    private static final int PRICECARD=1;


    public Minstrel() throws Exception {
        setPrice(PRICECARD);
        cardId=4;
        actualChoice = new MinstrelChoice();
    }


    @Override
    public void playCard(ExpertMatchInterface expertMatchInterface) throws FigureCardAlreadyPlayedInThisTurnException {
        expertMatchInterface.notifyIslandFigureCard(this);
    }

    @Override
    public String toString() {
        return "Minstrel card"+super.toString();
    }



}