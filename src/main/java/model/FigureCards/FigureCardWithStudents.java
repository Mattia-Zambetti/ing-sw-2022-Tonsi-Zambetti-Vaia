package model.FigureCards;

import model.Bag;
import model.ExpertMatchInterface;
import model.Student;
import model.exception.InexistentStudentException;
import model.exception.MaxNumberException;
import model.exception.NoMoreStudentsException;

import java.util.HashSet;
import java.util.Set;

public abstract class FigureCardWithStudents extends FigureCard{
    protected Set<Student> studentsOnCard;
    protected int studentsNumOnCard;
    protected int maxTakeStudentsNum;


    public FigureCardWithStudents() throws Exception {

    }

    protected void setStudentsOnCard(Set<Student> studentsToCard) throws Exception {
        if(studentsToCard.size()==studentsNumOnCard)
            this.studentsOnCard = new HashSet<>(studentsToCard);
        else throw new Exception("Wrong students number on a figure card...");
    }

    public boolean takeStudents(Set<Student> chosenStudents) throws InexistentStudentException, MaxNumberException, NoMoreStudentsException {
        if(maxTakeStudentsNum>=chosenStudents.size() && chosenStudents.size()!=0){
            for (Student s: chosenStudents) {
                if(studentsOnCard.contains(s)){
                    studentsOnCard.remove(s);
                }else throw new InexistentStudentException("This student isn't on the card...");
            }
        }else throw new MaxNumberException("Wrong students size, you can take only "+maxTakeStudentsNum+"students on this card and at least 1...");

        studentsOnCard.addAll(Bag.removeStudents(chosenStudents.size()));

        return true;
    }

    @Override
    public void playCard(ExpertMatchInterface expertMatchInterface) {
        expertMatchInterface.notifyStudentsOnFigureCard(studentsOnCard,this);
    }

}
