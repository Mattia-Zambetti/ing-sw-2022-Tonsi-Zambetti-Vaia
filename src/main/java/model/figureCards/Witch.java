package model.figureCards;

import model.ExpertMatchInterface;

public class Witch extends FigureCard {
    private static final int PRICECARD = 2;
    private static int blockCard = 4;


    public Witch() {
        setPrice(PRICECARD);
        cardId=8;
    }


    public void playCard(ExpertMatchInterface expertMatchInterface) {
        expertMatchInterface.notifyIslandFigureCard(this);
    }

    public static void removeBlockCard() throws NoMoreBlockCardsException{
        if(Witch.blockCard > 0)
            Witch.blockCard--;
        else throw new NoMoreBlockCardsException("Block Cards finished");
    }

    public static void addBlockCard() throws NoMoreBlockCardsException{
        if(Witch.blockCard < 4)
            Witch.blockCard++;
        else throw new NoMoreBlockCardsException("Too many block cards");
    }

    @Override
    public String toString() {
        return "I'm the witch";
    }



}
