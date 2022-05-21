package model.figureCards;
import controller.choice.MerchantChoice;
import model.Bag;

import java.io.Serializable;

public class Merchant extends FigureCardWithStudents  implements Serializable {

    private static final int PRICECARD=1;
    private static final int MAXTAKESTUDENTSNUM=1;
    private static final int STUDENTSNUMONCARD=4;



    public Merchant() throws Exception {
        setPrice(PRICECARD);
        studentsNumOnCard =STUDENTSNUMONCARD;
        setStudentsOnCard(Bag.removeStudents(studentsNumOnCard));
        cardId=4;
        maxTakeStudentsNum=MAXTAKESTUDENTSNUM;
        actualChoice = new MerchantChoice(this);
    }


    @Override
    public String toString() {
        return "Merchant card"+super.toString();
    }



}