package model;

import junit.framework.TestCase;
import model.exception.MaxNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BagTest extends TestCase {
    Bag bag;


    @BeforeEach
    void init(){
        Bag.instance();
    }

    //it tests if there's the right number of the students in the bag, and if it throws
    // the MaxNumberException by removing more students than the total. At the end of the
    // operation, there won't be no more students in the bag
    @Test
    void removeMoreStudentsThanTheTotal(){
        Bag.restoreBag();
        assertEquals(Bag.getStudentsNum(),Bag.getSTUDENTSNUMCOLOR()*5);
        Exception eTest= assertThrows(MaxNumberException.class, ()->Bag.removeStudents(Bag.getSTUDENTSNUMCOLOR()*5+1));
        assertEquals("Bag limit reached, now it's empty."+(Bag.getSTUDENTSNUMCOLOR()*5)+" students returned...", eTest.getMessage());
        assertEquals(0, Bag.getStudentsNum());
    }

    //it tests if the method removeStudents and the method removeStudent return the right number of
    // students to the caller
    @Test
    void removeStudentsWithoutException() throws MaxNumberException {
        Bag.restoreBag();
        assertEquals(5, Bag.removeStudents(5).size());
        assertEquals(Bag.getSTUDENTSNUMCOLOR()*5-5, Bag.getStudentsNum());

        String tmp=Bag.removeStudent().toString();
        assertTrue(tmp.equals("YELLOW student") ||
                tmp.equals("RED student") ||
                tmp.equals("BLUE student") ||
                tmp.equals("PINK student") ||
                tmp.equals("GREEN student"));
    }

    //It tests the excepition return in the removeStudent's method
    @Test
    void removeStudentWithException() throws MaxNumberException {
        Bag.restoreBag();
        Bag.removeStudents(Bag.getSTUDENTSNUMCOLOR()*5);
        Exception eTest= assertThrows(MaxNumberException.class, ()->Bag.removeStudent());
        assertEquals("Bag limit reached, no more students in the bag...",
                eTest.getMessage());
    }




}
