package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import model.exception.*;
import java.util.*;

public class DashboardTest extends TestCase{

    private Dashboard dashboard;
    private Set<Student> students;
    private final int INITIAL_NUM_OF_TOWER = 6;
    boolean checkVariable=true;

    @BeforeEach
    void init() {
        dashboard = new Dashboard(INITIAL_NUM_OF_TOWER,TowerColor.BLACK,Wizard.WIZARD1);

        students=new HashSet<>();

        students.add(new Student(1,Color.YELLOW));
        students.add(new Student(2,Color.RED));
        students.add(new Student(3,Color.YELLOW));
    }

    @Test
    void BasicTowersMethodTest () throws NegativeNumberOfTowerException, MaxNumberOfTowerPassedException {

        ArrayList<Tower> tmp, tmp2;

        assertEquals(dashboard.getTowersNum(),INITIAL_NUM_OF_TOWER);
        assertEquals(dashboard.getTowerColor(), TowerColor.BLACK);
        tmp = dashboard.removeTowers(2);
        assertEquals(dashboard.getTowersNum(),INITIAL_NUM_OF_TOWER-2);
        assertEquals(tmp.size(), 2);
        tmp2 = new ArrayList<>();
        tmp2.add(new Tower(TowerColor.BLACK,1));
        tmp2.add(new Tower(TowerColor.BLACK,1));
        dashboard.addTowers(tmp2);
        assertEquals( dashboard.getTowersNum(), INITIAL_NUM_OF_TOWER);

    }

    @Test
    void InsertAndRemoveFromEntranceTest() throws MaxNumberException, WrongColorException {

        dashboard.moveToEntrance(students);

        ArrayList<Student> studentsInTheEntrance = new ArrayList<>(dashboard.showEntrance());

        checkVariable=true;
        for ( Student s : students ) {
            if ( !studentsInTheEntrance.contains(s) )
                checkVariable=false;
        }
        assertTrue(checkVariable);

        HashSet<Student> oneStudent = new HashSet<>();
        oneStudent.add(new Student(4,Color.BLUE));
        dashboard.moveToEntrance(oneStudent);
        dashboard.moveToDR(new Student(4,Color.BLUE));
        dashboard.moveToDR(students);

        assertEquals( 0, dashboard.showEntrance().size() );

        assertEquals( 1, dashboard.getStudentsNumInDR(Color.RED));
        assertEquals( 2, dashboard.getStudentsNumInDR(Color.YELLOW));
        assertEquals( 1, dashboard.getStudentsNumInDR(Color.BLUE));

    }






}
