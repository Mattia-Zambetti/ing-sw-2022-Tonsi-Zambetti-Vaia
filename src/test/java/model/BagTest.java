package model;


import model.exception.MaxNumberException;
import model.exception.NoMoreStudentsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BagTest {
    Bag bag;


    @BeforeEach
    void init(){
        Bag.restoreBag();
    }

    /**it tests if there's the right number of the students in the bag, and if it throws
    *the MaxNumberException by removing more students than the total. At the end of the
    * operation, there won't be no more students in the bag*/
    @Test
    void removeMoreStudentsThanTheTotal(){
        assertEquals(Bag.getStudentsNum(),Bag.getINITIALSTUDENTS()*5);
        Exception eTest= assertThrows(NoMoreStudentsException.class, ()->Bag.removeStudents(Bag.getINITIALSTUDENTS()*5+1));
        assertEquals("Bag limit reached, now it's empty."+(Bag.getINITIALSTUDENTS()*5)+" students returned...", eTest.getMessage());
        assertEquals(0, Bag.getStudentsNum());
    }

    /**it tests if the method removeStudents and the method removeStudent return the right number of
    * students to the caller*/
    @Test
    void removeStudentsWithoutException() throws NoMoreStudentsException {
        assertThrows(MaxNumberException.class, Bag::createAllStudents);
        assertEquals(5, Bag.removeStudents(5).size());
        assertEquals(Bag.getINITIALSTUDENTS()*5-5, Bag.getStudentsNum());

        Student tmp=Bag.removeStudent();

        System.out.println(tmp);

        assertTrue(tmp.getColor().equals(new Student(1,Color.RED).getColor()) ||
                tmp.getColor().equals(Color.PINK) ||
                tmp.getColor().equals(Color.YELLOW) ||
                tmp.getColor().equals(Color.BLUE) ||
                tmp.getColor().equals(Color.GREEN));
    }

    /**It tests the excepition return in the removeStudent's method*/
    @Test
    void removeStudentWithException() throws NoMoreStudentsException {
        Bag.restoreBag();
        Bag.removeStudents(Bag.getINITIALSTUDENTS()*5);
        Exception eTest= assertThrows(NoMoreStudentsException.class, Bag::removeStudent);
        assertEquals("Bag limit reached, no more students in the bag...",
                eTest.getMessage());
    }




}
