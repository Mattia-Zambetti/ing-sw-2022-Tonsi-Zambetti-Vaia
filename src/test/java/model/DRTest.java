package model;

import model.exception.MaxNumberException;
import model.exception.StudentIDAlreadyExistingException;
import model.exception.WrongColorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DRTest {
    DiningRoom diningRoomTest, diningRoomTest2;

    //Before each test 2 dining rooms are created, the second one is a copy of the first one
    @BeforeEach
    void init(){
        diningRoomTest = new DiningRoom(Color.RED);
        diningRoomTest2 = new DiningRoom(diningRoomTest);
    }

    //This test checks if the insertStudent method works
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

    //This test checks that the MaxNumberException is thrown when the dining room is full and another student is added
    @Test
    void MaxNumberExceptionDRTest() throws WrongColorException, MaxNumberException, StudentIDAlreadyExistingException {

        assertEquals( diningRoomTest.getStudentsNumber(), 0);

        for(int i = 0; i<DiningRoom.getDiningRoomDim(); i++){
            diningRoomTest.insertStudent(new Student(i+1,Color.RED));
        }
        assertThrows(MaxNumberException.class,()->diningRoomTest.insertStudent(new Student(1,Color.RED)));

    }

    //Checks that NullPointerException is thrown when a null Student is added
    @Test
    void NullPointerExceptionDRTest () {
        Student s = null;

        assertThrows(NullPointerException.class, ()->diningRoomTest.insertStudent(s));
    }

    //Checks that a WrongColorException is thrown when trying to add a student with a color in a dining room with another color
    @Test
    void WrongColorExceptionDRTest () {

        assertThrows(WrongColorException.class, ()->diningRoomTest.insertStudent(new Student(1, Color.GREEN)));
        assertThrows(WrongColorException.class, ()->diningRoomTest.insertStudent(new Student(1, Color.PINK)));
        assertThrows(WrongColorException.class, ()->diningRoomTest.insertStudent(new Student(1, Color.YELLOW)));
        assertThrows(WrongColorException.class, ()->diningRoomTest.insertStudent(new Student(1, Color.BLUE)));
    }

    //Checks that two identical students can't be added in the same dining room (used to avoid two insertions when we want only one)
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
