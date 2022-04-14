//Tonsi
package model.FigureCards;

import model.ExpertMatchInterface;

public abstract class FigureCard {
    protected int price;


    protected void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public abstract void playCard(ExpertMatchInterface expertMatchInterface);
}
