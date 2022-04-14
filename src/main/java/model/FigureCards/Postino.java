package model.FigureCards;

import model.ExpertMatchInterface;

public class Postino extends FigureCard{
    private static final int PRICECARD=1;

    protected static boolean alreadyPlayed=false;

    public Postino(){
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
        return "I'm the postman";
    }
}
