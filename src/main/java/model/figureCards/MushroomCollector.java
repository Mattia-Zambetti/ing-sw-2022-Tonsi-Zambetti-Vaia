package model.figureCards;

import controller.choice.MerchantChoice;
import controller.choice.MushroomCollectorChoice;
import model.ExpertMatchInterface;

import java.io.Serializable;

public class MushroomCollector extends FigureCard  implements Serializable {
    private static final int PRICECARD=3;
    public MushroomCollector(){
        setPrice(PRICECARD);
        cardId=5;
        actualChoice = new MushroomCollectorChoice();
    }


    public void playCard(ExpertMatchInterface expertMatchInterface){
        expertMatchInterface.notifyIslandFigureCard(this);
    }

    @Override
    public String toString() {
        return "Mushroom collector card"+ super.toString();
    }



}
