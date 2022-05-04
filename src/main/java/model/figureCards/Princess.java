package model.figureCards;
import model.Bag;

import java.io.Serializable;

public class Princess extends FigureCardWithStudents  implements Serializable {
    private static final int PRICECARD=2;
    private static final int MAXTAKESTUDENTSNUM=1;
    private static final int STUDENTSNUMONCARD=4;


    public Princess() throws Exception {
        setPrice(PRICECARD);
        studentsNumOnCard =STUDENTSNUMONCARD;
        setStudentsOnCard(Bag.removeStudents(studentsNumOnCard));
        cardId=7;
        maxTakeStudentsNum=MAXTAKESTUDENTSNUM;
    }
    @Override
    public String toString() {
        return "I'm the princess, I've got "+studentsOnCard.size()+" students";
    }



}