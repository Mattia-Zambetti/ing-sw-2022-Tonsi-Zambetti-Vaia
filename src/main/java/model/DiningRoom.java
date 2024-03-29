//Zambo
package model;

import graphicAssets.CLIgraphicsResources;
import model.exception.MaxNumberException;
import model.exception.StudentIDAlreadyExistingException;
import model.exception.WrongColorException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiningRoom implements Serializable {
    private static final int DINING_ROOM_DIM = 10;
    private final Set<Student> students;
    private final Color roomColor;


    public DiningRoom ( Color colorOfRoom ) {
        this.roomColor = colorOfRoom;
        students = new HashSet<>(0);
    }

    /** It creates a copy of the dining room*/
    public DiningRoom(DiningRoom dr){
        this.roomColor=dr.roomColor;
        this.students=new HashSet<>(dr.students);
    }

    /** Return the number of students contained in this DR */
    public int getStudentsNumber () {
        return students.size();
    }

    /** Inserts the newStudent into this DR */
    public void insertStudent ( Student newStudent ) throws MaxNumberException, NullPointerException, WrongColorException, StudentIDAlreadyExistingException {
        if ( newStudent == null)
            throw new NullPointerException("Passed a null reference for newStudent in DR insertStudent()");
        if ( newStudent.getColor().ordinal()!=this.roomColor.ordinal() )
            throw new WrongColorException("Trying to insert newStudent in the wrong DR");
        if ( students.size() >= DINING_ROOM_DIM )
            throw new MaxNumberException(roomColor+" Dining Room Full");
        if ( this.CheckForIDPresence(newStudent.getID())  )
            throw new StudentIDAlreadyExistingException("A Student with the same ID is already present."+
                    "\nThe ID is: "+newStudent.getID());

        students.add( newStudent );
    }

    /** Removes the specified student from this DR */
    public void removeStudent(Student student) throws StudentIDAlreadyExistingException{
        if(students.contains(student))
            students.remove(student);
        else throw new StudentIDAlreadyExistingException("error");
    }

    /** Removes and returns a student from this DR */
    public Student removeStudentByColor() throws StudentIDAlreadyExistingException{
        List<Student> studentstmp= new ArrayList<>();
        studentstmp = students.stream().toList();
        if(studentstmp.size() > 0){
            students.remove(studentstmp.get(0));
            return studentstmp.get(0);
        }
        return null;
    }

    public Set<Student> getStudents() {
        return new HashSet<>(students);
    }

    public static int getDiningRoomDim() {
        return DINING_ROOM_DIM;
    }

    /** Checks if a student with the requested ID is contained in this DR */
    private boolean CheckForIDPresence( int ID ) {
        for ( Student s: this.students ) {
            if ( s.getID() == ID )
                return true;
        }
        return false;
    }

    /** toString of this DR used directly for the CLI version of the Game */
    @Override
    public String toString() {
        return CLIgraphicsResources.ColorCLIgraphicsResources.getTextColor(roomColor) + roomColor.toString() + " DR:\t" + students.size() + " students" + CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR;
    }
}
