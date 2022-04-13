//Zambo
package model;

import model.exception.MaxNumberException;
import model.exception.StudentIDAlreadyExistingException;
import model.exception.WrongColorException;

import java.util.HashSet;
import java.util.Set;

public class DiningRoom {
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
}
