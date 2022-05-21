package controller.choice;

import model.Student;
import model.figureCards.FigureCard;
import model.figureCards.FigureCardWithStudents;
import model.figureCards.Merchant;

import java.util.HashSet;
import java.util.Set;

public abstract class FigureCardWithStudentsChoice extends FigureCardActionChoice{
    protected int chosenStudent;
    protected FigureCardWithStudents figureCardWithStudents;

    public FigureCardWithStudentsChoice(FigureCardWithStudents figureCardWithStudents){
        this.figureCardWithStudents = figureCardWithStudents;
    }
    //FigureCardWithStudents thisFigureCard;
    protected Set chosenStudents = new HashSet<>();

    public Set<Student> getChosenStudent() {
        return chosenStudents;
    }

    public void setChosenStudent() {
        chosenStudents.add(figureCardWithStudents.getStudentsOnCardByInt(chosenStudent));
    }
}
