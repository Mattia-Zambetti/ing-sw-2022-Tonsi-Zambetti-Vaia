package model.figureCards;

import model.ExpertMatchInterface;

public class MushroomCollector extends FigureCard {
    private static final int PRICECARD=3;
    public MushroomCollector(){
        setPrice(PRICECARD);
        cardId=5;
    }


    public void playCard(ExpertMatchInterface expertMatchInterface){
        expertMatchInterface.notifyIslandFigureCard(this);
    }

    @Override
    public String toString() {
        return "I'm the mushroom collector";
    }



}
