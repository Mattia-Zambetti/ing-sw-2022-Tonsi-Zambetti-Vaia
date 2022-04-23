package view.choice;

import model.Student;

import java.util.HashSet;
import java.util.Set;

public class JesterChoice extends FigureCardWithStudentsChoice {

    private Set<Student> studentsFromEntrance;

    public Set<Student> getStudentsFromEntrance() {
        return new HashSet<>(studentsFromEntrance);
    }

    public void setStudentsFromEntrance(Set<Student> studentsFromEntrance) {
        this.studentsFromEntrance = new HashSet<>(studentsFromEntrance);
    }
}

