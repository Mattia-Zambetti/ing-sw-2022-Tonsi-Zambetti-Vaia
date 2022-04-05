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
    void BasicTowersMethodTest () throws NegativeNumberOfTowerException {

        assertEquals(dashboard.getTowersNum(),INITIAL_NUM_OF_TOWER);
        assertEquals(dashboard.getTowerColor(), TowerColor.BLACK);
        dashboard.removeTowers(1);
        assertEquals(dashboard.getTowersNum(),INITIAL_NUM_OF_TOWER-1);

    }

    @Test
    void InsertAndRemoveFromEntranceTest() throws MaxNumberException {

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

    }

    //TODO test che ho spostato effettivamente in DR copiando parte del test precedente



}
