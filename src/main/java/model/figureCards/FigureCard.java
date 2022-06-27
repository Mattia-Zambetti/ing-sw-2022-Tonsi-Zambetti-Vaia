//Tonsi
package model.figureCards;

import controller.choice.Choice;
import model.ExpertMatchInterface;
import model.exception.NoMasterException;
import model.exception.WrongColorException;

import java.io.Serializable;

public abstract class FigureCard implements Serializable {
    protected int price;
    protected int cardId;

    protected Choice actualChoice;

    public int getCardId() {
        return cardId;
    }

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

    public abstract void playCard(ExpertMatchInterface expertMatchInterface) throws FigureCardAlreadyPlayedInThisTurnException, NoMasterException, WrongColorException;

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

    @Override
    public String toString() {
        if(getPrice()==1) {
            return "(price: " + getPrice() + " coin)";
        }else
            return "(price: " + getPrice() + " coins)";
    }

    public abstract int getPRICECARD();
}
