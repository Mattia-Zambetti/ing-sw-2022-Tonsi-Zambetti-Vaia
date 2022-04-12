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

        dashboard = new Dashboard(INITIAL_NUM_OF_TOWER,TowerColor.BLACK,Wizard.WIZARD1, "Zambo",0);
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

        assertThrows( IllegalArgumentException.class, ()->new Dashboard(10, TowerColor.GREY, Wizard.WIZARD1, "Zambo",1));
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
    void InsertAndRemoveFromEntranceTest() throws MaxNumberException, StudentIDAlreadyExistingException, InexistentStudentException {

        dashboard.moveToEntrance(students);

        HashSet<Student> studentsInTheEntrance = new HashSet<>(dashboard.showEntrance());

        checkVariable=true;
        for ( Student s : students ) {
            if (!studentsInTheEntrance.contains(s)) {
                checkVariable = false;
                break;
            }
        }
        assertTrue(checkVariable);

        ArrayList<Student> studentsRemoved = new ArrayList<>();
        for ( Student s : studentsInTheEntrance ) {
            if ( s.getID()%2==0 ) {
                studentsRemoved.add(dashboard.removeStudentFromEntrance(s));
            }
        }
        studentsInTheEntrance = (HashSet<Student>) dashboard.showEntrance();

        assertTrue(studentsInTheEntrance.contains(new Student(1,Color.YELLOW)));
        assertTrue(studentsRemoved.contains(new Student(2,Color.RED)));
        assertTrue(studentsInTheEntrance.contains(new Student(3,Color.BLUE)));
        assertTrue(studentsRemoved.contains(new Student(4,Color.GREEN)));
        assertTrue(studentsInTheEntrance.contains(new Student(5,Color.PINK)));

    }

    @Test
    void MoveFromEntranceTODRTest () throws MaxNumberException, StudentIDAlreadyExistingException, WrongColorException, InexistentStudentException {

        dashboard.moveToEntrance(students);
        assertEquals(5, dashboard.showEntrance().size());

        for ( Student s : students ) {
            dashboard.moveToDR(dashboard.removeStudentFromEntrance(s));
        }
        assertEquals(0, dashboard.showEntrance().size());

        HashSet<Student> students2 = new HashSet();

        students2.add(new Student(11,Color.YELLOW));
        students2.add(new Student(12,Color.RED));
        students2.add(new Student(13,Color.BLUE));
        students2.add(new Student(14,Color.GREEN));
        students2.add(new Student(15,Color.PINK));
        dashboard.moveToEntrance(students2);
        assertEquals(5, dashboard.showEntrance().size());
        dashboard.moveToDR(students2);


        assertEquals(2,dashboard.getStudentsNumInDR(Color.RED));
        assertEquals(2,dashboard.getStudentsNumInDR(Color.BLUE));
        assertEquals(2,dashboard.getStudentsNumInDR(Color.YELLOW));
        assertEquals(2,dashboard.getStudentsNumInDR(Color.GREEN));
        assertEquals(2,dashboard.getStudentsNumInDR(Color.PINK));


    }

    @Test
    void CardTest () throws CardNotFoundException {
        HashSet<Card> cards;
        Card chosenCard = null;

        cards = (HashSet) dashboard.showCards();

        for ( Card c: cards) {
            System.out.println(c.toString());
        }

        int cardID = 1;

        for ( Card c: cards ) {
            if ( c.getId() == cardID ) {
                chosenCard = c;
                break;
            }
        }

        dashboard.playChosenCard(chosenCard);
        assertEquals(chosenCard, dashboard.getCurrentCard());
    }

    @Test
    void MasterTest () throws NoMasterException {
        boolean check = true;
        HashMap<Color, Master> mastersMap = new HashMap<>();
        for (Color c : Color.values()) {
            mastersMap.put(c, new Master(c));
        }

        for ( Color c: Color.values() ) {
            if ( dashboard.haveMaster(c) )
                check = false;
        }
        assertTrue(check);

        Color obtainedColor = Color.BLUE;
        dashboard.insertMaster(mastersMap.remove(obtainedColor));

        assertTrue(dashboard.haveMaster(obtainedColor));

        mastersMap.put(obtainedColor, dashboard.removeMaster(obtainedColor));
        assertFalse(dashboard.haveMaster(obtainedColor));

        for (Color c : Color.values()) {
            dashboard.insertMaster(new Master(c));
        }

        List<Master> masters = (List<Master>) dashboard.getMastersList();
        assertEquals(5,masters.size());
    }

    @Test
    void masterTestException () {
        assertThrows(NoMasterException.class, ()->dashboard.removeMaster(Color.BLUE));
    }





}
