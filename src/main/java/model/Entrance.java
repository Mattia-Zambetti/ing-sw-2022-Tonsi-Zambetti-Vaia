//Zambo, Tonsi
package model;

import model.exception.*;

import java.util.HashSet;
import java.util.Set;

public class Entrance {
    private final static int MAXSTUDENTS =9;
    private Set<Student> students;

    public Entrance(){
        students=new HashSet<>();
    }

    public static int getMAXSTUDENTS() {
        return MAXSTUDENTS;
    }

    public void insertStudents(Set<Student> studentsFromCloud) throws MaxNumberException {
        int initialSize=students.size();
        for (Student s: studentsFromCloud){
            if(students.size()== MAXSTUDENTS)
                throw new MaxNumberException("You can't add students to the entrance. " +
                        "Students inserted in this round:"+ (students.size()-initialSize) );
            else
                students.add(s);
        }
    }

    public void insertStudent(Student studentToBeAdded) throws MaxNumberException {
        if( this.students.size() == MAXSTUDENTS)
            throw new MaxNumberException("Entrance full, you can't add students to the entrance.");
        else
            this.students.add(studentToBeAdded);
    }

    //This method verify that an object is exactly the same, and not a clone, pay attention.
    //It's perfect for us because we can pass students, they are immutable object, but
    //if we use a clone of the Student, it doesn't work(watch
    // the insertStudentsWithoutException() test)
    public void removeStudent(Student student) throws InexistentStudentException{
        if ( students.contains(student) )
            students.remove(student);
        else
            throw new InexistentStudentException("Chosen student is not available");
    }

    public Set<Student> getStudents(){
        return new HashSet<>(students);
    }

    /*private boolean containsStudent( Student student ) {
        for ( Student s : students ) {
            if ( s.equals(student) )
                return true;
        }
        return false;
    }*/
}

