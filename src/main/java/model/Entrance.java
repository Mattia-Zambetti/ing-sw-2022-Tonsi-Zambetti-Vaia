//Zambo, Tonsi
package model;

import model.exception.*;

import java.util.*;

public class Entrance {
    private final static int MAXSTUDENTS =9;
    private Set<Student> students;

    public Entrance(){
        students=new HashSet<>();
    }

    public Entrance(Entrance entrance){
        this.students=new HashSet<>(entrance.getStudents());
    }

    public static int getMAXSTUDENTS() {
        return MAXSTUDENTS;
    }

    public void insertStudents(Set<Student> studentsToBeAdded) throws MaxNumberException, StudentIDAlreadyExistingException {
        int initialSize=students.size();
        for (Student s: studentsToBeAdded){
            if (students.size()== MAXSTUDENTS)
                throw new MaxNumberException("You can't add students to the entrance. " +
                        "Students inserted in this round:"+ (students.size()-initialSize) );
            if ( this.CheckForIDPresence(s.getID())  )
                throw new StudentIDAlreadyExistingException("A Student with the same ID is already present."+
                        "\nThe ID is: "+s.getID());
            students.add(s);
        }
    }

    public void insertStudent(Student studentToBeAdded) throws MaxNumberException, StudentIDAlreadyExistingException {
        if( this.students.size() == MAXSTUDENTS)
            throw new MaxNumberException("Entrance full, you can't add students to the entrance.");
        if ( this.CheckForIDPresence(studentToBeAdded.getID())  )
            throw new StudentIDAlreadyExistingException("A Student with the same ID is already present."+
                    "\nThe ID is: "+studentToBeAdded.getID());
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
        return new HashSet<Student>(students);
    }

    private boolean CheckForIDPresence( int ID ) {
        for ( Student s: this.students ) {
            if ( s.getID() == ID )
                return true;
        }
        return false;
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

