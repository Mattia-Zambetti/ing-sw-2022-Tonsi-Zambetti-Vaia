//Zambo
package model;

import model.exception.*;

import java.util.ArrayList;

public class DiningRoom {
    private static final int DINING_ROOM_DIM = 10;
    private ArrayList<Student> students;
    private final Color roomColor;


    public DiningRoom ( Color colorOfRoom ) {
        this.roomColor = colorOfRoom;
        students = new ArrayList<Student>(0);
    }

    public int getStudentsNumber () {
        return students.size();
    }

    public void insertStudent ( Student newStudent ) throws MaxNumberException, NullPointerException, WrongColorException{
        if ( newStudent == null)
            throw new NullPointerException("Passed a null reference for newStudent in DR insertStudent()");
        if ( newStudent.getColor().ordinal()!=this.roomColor.ordinal() )
            throw new WrongColorException("Trying to insert newStudent in the wrong DR");
        if ( students.size() >= DINING_ROOM_DIM )
            throw new MaxNumberException(roomColor.toString()+" Dining Room Full");

        students.add( newStudent );
    }

    public static int getDiningRoomDim() {
        return DINING_ROOM_DIM;
    }
}
