//Zambo
package model;

import model.exception.MaxNumberException;
import model.exception.StudentIDAlreadyExistingException;
import model.exception.WrongColorException;
import graphicAssets.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class DiningRoom implements Serializable {
    private static final int DINING_ROOM_DIM = 10;
    private final Set<Student> students;
    private final Color roomColor;


    public DiningRoom ( Color colorOfRoom ) {
        this.roomColor = colorOfRoom;
        students = new HashSet<>(0);
    }

    public DiningRoom(DiningRoom dr){
        this.roomColor=dr.roomColor;
        this.students=new HashSet<>(dr.students);
    }

    public int getStudentsNumber () {
        return students.size();
    }

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

    public void removeStudent(Student student) throws StudentIDAlreadyExistingException{
        if(students.contains(student))
            students.remove(student);
        else throw new StudentIDAlreadyExistingException("error");
    }

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

    private boolean CheckForIDPresence( int ID ) {
        for ( Student s: this.students ) {
            if ( s.getID() == ID )
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return CLIgraphicsResources.ColorCLIgraphicsResources.getTextColor(roomColor) + roomColor.toString() + " DR:\t" + students.size() + " students" + CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR;
    }
}
