package model.FigureCards;

import model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class FigureCardWithStudents extends FigureCard{
    protected List<Student> studentsOnCard;
    protected int studentsNumOnCard;


    public FigureCardWithStudents() throws Exception {

    }

    public void setStudentsOnCard(Set<Student> studentsToCard) throws Exception {
        if(studentsToCard.size()==studentsNumOnCard)
            this.studentsOnCard = new ArrayList<>(studentsToCard);
        else throw new Exception("Wrong students number on a figure card...");
    }

}
