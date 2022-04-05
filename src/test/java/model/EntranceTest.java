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
    Set<Student> studentsTest;

    @BeforeEach
    void init(){
        entrance=new Entrance();
        studentsTest=new HashSet<>();
        for(int i = 0; i<Entrance.getMAXSTUDENTS()-2; i++){
            studentsTest.add(new Student(i+1, Color.PINK));
        }
    }

    //It's a test on the exception, regarding the Students limit in the entrance
    @Test
    void insertStudentsWithException(){
        Set<Student> studentsTestTmp=new HashSet<>();
        for(int i = 0; i<Entrance.getMAXSTUDENTS()+1; i++){
            studentsTestTmp.add(new Student(1,Color.PINK));
        }
        Exception eTest=assertThrows(MaxNumberException.class,()->entrance.insertStudents(studentsTestTmp));
        assertEquals("You can't add students to the entrance. " +
                "Students inserted in this round:"+ Entrance.getMAXSTUDENTS(),eTest.getMessage());
    }

    //It tests if the students have been inserted correctly without throw the
    //MaxNumberException. It tests also if it can remove a student by using a copy
    //or the original
    @Test
    void insertAndDeleteStudentsWithoutMaxNumberException() throws MaxNumberException, InexistentStudentException {
        Student studentTmp= new Student( Entrance.getMAXSTUDENTS(), Color.RED);
        Student pinkStudent1 = new Student( 1, Color.PINK);
        //Set<Student> studentsSet=new HashSet<Student>();
        //studentsSet.add(studentTmp);

        entrance.insertStudents(studentsTest);
        assertEquals(entrance.getStudents().size(),Entrance.getMAXSTUDENTS()-2);

        boolean AllStudentsArePresent = true;
        for ( Student s : studentsTest ) {
            if ( !entrance.getStudents().contains(s) ) {
                AllStudentsArePresent = false;
                break;
            }
        }
        assertTrue(AllStudentsArePresent);

        //entrance.insertStudents(studentsSet);
        //assertEquals(entrance.getStudents().size(),Entrance.getMAXSTUDENTS()-1);

        entrance.insertStudent(studentTmp);
        assertEquals(entrance.getStudents().size(),Entrance.getMAXSTUDENTS()-1);

        //assertThrows( InexistentStudentException.class,()->entrance.removeStudent(pinkStudent1) );
        //assertEquals(entrance.getStudents().size(),Entrance.getMAXSTUDENTS()-1);

        entrance.removeStudent(pinkStudent1);
        assertEquals(entrance.getStudents().size(),Entrance.getMAXSTUDENTS()-2);

        boolean studentAbsent = true;

        for ( Student s : entrance.getStudents() ) {
            if ( s.equals(pinkStudent1) ) {
                studentAbsent = false;
                break;
            }
        }

        assertTrue(studentAbsent);
    }
}
