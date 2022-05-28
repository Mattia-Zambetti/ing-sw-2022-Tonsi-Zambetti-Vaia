package model.figureCards;

import model.ExpertMatchInterface;

import java.io.Serializable;

public class Postman extends FigureCard implements Serializable {
    private static int PRICECARD=1;

    public Postman(){
        setPrice(PRICECARD);
        cardId=6;
    }

    public void playCard(ExpertMatchInterface expertMatchInterface){
        expertMatchInterface.setPostManValue();
    }

    @Override
    public String toString() {
        return "Postman card"+super.toString();
    }
}
