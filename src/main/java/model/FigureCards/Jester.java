package model.FigureCards;

import model.Bag;

public class Jester extends FigureCardWithStudents{

    private static final int PRICECARD=1;
    private static final int MAXTAKESTUDENTSNUM=3;


    public Jester() throws Exception {
        setPrice(PRICECARD);
        studentsNumOnCard =6;
        setStudentsOnCard(Bag.removeStudents(studentsNumOnCard));
        cardId=2;
        maxTakeStudentsNum=MAXTAKESTUDENTSNUM;
    }




    @Override
    public String toString() {
        return "I'm the jester, I've got "+studentsOnCard.size()+" students";
    }

}


