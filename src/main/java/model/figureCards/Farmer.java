package model.figureCards;

import javafx.scene.text.Text;
import model.ExpertMatchInterface;
import model.exception.NoMasterException;
import model.exception.WrongColorException;

import java.io.Serializable;

public class Farmer extends FigureCard implements Serializable {
    private static final int PRICECARD=2;

    public Farmer(){
        setPrice(PRICECARD);
        cardId=10;
    }

    public void playCard(ExpertMatchInterface expertMatchInterface) throws FigureCardAlreadyPlayedInThisTurnException, NoMasterException, WrongColorException {
        expertMatchInterface.setIsFarmer();
    }

    @Override
    public String toString() {
        return "Farmer card"+ super.toString();
    }

    public int getPRICECARD() {
        return PRICECARD;
    }


    public static void hintMessage(Text text) {
        text.setText("You can take the other players masters also if you have the same students number of your opponent for 1 turn!");
    }
}
