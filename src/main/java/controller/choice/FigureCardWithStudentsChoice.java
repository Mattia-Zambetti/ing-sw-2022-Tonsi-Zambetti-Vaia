package controller.choice;

import model.Student;
import model.figureCards.FigureCardWithStudents;

import java.util.HashSet;
import java.util.Set;

public abstract class FigureCardWithStudentsChoice extends FigureCardActionChoice{
    protected int chosenStudent;
    protected FigureCardWithStudents figureCardWithStudents;

    public FigureCardWithStudentsChoice(FigureCardWithStudents figureCardWithStudents){
        this.figureCardWithStudents = figureCardWithStudents;
    }

    protected Set<Student> chosenStudents = new HashSet<>();

    public Set<Student> getChosenStudent() {
        return new HashSet<>(chosenStudents);
    }

    public void setChosenStudent() {
        chosenStudents.add(figureCardWithStudents.getStudentsOnCardByInt(chosenStudent));
    }
}
