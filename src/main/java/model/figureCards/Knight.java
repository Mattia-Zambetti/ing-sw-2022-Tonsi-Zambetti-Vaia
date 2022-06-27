package model.figureCards;

import javafx.scene.text.Text;
import model.ExpertMatchInterface;

import java.io.Serializable;

public class Knight extends FigureCard implements Serializable {
    private static final int PRICECARD = 2;

    public Knight() {
        setPrice(PRICECARD);
        cardId=3;
    }

    public void playCard(ExpertMatchInterface expertMatchInterface) {
        expertMatchInterface.setIsKnight();
    }

    @Override
    public String toString() {
        return "Knight card"+super.toString();
    }


    public static void hintMessage(Text text, boolean isCurrentPlayer) {
        if(isCurrentPlayer)
            text.setText("+2 additional point in influence calculation in this turn!");
        else
            text.setText("It's been played the knight, +2 additional point in influence calculation in this turn to the current player!");
    }

    public int getPRICECARD() {
        return PRICECARD;
    }
}