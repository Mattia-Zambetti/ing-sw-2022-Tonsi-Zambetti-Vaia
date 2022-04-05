package model;

import junit.framework.TestCase;
import model.exception.MaxNumberException;
import model.exception.WrongColorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class DRTest extends TestCase {
    DiningRoom diningRoomTest;

    @BeforeEach
    void init(){
        diningRoomTest = new DiningRoom(Color.RED);
    }

    @Test
    void noExceptionDRTest() throws MaxNumberException, WrongColorException {

        assertEquals( diningRoomTest.getStudentsNumber(), 0);

        diningRoomTest.insertStudent(new Student(1,Color.RED));
        assertEquals( diningRoomTest.getStudentsNumber(), 1);

        diningRoomTest.insertStudent(new Student(2,Color.RED));
        assertEquals( diningRoomTest.getStudentsNumber(), 2);
        diningRoomTest.insertStudent(new Student(3,Color.RED));
        assertEquals( diningRoomTest.getStudentsNumber(), 3);
        diningRoomTest.insertStudent(new Student(4,Color.RED));
        assertEquals( diningRoomTest.getStudentsNumber(), 4);

    }

    @Test
    void MaxNumberExceptionDRTest() throws WrongColorException, MaxNumberException {

        assertEquals( diningRoomTest.getStudentsNumber(), 0);

        for(int i = 0; i<DiningRoom.getDiningRoomDim(); i++){
            diningRoomTest.insertStudent(new Student(1,Color.RED));
        }
        Exception eTest=assertThrows(MaxNumberException.class,()->diningRoomTest.insertStudent(new Student(1,Color.RED)));

    }


}
