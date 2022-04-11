package model;


import junit.framework.TestCase;
import model.exception.AlreadyFilledCloudException;
import model.exception.MaxNumberException;
import model.exception.WrongCloudNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

//Classe di test in quanto estende TestCase, i test possono essere eseguiti in blocco grazie al
// play verde in parte alla classe(schiacciando col destro sui test è possibile far partire tutti i test
// di tutte le classi), oppure far partire singolarmente ogni test sempre nello stesso modo
class CloudTest extends TestCase {
    //si possono definire variabili che poi verranno utilizzate nei test
    Cloud cloudTest;


    //Questo metodo viene eseguito prima di ogni test, usato per inizializzare solitamente gli oggetti
    @BeforeEach
    void init() throws MaxNumberException, AlreadyFilledCloudException {
        Set<Student> students=new HashSet<>();

        students.add(new Student(1,Color.YELLOW));
        students.add(new Student(2,Color.RED));
        students.add(new Student(3,Color.YELLOW));

        Cloud.setStudentsNumOnCloud(3);
        cloudTest=new Cloud();
        cloudTest.refillCloud(students);

    }

    //Da mettere sempre prima di un test
    @Test
    void createToStringCloud(){
        //Indica se il test è fallito(false) oppure è riuscito all'esecuzione
        assertTrue(cloudTest.toString().equals("YELLOW student\nYELLOW student\nRED student\n") ||
                    cloudTest.toString().equals("YELLOW student\nRED student\nYELLOW student\n") ||
                    cloudTest.toString().equals("RED student\nYELLOW student\nYELLOW student\n"));
    }

    @Test
    void checkIsEmptyAfterTakeStudents() throws WrongCloudNumberException {
        Set<Student> tmp=new HashSet<>();
        cloudTest.takeStudents();
        //test riuscito sse i due oggetti sono uguali
        assertEquals(tmp,cloudTest.takeStudents());
        assertEquals("",cloudTest.toString());
    }

    @Test
    void takeAndRefillCloudNullSet(){
        Set<Student> tmp=new HashSet<>();
        try {
            cloudTest.takeStudents();
            cloudTest.refillCloud(tmp);
        }catch (WrongCloudNumberException | MaxNumberException e){
            //probabilmente esiste una assert per le exception, consiglio di cercare
            System.out.println(e.getMessage());
        } catch (AlreadyFilledCloudException e) {
            e.printStackTrace();
        } finally{
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

        } catch (MaxNumberException|WrongCloudNumberException e) {
            System.out.println(e.getMessage());
        } catch (AlreadyFilledCloudException e) {
            e.printStackTrace();
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

        } catch (MaxNumberException | AlreadyFilledCloudException | WrongCloudNumberException e) {
            System.out.println(e.getMessage());
        }finally {
            assertEquals("",cloudTest.toString());
        }
    }





}
