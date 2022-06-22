package model.figureCards;

import javafx.scene.text.Text;
import model.ExpertMatchInterface;

import java.io.Serializable;

public class Postman extends FigureCard implements Serializable {
    private static final int PRICECARD=1;

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

    public int getPRICECARD() {
        return PRICECARD;
    }


    public static void hintMessage(Text text) {
        text.setText("+2 to the the mother nature maximum moves in this turn!");
    }
}
