package model;


import model.exception.AlreadyFilledCloudException;
import model.exception.MaxNumberException;
import model.exception.WrongCloudNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {

    Cloud cloudTest;


    @BeforeEach
    void init() throws MaxNumberException, AlreadyFilledCloudException {
        Set<Student> students=new HashSet<>();

        students.add(new Student(1,Color.YELLOW));
        students.add(new Student(2,Color.RED));
        students.add(new Student(3,Color.YELLOW));

        Cloud.setStudentsNumOnCloud(3);
        cloudTest=new Cloud(1);
        cloudTest.refillCloud(students);

    }

    /**It tests if the students on the cloud has taken correctly; after this operation the students list on the cloud is empty */
    @Test
    void checkIsEmptyAfterTakeStudents() throws WrongCloudNumberException {
        cloudTest.takeStudents();

        assertThrows(WrongCloudNumberException.class,()->cloudTest.takeStudents());
        assertEquals(cloudTest, new Cloud(1));
    }

    /** It tests if, by inserting a students empty set, the method work correctly
     * (the student list has to be empty at the end) */
    @Test
    void takeAndRefillCloudEmptySet(){
        Set<Student> tmp=new HashSet<>();
        try {
            cloudTest.takeStudents();
            cloudTest.refillCloud(tmp);
        }catch (WrongCloudNumberException | MaxNumberException e){
            System.out.println(e.getMessage());
        } catch (AlreadyFilledCloudException e) {
            e.printStackTrace();
        } finally{
            assertEquals(cloudTest, new Cloud(1));
        }
    }

    /** It tests if, by inserting a set of 3 students, the method work correctly(the student list has to be of the same 3 students
     * at the end) */
    @Test
    void takeAndRefillCloudRealSet() throws WrongCloudNumberException {
        Set<Student> tmp=new HashSet<>();

        tmp.add(new Student(1,Color.YELLOW));
        tmp.add(new Student(2,Color.RED));
        tmp.add(new Student(3,Color.BLUE));
        try {
            cloudTest.takeStudents();
            cloudTest.refillCloud(tmp);
            cloudTest.setCloudNotChosen();

        } catch (MaxNumberException|WrongCloudNumberException e) {
            System.out.println(e.getMessage());
        } catch (AlreadyFilledCloudException e) {
            e.printStackTrace();
        }


        assertTrue(cloudTest.takeStudents().containsAll(tmp));
    }

    /** It tests if, by inserting a set of 3 students with a student null, the method work correctly( it throws MaxNumberException and the
     * students set is empty at the end)*/
    @Test
    void refillCloudWithStudentNull(){
        Set<Student> tmp=new HashSet<>();

        tmp.add(new Student(1,Color.YELLOW));
        tmp.add(new Student(2,Color.RED));
        tmp.add(null);
        try {
            cloudTest.takeStudents();
            cloudTest.refillCloud(tmp);

        } catch (MaxNumberException | AlreadyFilledCloudException | WrongCloudNumberException e) {
            System.out.println(e.getMessage());
        }finally {
            assertEquals(cloudTest, new Cloud(1));
        }
    }

    /** It tests if the cloud is correctly copied with the cloud copy constructor*/
    @Test
    void cloudCopyTest() throws WrongCloudNumberException {
        Cloud copyTest=new Cloud(cloudTest);
        assertEquals(copyTest,cloudTest);
        assertEquals(copyTest.getStudentsOnCloud(), cloudTest.getStudentsOnCloud());
        cloudTest.takeStudents();
        assertNotEquals(cloudTest,copyTest);
    }





}
