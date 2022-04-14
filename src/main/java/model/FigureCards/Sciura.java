package model.FigureCards;

import model.ExpertMatchInterface;

public class Sciura extends FigureCard {
    private static final int PRICECARD=2;
    private static int blockCard=4;

    protected static boolean alreadyPlayed=false;

    public Sciura(){
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
        return "I'm the witch";
    }
}

