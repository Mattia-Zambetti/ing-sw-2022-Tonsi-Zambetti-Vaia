package view.choice;

import model.Student;

import java.util.HashSet;
import java.util.Set;

public abstract class FigureCardWithStudentsChoice extends FigureCardActionChoice{
    private Set<Student> chosenStudents;

    public Set<Student> getChosenStudents() {
        return new HashSet<>(chosenStudents);
    }

    public void setChosenStudents(Set<Student> chosenStudents) {
        this.chosenStudents = new HashSet<>(chosenStudents);
    }
}
