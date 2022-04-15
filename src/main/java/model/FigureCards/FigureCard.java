//Tonsi
package model.FigureCards;

import model.ExpertMatchInterface;

public abstract class FigureCard {
    protected int price;


    public void pricePlusPlus() {
        this.price++;
    }

    public int getPrice() {
        return price;
    }

    protected void setPrice(int price) {
        this.price = price;
    }

    public abstract void playCard(ExpertMatchInterface expertMatchInterface) throws FigureCardAlreadyPlayedInThisTurnException;
}
