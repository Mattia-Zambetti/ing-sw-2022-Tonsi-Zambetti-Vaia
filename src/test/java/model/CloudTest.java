package model;


import junit.framework.TestCase;
import model.exception.MaxNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class CloudTest extends TestCase {
    Cloud cloudTest;

    @BeforeEach
    void init(){
        Set<Student> students=new HashSet<>();

        students.add(new Student(1,Color.YELLOW));
        students.add(new Student(1,Color.RED));
        students.add(new Student(1,Color.YELLOW));

        cloudTest=new Cloud(students);
        Cloud.setStudentsNumOnCloud(students.size());
    }

    @Test
    void createToStringCloud(){
        assertTrue(cloudTest.toString().equals("YELLOW student\nYELLOW student\nRED student\n") ||
                    cloudTest.toString().equals("YELLOW student\nRED student\nYELLOW student\n") ||
                    cloudTest.toString().equals("RED student\nYELLOW student\nYELLOW student\n"));
    }

    @Test
    void checkIsEmptyAfterTakeStudents() throws MaxNumberException {
        Set<Student> tmp=new HashSet<>();
        cloudTest.takeStudents();
        assertEquals(tmp,cloudTest.takeStudents());
        assertEquals("",cloudTest.toString());
    }

    @Test
    void takeAndRefillCloudNullSet(){
        Set<Student> tmp=new HashSet<>();
        try {
            cloudTest.takeStudents();
            cloudTest.refillCloud(tmp);
        }catch (MaxNumberException e){
            System.out.println(e.getMessage());
        }finally{
            assertEquals("",cloudTest.toString());
        }
    }

    @Test
    void takeAndRefillCloudRealSet(){
        Set<Student> tmp=new HashSet<>();

        tmp.add(new Student(1,Color.YELLOW));
        tmp.add(new Student(2,Color.RED));
        tmp.add(new Student(3,Color.BLUE));
        try {
            cloudTest.takeStudents();
            cloudTest.refillCloud(tmp);

        } catch (MaxNumberException e) {
            System.out.println(e.getMessage());
        }


        assertTrue(cloudTest.toString().equals("BLUE student\nYELLOW student\nRED student\n") ||
                cloudTest.toString().equals("BLUE student\nRED student\nYELLOW student\n") ||
                cloudTest.toString().equals("RED student\nYELLOW student\nBLUE student\n") ||
                cloudTest.toString().equals("YELLOW student\nRED student\nBLUE student\n") ||
                cloudTest.toString().equals("RED student\nBLUE student\nYELLOW student\n") ||
                cloudTest.toString().equals("YELLOW student\nBLUE student\nRED student\n"));
    }

    @Test
    void refillCloudWithStudentNull(){
        Set<Student> tmp=new HashSet<>();

        tmp.add(new Student(1,Color.YELLOW));
        tmp.add(new Student(2,Color.RED));
        tmp.add(new Student(3,null));
        try {
            cloudTest.takeStudents();
            cloudTest.refillCloud(tmp);

        } catch (MaxNumberException e) {
            System.out.println(e.getMessage());
        }finally {
            assertEquals("",cloudTest.toString());
        }
    }




}
