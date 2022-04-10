package model;

import junit.framework.TestCase;
import model.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntranceTest extends TestCase {
    Entrance entrance;
    Set<Student> studentsStartingSet;

    @BeforeEach
    void init(){
        entrance=new Entrance();
        studentsStartingSet =new HashSet<>();
        for(int i = 0; i<Entrance.getMAXSTUDENTS()-2; i++){
            studentsStartingSet.add(new Student(i+1, Color.PINK));
        }
    }

    //It's a test on the exception regarding the Students limit in the entrance
    @Test
    void insertStudentsWithMaxNumberException() throws MaxNumberException {
        Set<Student> studentsTestTmp = new HashSet<>();

        for (int i = 0; i < Entrance.getMAXSTUDENTS() + 1; i++) {
            studentsTestTmp.add(new Student(i + 1, Color.PINK));
        }
        Exception eTest = assertThrows(MaxNumberException.class, () -> entrance.insertStudents(studentsTestTmp));
        //assertEquals("You can't add students to the entrance. " +
        //        "Students inserted in this round:" + Entrance.getMAXSTUDENTS(), eTest.getMessage());

        Student studentAlreadyInserted = new Student(Entrance.getMAXSTUDENTS() + 1, Color.PINK);
        Exception eTest1 = assertThrows(MaxNumberException.class, () -> entrance.insertStudent(studentAlreadyInserted));

    }

    //It tests if the students have been inserted correctly without throw the
    //MaxNumberException. It tests also if it can remove a student by using a copy
    //or the original
    @Test
    void insertAndDeleteStudentsWithoutException() throws MaxNumberException, InexistentStudentException, StudentIDAlreadyExistingException {
        Student studentTmp= new Student( Entrance.getMAXSTUDENTS(), Color.RED);
        Student pinkStudent = new Student( 1, Color.PINK);

        Set<Student> studentsSet=new HashSet<Student>();
        studentsSet.add(studentTmp);

        entrance.insertStudents(studentsStartingSet);
        assertEquals(entrance.getStudents().size(),Entrance.getMAXSTUDENTS()-2);

        boolean AllStudentsArePresent = true;
        for ( Student s : studentsStartingSet) {
            if ( !entrance.getStudents().contains(s) ) {
                AllStudentsArePresent = false;
                break;
            }
        }
        assertTrue(AllStudentsArePresent);

        entrance.insertStudent(studentTmp);
        assertEquals(entrance.getStudents().size(),Entrance.getMAXSTUDENTS()-1);


        entrance.removeStudent(pinkStudent);
        assertEquals(entrance.getStudents().size(),Entrance.getMAXSTUDENTS()-2);

        boolean studentAbsent = true;

        for ( Student s : entrance.getStudents() ) {
            if ( s.equals(pinkStudent) ) {
                studentAbsent = false;
                break;
            }
        }

        assertTrue(studentAbsent);
    }

    //Test that checks if NullPointeerException is thrown when a null student is passed
    //FAILED: hashSet permits the null element
    /*@Test
    void NullStudentException_Method_insertStudent () throws MaxNumberException {
      Student s = null;

        Exception eTest = assertThrows( NullPointerException.class, ()->entrance.insertStudent(s) );


    }*/

    //Test that checks if IllegalArgumentException is thrown when a student with the same ID (and Hash) of another tudent already present is passed
    //FAILED: there is no problem adding a Student with the same ID of another to an HashSet
    //UPDATE: StudentIDAlreadyExistingException added
    /*@Test
    void IllegalArgumentException_Method_insertSingleStudent () throws MaxNumberException {
        Student s = new Student(11, Color.RED);
        Student s1 = new Student(11, Color.RED);
        Student s2 = new Student(s);

        entrance.insertStudent(s);
        Exception eTest1 = assertThrows( IllegalArgumentException.class, ()->entrance.insertStudent(s1) );
        Exception eTest2 = assertThrows( IllegalArgumentException.class, ()->entrance.insertStudent(s2) );
    }*/

    //Test that checks if StudentIDAlreadyExistingException is thrown
    @Test
    void StudentIDAlreadyExistingException_Method_insertSingleStudent () throws MaxNumberException, StudentIDAlreadyExistingException {
        Student s = new Student(11, Color.RED);
        Student s1 = new Student(11, Color.RED);
        Student s2 = new Student(s);

        entrance.insertStudent(s);
        Exception eTest1 = assertThrows( StudentIDAlreadyExistingException.class, ()->entrance.insertStudent(s1) );
        Exception eTest2 = assertThrows( StudentIDAlreadyExistingException.class, ()->entrance.insertStudent(s2) );
    }

    //It tests if it's possible to insert the same student's set 2 times
    @Test
    void StudentIDAlreadyExistingException_Method_insertMultipleStudent () throws MaxNumberException, StudentIDAlreadyExistingException {
        Set<Student> setAlreadyInserted = new HashSet<>(studentsStartingSet);

        entrance.insertStudents(studentsStartingSet);

        Exception eTest1 = assertThrows( StudentIDAlreadyExistingException.class, ()->entrance.insertStudents(setAlreadyInserted) );
        assertEquals(studentsStartingSet.size(),entrance.getStudents().size());
    }

    @Test
    void removeInexistentStudentTest () throws MaxNumberException, StudentIDAlreadyExistingException, InexistentStudentException {
        entrance.insertStudents(studentsStartingSet);

        entrance.removeStudent( new Student(1, Color.PINK));
        assertThrows( InexistentStudentException.class, ()->entrance.removeStudent( new Student(Entrance.getMAXSTUDENTS(), Color.PINK) ) );
        assertThrows( InexistentStudentException.class, ()->entrance.removeStudent( new Student(1, Color.RED) ) );

    }

    @Test
    void NullPointerExceptionTest () {
        Set<Student> studentsToBeAddedSet = null;
        Student studentToBeAdded = null;


        assertThrows( NullPointerException.class, ()->entrance.insertStudents( studentsToBeAddedSet ) );
        assertThrows( NullPointerException.class, ()->entrance.insertStudent( studentToBeAdded ) );
        assertThrows( NullPointerException.class, ()->entrance.removeStudent( studentToBeAdded ) );


    }

    @Test
    void ModifyStudentsAlreadyAddedExternally () {


    }

}
