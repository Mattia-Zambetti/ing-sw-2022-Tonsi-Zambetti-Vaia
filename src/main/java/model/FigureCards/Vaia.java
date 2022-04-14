package model.FigureCards;

import model.ExpertMatchInterface;

public class Vaia extends FigureCard {
    private static final int PRICECARD=3;

    protected static boolean alreadyPlayed=false;

    public Vaia(){
        setPrice(PRICECARD);
        alreadyPlayed=true;
    }

    public static boolean isAlreadyPlayed() {
        return alreadyPlayed;
    }

    public void playCard(ExpertMatchInterface expertMatchInterface){

    }

    @Override
    public String toString() {
        return "Hai vinto cucciolo";
    }

}
