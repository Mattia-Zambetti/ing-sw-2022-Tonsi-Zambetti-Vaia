package model.figureCards;

import model.ExpertMatchInterface;

import java.io.Serializable;

public class Centaur extends FigureCard implements Serializable {
    private static final int PRICECARD=3;



    public Centaur(){
        setPrice(PRICECARD);
        cardId=1;
    }


    public void playCard(ExpertMatchInterface expertMatchInterface) throws FigureCardAlreadyPlayedInThisTurnException {
        if(!expertMatchInterface.isCentaurEffect()){
            expertMatchInterface.setCentaurEffect(true);
        }else throw new FigureCardAlreadyPlayedInThisTurnException("This card has already been played...");
    }

    @Override
    public String toString() {
        return "I'm the centaur";
    }

}
