//Zambo, Tonsi
package model;

import model.exception.MaxNumberException;

import java.util.HashSet;
import java.util.Set;

public class Entrance {
    private final static int MAXSTUDENTI=9;
    private Set<Student> students;

    public Entrance(){
        students=new HashSet<>();
    }

    public void insertStudents(Set<Student> studentsFromCloud) throws MaxNumberException {
        for (Student s: studentsFromCloud){
            if(students.size()>MAXSTUDENTI)
                throw new MaxNumberException("numero massimo raggiunto");
            else
                students.add(s);
        }
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

    public Set<Student> getStudents(){
        return new HashSet<>(students);
    }
}
