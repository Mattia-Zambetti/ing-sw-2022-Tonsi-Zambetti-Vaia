package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.*;


import model.exception.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DashboardTest extends TestCase{

    private Dashboard dashboard, dashboard2;
    private Set<Student> students;
    private final int INITIAL_NUM_OF_TOWER = 8;
    boolean checkVariable=true;

    @BeforeEach
    void init() throws CardNotFoundException {

        dashboard = new Dashboard(INITIAL_NUM_OF_TOWER,TowerColor.BLACK,Wizard.WIZARD1);
        dashboard2 = new Dashboard( dashboard );
        students=new HashSet<>();

        students.add(new Student(1,Color.YELLOW));
        students.add(new Student(2,Color.RED));
        students.add(new Student(3,Color.BLUE));
        students.add(new Student(4,Color.GREEN));
        students.add(new Student(5,Color.PINK));
    }

    @Test
    void ConstructorExceptionsTest () {
        Dashboard wrongDashboard2 = null;

        assertThrows( IllegalArgumentException.class, ()->new Dashboard(10, TowerColor.GREY, Wizard.WIZARD1));
        assertThrows( NullPointerException.class, ()->new Dashboard(wrongDashboard2));

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
    void AddTowersMethodExceptionsTest () throws TowerIDAlreadyExistingException, MaxNumberOfTowerPassedException, NegativeNumberOfTowerException {

        ArrayList<Tower> towerList;

        towerList = new ArrayList<>();
        towerList.add(new Tower(TowerColor.BLACK,9));
        towerList.add(new Tower(TowerColor.BLACK,10));
        assertEquals( 2, towerList.size() );

        ArrayList<Tower> finalTowerList = towerList;
        assertThrows(MaxNumberOfTowerPassedException.class,()->dashboard.addTowers(finalTowerList));

        ArrayList<Tower> removeTowerList = dashboard.removeTowers(6);
        assertEquals( 2, dashboard.getTowersNum() );

        towerList = new ArrayList<>();
        towerList.add(new Tower(TowerColor.BLACK, 1));
        ArrayList<Tower> finalTowerList1 = towerList;
        assertThrows(TowerIDAlreadyExistingException.class,()->dashboard.addTowers(finalTowerList1) );

        towerList = null;
        ArrayList<Tower> finalTowerList2 = towerList;
        assertThrows(NullPointerException.class,()->dashboard.addTowers(finalTowerList2) );
        towerList = new ArrayList<>();
        towerList.add(null);
        ArrayList<Tower> finalTowerList3 = towerList;
        assertThrows(NullPointerException.class,()->dashboard.addTowers(finalTowerList3) );

    }

    @Test
    void RemoveTowersMethodExceptionsTest () {

        assertThrows(NegativeNumberOfTowerException.class,()->dashboard.removeTowers(10));

    }

    @Test
    void InsertAndRemoveFromEntranceTest() throws MaxNumberException, WrongColorException, StudentIDAlreadyExistingException, InexistentStudentException {

        dashboard.moveToEntrance(students);

        ArrayList<Student> studentsInTheEntrance = new ArrayList<>(dashboard.showEntrance());

        checkVariable=true;
        for ( Student s : students ) {
            if (!studentsInTheEntrance.contains(s)) {
                checkVariable = false;
                break;
            }
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
