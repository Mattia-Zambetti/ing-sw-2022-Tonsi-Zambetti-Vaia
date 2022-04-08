package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.*;


import model.exception.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DashboardTest extends TestCase{

    private Dashboard dashboard;
    private Set<Student> students;
    private final int INITIAL_NUM_OF_TOWER = 8;
    boolean checkVariable=true;

    @BeforeEach
    void init() {

        dashboard = new Dashboard(INITIAL_NUM_OF_TOWER,TowerColor.BLACK,Wizard.WIZARD1);
        students=new HashSet<>();

        students.add(new Student(1,Color.YELLOW));
        students.add(new Student(2,Color.RED));
        students.add(new Student(3,Color.BLUE));
        students.add(new Student(4,Color.GREEN));
        students.add(new Student(5,Color.PINK));
    }

    @Test
    void BasicTowersMethodTest () throws NegativeNumberOfTowerException, MaxNumberOfTowerPassedException, TowerIDAlreadyExistingException {

        ArrayList<Tower> tmp, tmp2;

        assertEquals(dashboard.getTowersNum(),INITIAL_NUM_OF_TOWER);
        assertEquals(dashboard.getTowerColor(), TowerColor.BLACK);
        tmp = dashboard.removeTowers(2);
        assertEquals(dashboard.getTowersNum(),INITIAL_NUM_OF_TOWER-2);
        assertEquals(tmp.size(), 2);
        tmp2 = new ArrayList<>();
        tmp2.add(new Tower(TowerColor.BLACK,INITIAL_NUM_OF_TOWER-2));
        tmp2.add(new Tower(TowerColor.BLACK,INITIAL_NUM_OF_TOWER-1));
        dashboard.addTowers(tmp2);
        assertEquals( dashboard.getTowersNum(), INITIAL_NUM_OF_TOWER);

    }

    @Test
    void TowersMethodExceptionsTest () {

        ArrayList<Tower> tmp2;

        assertEquals(dashboard.getTowersNum(),INITIAL_NUM_OF_TOWER);
        assertEquals(dashboard.getTowerColor(), TowerColor.BLACK);
        tmp2 = new ArrayList<>();
        tmp2.add(new Tower(TowerColor.BLACK,1));
        tmp2.add(new Tower(TowerColor.BLACK,1));
        assertEquals( 2, tmp2.size() );
        assertThrows(MaxNumberOfTowerPassedException.class,()->dashboard.addTowers(tmp2));

        assertThrows(NegativeNumberOfTowerException.class,()->dashboard.removeTowers(10));


    }

    @Test
    void InsertAndRemoveFromEntranceTest() throws MaxNumberException, WrongColorException, StudentIDAlreadyExistingException, InexistentStudentException {

        dashboard.moveToEntrance(students);

        ArrayList<Student> studentsInTheEntrance = new ArrayList<>(dashboard.showEntrance());

        checkVariable=true;
        for ( Student s : students ) {
            if ( !studentsInTheEntrance.contains(s) )
                checkVariable=false;
        }
        assertTrue(checkVariable);

        HashSet<Student> oneStudent = new HashSet<>();
        oneStudent.add(new Student(8,Color.BLUE));
        dashboard.moveToEntrance(oneStudent);
        dashboard.moveToDR(new Student(8,Color.BLUE));
        dashboard.moveToDR(students);

        assertEquals( 0, dashboard.showEntrance().size() );

        assertEquals( 1, dashboard.getStudentsNumInDR(Color.RED));
        assertEquals( 1, dashboard.getStudentsNumInDR(Color.YELLOW));
        assertEquals( 2, dashboard.getStudentsNumInDR(Color.BLUE));

    }






}
