//Zambo, Tonsi
package model;

import graphicAssets.CLIgraphicsResources;
import model.exception.*;

import java.io.Serializable;
import java.util.*;

public class Entrance implements Serializable {
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

    /** Inserts more than one student into the Entrance */
    public void insertStudents(Set<Student> studentsToBeAdded) throws MaxNumberException, StudentIDAlreadyExistingException, NullPointerException {
        int initialSize=students.size();

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

    /** Inserts more one student into the Entrance */
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

    /**This method verifies that an object is exactly the same, and not a clone, pay attention.
    *It's perfect for us because we can pass students, they are immutable object, but
    *if we use a clone of the Student, it doesn't work(watch
    * the insertStudentsWithoutException() test)*/
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

    /** Returns a copy of students contained in the Entrance */
    public Set<Student> getStudents(){
        return new HashSet<>(students);
    }

    /** Checks if a student with the requested ID is contained in the Entrance */
    private boolean CheckForIDPresence( int ID ) {
        for ( Student s: this.students ) {
            if ( s.getID() == ID )
                return true;
        }
        return false;
    }

    /** toString of the entrance used for the CLI version of the Game */
    @Override
    public String toString() {
        String outputString = "";
        int i = 1;
        outputString = outputString.concat("ENTRANCE  -  TOTAL = "+students.size()+" students\n");
        for ( Student s : students ) {
            outputString = outputString.concat(CLIgraphicsResources.ColorCLIgraphicsResources.getTextColor(s.getColor()) + " " + s.toString()+ CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR);
            if ( i%3==0 )
                outputString = outputString.concat("\n");
            else {
                outputString = switch (s.getColor()) {
                    case RED -> outputString.concat("      ");
                    case GREEN -> outputString.concat("    ");
                    case YELLOW -> outputString.concat("   ");
                    case BLUE -> outputString.concat("     ");
                    case PINK -> outputString.concat("     ");
                    default -> outputString;
                };
            }
            i++;
        }
        return outputString;
    }


}

