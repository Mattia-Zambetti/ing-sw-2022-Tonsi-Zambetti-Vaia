package model.figureCards;

import model.Bag;
import model.Student;
import model.exception.MaxNumberException;

import java.io.Serializable;
import java.util.Set;

public class Jester extends FigureCardWithStudents implements Serializable {

    private static final int PRICECARD=1;
    private static final int MAXTAKESTUDENTSNUM=3;
    private static final int STUDENTSNUMONCARD=6;


    public Jester() throws Exception {
        setPrice(PRICECARD);
        studentsNumOnCard =STUDENTSNUMONCARD;
        setStudentsOnCard(Bag.removeStudents(studentsNumOnCard));
        cardId=2;
        maxTakeStudentsNum=MAXTAKESTUDENTSNUM;
    }


    public void refillStudents(Set<Student> studentsFromEntrance){
        try{
            if(studentsFromEntrance.size()==STUDENTSNUMONCARD-studentsOnCard.size()){
                studentsOnCard.addAll(studentsFromEntrance);

            }else throw new MaxNumberException("Wrong students number given to this figure card...");
        }catch (MaxNumberException e){
            System.out.println(e.getMessage());
        }

    }





    @Override
    public String toString() {
        return "Jester card"+super.toString();
    }

}


