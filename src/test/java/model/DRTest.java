package model;

import junit.framework.TestCase;
import model.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class DRTest extends TestCase {
    DiningRoom diningRoomTest, diningRoomTest2;

    @BeforeEach
    void init(){
        diningRoomTest = new DiningRoom(Color.RED);
        diningRoomTest2 = new DiningRoom(diningRoomTest);
    }

    @Test
    void noExceptionDRTest() throws MaxNumberException, WrongColorException, StudentIDAlreadyExistingException {

        assertEquals( diningRoomTest.getStudentsNumber(), 0);

        diningRoomTest.insertStudent(new Student(1,Color.RED));
        assertEquals( diningRoomTest.getStudentsNumber(), 1);

        diningRoomTest.insertStudent(new Student(2,Color.RED));
        assertEquals( diningRoomTest.getStudentsNumber(), 2);
        diningRoomTest.insertStudent(new Student(3,Color.RED));
        assertEquals( diningRoomTest.getStudentsNumber(), 3);
        diningRoomTest.insertStudent(new Student(4,Color.RED));
        assertEquals( diningRoomTest.getStudentsNumber(), 4);
        diningRoomTest.insertStudent(new Student(5,Color.RED));
        assertEquals( diningRoomTest.getStudentsNumber(), 5);

        assertEquals( DiningRoom.getDiningRoomDim(), 10);


    }

    @Test
    void MaxNumberExceptionDRTest() throws WrongColorException, MaxNumberException, StudentIDAlreadyExistingException {

        assertEquals( diningRoomTest.getStudentsNumber(), 0);

        for(int i = 0; i<DiningRoom.getDiningRoomDim(); i++){
            diningRoomTest.insertStudent(new Student(i+1,Color.RED));
        }
        assertThrows(MaxNumberException.class,()->diningRoomTest.insertStudent(new Student(1,Color.RED)));

    }

    @Test
    void NullPointerExceptionDRTest () {
        Student s = null;

        assertThrows(NullPointerException.class, ()->diningRoomTest.insertStudent(s));
    }

    @Test
    void WrongColorExceptionDRTest () {

        assertThrows(WrongColorException.class, ()->diningRoomTest.insertStudent(new Student(1, Color.GREEN)));
        assertThrows(WrongColorException.class, ()->diningRoomTest.insertStudent(new Student(1, Color.PINK)));
        assertThrows(WrongColorException.class, ()->diningRoomTest.insertStudent(new Student(1, Color.YELLOW)));
        assertThrows(WrongColorException.class, ()->diningRoomTest.insertStudent(new Student(1, Color.BLUE)));
    }

    @Test
    void StudentIDAlreadyExistingExceptionDRTest () throws MaxNumberException, StudentIDAlreadyExistingException, WrongColorException {

        diningRoomTest.insertStudent(new Student(1,Color.RED));
        diningRoomTest.insertStudent(new Student(2,Color.RED));
        diningRoomTest.insertStudent(new Student(15,Color.RED));

        assertThrows(StudentIDAlreadyExistingException.class, ()->diningRoomTest.insertStudent(new Student(1, Color.RED)));
        assertThrows(StudentIDAlreadyExistingException.class, ()->diningRoomTest.insertStudent(new Student(2, Color.RED)));

        diningRoomTest.insertStudent(new Student(11,Color.RED));

        assertThrows(StudentIDAlreadyExistingException.class, ()->diningRoomTest.insertStudent(new Student(15, Color.RED)));

    }

}
