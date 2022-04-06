package model;

import junit.framework.TestCase;
import model.exception.InexistentStudentException;
import model.exception.MaxNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

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
    void insertStudentsWithException() throws MaxNumberException {
        Set<Student> studentsTestTmp = new HashSet<>();

        for (int i = 0; i < Entrance.getMAXSTUDENTS() + 1; i++) {
            studentsTestTmp.add(new Student(i + 1, Color.PINK));
        }
        Exception eTest = assertThrows(MaxNumberException.class, () -> entrance.insertStudents(studentsTestTmp));
        assertEquals("You can't add students to the entrance. " +
                "Students inserted in this round:" + Entrance.getMAXSTUDENTS(), eTest.getMessage());

        Student studentAlreadyInserted = new Student(1, Color.PINK);
        Set<Student> setAlreadyInserted = new HashSet<>();
        setAlreadyInserted.add(studentAlreadyInserted);


    }

    //It tests if it's possible to insert the same student 2 times
    @Test
    void insertAStudent2Times() throws MaxNumberException {
        entrance.insertStudents(studentsStartingSet);

        Student studentAlreadyInserted = new Student(1, Color.PINK);
        Set<Student> setAlreadyInserted = new HashSet<>();
        setAlreadyInserted.add(studentAlreadyInserted);

        assertEquals(studentsStartingSet.size(),entrance.getStudents().size());
    }

    //It tests if the students have been inserted correctly without throw the
    //MaxNumberException. It tests also if it can remove a student by using a copy
    //or the original
    @Test
    void insertAndDeleteStudentsWithoutMaxNumberException() throws MaxNumberException, InexistentStudentException {
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
}
