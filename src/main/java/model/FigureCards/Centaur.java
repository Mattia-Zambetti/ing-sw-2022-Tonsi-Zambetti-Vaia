package model.FigureCards;

import model.ExpertMatchInterface;

public class Centaur extends FigureCard {
    private static final int PRICECARD=3;

    protected static boolean alreadyPlayed=false;

    public Centaur(){
        setPrice(PRICECARD);
        alreadyPlayed=true;
    }

    public static boolean isAlreadyPlayed() {
        return alreadyPlayed;
    }

    public void playCard(ExpertMatchInterface expertMatchInterface) throws FigureCardAlreadyPlayedInThisTurnException {
        if(!expertMatchInterface.isCentaurEffect()){
            expertMatchInterface.setCentaurEffect(true);
        }else throw new FigureCardAlreadyPlayedInThisTurnException("This card has already been played...");
    }

    @Override
    public String toString() {
        return "I'm the centaur";
    }

}
