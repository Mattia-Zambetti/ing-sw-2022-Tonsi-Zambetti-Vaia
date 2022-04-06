//Zambo, Tonsi
package model;

import model.exception.*;

import java.util.*;

public class Entrance {
    private final static int MAXSTUDENTS =9;
    private Set<Student> students; //TODO set instead of List

    public Entrance(){
        students=new HashSet<>();
    }

    public Entrance(Entrance entrance){
        this.students=new HashSet<>(entrance.getStudents());
    }

    public static int getMAXSTUDENTS() {
        return MAXSTUDENTS;
    }

    public void insertStudents(Set<Student> studentsToBeAdded) throws MaxNumberException {
        int initialSize=students.size();
        for (Student s: studentsToBeAdded){
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

    public ArrayList<Student> getStudents(){
        return new ArrayList<>(students);
    }

    //METODO CREATO CAUSA PROBLEMI CON HASHSET
    /*private boolean containsStudent( Student student ) {
        for ( Student s : students ) {
            if ( s.equals(student) )
                return true;
        }
        return false;
    }*/
}

