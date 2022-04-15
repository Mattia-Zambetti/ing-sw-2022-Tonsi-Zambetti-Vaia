package model.FigureCards;

import model.ExpertMatchInterface;

public class Witch extends FigureCard {
    private static final int PRICECARD=2;
    private static int blockCard=4;

    protected static boolean alreadyPlayed=false;

    public Witch(){
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

