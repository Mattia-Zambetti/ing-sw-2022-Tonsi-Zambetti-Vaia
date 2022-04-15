package model.FigureCards;

import model.ExpertMatchInterface;

public class Knight extends FigureCard {
    private static final int PRICECARD=2;
    protected static boolean alreadyPlayed=false;

    public Knight(){
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
        return "I'm the knight";
    }
}