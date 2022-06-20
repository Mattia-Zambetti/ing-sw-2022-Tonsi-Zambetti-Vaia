package model.figureCards;

import controller.choice.GrannyGrassChoice;
import model.ExpertMatchInterface;

import java.io.Serializable;

public class GrannyGrass extends FigureCard  implements Serializable {
    private static final int PRICECARD = 2;
    private static int blockCard = 4;


    public GrannyGrass() {
        setPrice(PRICECARD);
        cardId=8;
        actualChoice = new GrannyGrassChoice();
    }


    public void playCard(ExpertMatchInterface expertMatchInterface) {
        expertMatchInterface.notifyFigureCard(this);
    }

    public static void removeBlockCard() throws NoMoreBlockCardsException{
        if(GrannyGrass.blockCard > 0)
            GrannyGrass.blockCard--;
        else throw new NoMoreBlockCardsException("Block Cards finished");
    }

    //TODO DA TESTARE
    public static void addBlockCard() throws NoMoreBlockCardsException{
        if(GrannyGrass.blockCard < 4)
            GrannyGrass.blockCard++;
        else throw new NoMoreBlockCardsException("Too many block cards");
    }

    @Override
    public String toString() {
        return "Granny grass card" + super.toString();
    }


    public static int getBlockCard() {
        return blockCard;
    }
}
