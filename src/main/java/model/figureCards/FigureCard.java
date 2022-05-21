//Tonsi
package model.figureCards;

import controller.choice.Choice;
import model.ExpertMatchInterface;

import java.io.Serializable;

public abstract class FigureCard implements Serializable {
    protected int price;
    protected int cardId;

    public Choice actualChoice;

    public Choice getActualChoice() {
        return actualChoice;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FigureCard)) return false;
        FigureCard that = (FigureCard) o;
        return cardId == that.cardId;
    }

    //TODO DA TESTARE
    @Override
    public int hashCode() {
        return cardId;
    }
}
