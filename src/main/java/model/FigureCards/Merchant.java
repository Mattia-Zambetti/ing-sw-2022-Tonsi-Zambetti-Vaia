package model.FigureCards;
import model.Bag;

public class Merchant extends FigureCardWithStudents {

    private static final int PRICECARD=1;
    private static final int MAXTAKESTUDENTSNUM=1;
    private static final int STUDENTSNUMONCARD=4;



    public Merchant() throws Exception {
        setPrice(PRICECARD);
        studentsNumOnCard =STUDENTSNUMONCARD;
        setStudentsOnCard(Bag.removeStudents(studentsNumOnCard));
        cardId=4;
        maxTakeStudentsNum=MAXTAKESTUDENTSNUM;
    }


    @Override
    public String toString() {
        return "I'm the merchant, I've got "+studentsOnCard.size()+" students";
    }



}