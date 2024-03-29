package model.figureCards;

import model.Bag;
import model.ExpertMatchInterface;
import model.Student;
import model.exception.InexistentStudentException;
import model.exception.MaxNumberException;
import model.exception.NoMoreStudentsException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class FigureCardWithStudents extends FigureCard implements Serializable {
    protected List<Student> studentsOnCard;
    protected int studentsNumOnCard;
    protected int maxTakeStudentsNum;


    public FigureCardWithStudents() throws Exception {

    }

    protected void setStudentsOnCard(Set<Student> studentsToCard) throws Exception {
        if(studentsToCard.size()==studentsNumOnCard)
            this.studentsOnCard = new ArrayList<>(studentsToCard);
        else throw new Exception("Wrong students number on a figure card...");
    }

    public boolean takeStudents(Set<Student> chosenStudents) throws MaxNumberException, NoMoreStudentsException, InexistentStudentException {
        if(studentsOnCard.containsAll(chosenStudents)) {

            if (maxTakeStudentsNum >= chosenStudents.size() && chosenStudents.size() != 0) {
                for (Student s : chosenStudents) {
                    studentsOnCard.remove(s);
                }
            } else
                throw new MaxNumberException("Wrong students size, you can take only " + maxTakeStudentsNum + " students on this card and at least 1...");
        }else throw new InexistentStudentException("This student isn't on this figure card...");
        if(!(this instanceof Jester))
            studentsOnCard.addAll(Bag.removeStudents(chosenStudents.size()));


        return true;
    }

    public List<Student> getStudentsOnCard(){
        return new ArrayList<>(studentsOnCard);
    }

    //TODO DA TESTARE
    public Student getStudentsOnCardByInt(int studentChosen){
        return studentsOnCard.get(studentChosen);
    }

    @Override
    public void playCard(ExpertMatchInterface expertMatchInterface){
        expertMatchInterface.notifyFigureCard(this);
    }

    public int getStudentsNumOnCard() {
        return studentsNumOnCard;
    }
}
