//Zambo
package model;

import model.exception.MaxNumberException;
import model.exception.NoMasterException;

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

    public void insertStudent ( Student newStudent ) throws MaxNumberException{
        if ( students.size() < DINING_ROOM_DIM )
            students.add( newStudent );
        throw new MaxNumberException(roomColor.toString()+" Dining Room Full");
    }


}
