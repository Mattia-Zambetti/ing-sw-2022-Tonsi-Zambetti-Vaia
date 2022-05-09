package model.figureCards;

import model.ExpertMatchInterface;

public class Postman extends FigureCard{
    private static final int PRICECARD=1;

    public Postman(){
        setPrice(PRICECARD);
        cardId=6;
    }

    public void playCard(ExpertMatchInterface expertMatchInterface){
        expertMatchInterface.setPostManValue();
    }
}