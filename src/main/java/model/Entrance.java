//Zambo, Tonsi
package model;

import model.exception.*;

import java.util.*;

public class Entrance {
    private final static int MAXSTUDENTS =9;
    private final Set<Student> students;

    public Entrance(){
        students=new HashSet<>();
    }

    public Entrance(Entrance entrance){
        this.students=new HashSet<>(entrance.getStudents());
    }

    public static int getMAXSTUDENTS() {
        return MAXSTUDENTS;
    }

    public void insertStudents(Set<Student> studentsToBeAdded) throws MaxNumberException, StudentIDAlreadyExistingException, NullPointerException {
        int initialSize=students.size();

        //TODO valutare sia qui che in DR se è il caso di lanciare eccezioni (null) quando viene passato un set nullo o studenti null
        if ( studentsToBeAdded == null )
            throw new NullPointerException("Tried to add null Set instead of a Set of students in your Entrance");
        for (Student s: studentsToBeAdded){
            if ( s == null )
                throw new NullPointerException("Tried to add null c in your Entrance");
            if (students.size()== MAXSTUDENTS)
                throw new MaxNumberException("You can't add more students to the entrance. " +
                        "Students inserted in this round:"+ (students.size()-initialSize) );
            if ( this.CheckForIDPresence(s.getID())  )
                throw new StudentIDAlreadyExistingException("A Student with the same ID is already present in the Entrance."+
                        "\nThe ID is: "+s.getID());
            students.add(s);
        }
    }

    public void insertStudent(Student studentToBeAdded) throws MaxNumberException, StudentIDAlreadyExistingException, NullPointerException {
        if ( studentToBeAdded == null )
            throw new NullPointerException("Tried to add null Student in your Entrance");
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
    public Student removeStudent(Student student) throws InexistentStudentException, NullPointerException{
        if ( student == null )
            throw new NullPointerException("Tried to remove null Student from your Entrance");
        if ( students.contains(student) ) {
            students.remove(student);
            return student;
        }
        else
            throw new InexistentStudentException("Chosen student is not available");
    }

    public Set<Student> getStudents(){
        return new HashSet<>(students);
    }

    private boolean CheckForIDPresence( int ID ) {
        for ( Student s: this.students ) {
            if ( s.getID() == ID )
                return true;
        }
        return false;
    }

}

