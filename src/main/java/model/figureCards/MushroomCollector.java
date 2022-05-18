package model.figureCards;

import model.ExpertMatchInterface;

import java.io.Serializable;

public class MushroomCollector extends FigureCard  implements Serializable {
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
        return "Mushroom collector card"+ super.toString();
    }



}
