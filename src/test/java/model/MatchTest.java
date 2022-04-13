package model;

import junit.framework.TestCase;
import model.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchTest extends TestCase {
    Match match;
    static final int PLAYERSNUM = 2;
    ArrayList<Student>[] students;


    @BeforeEach void init() throws MaxNumberException, WrongDataplayerException, WrongColorException {
        match=new Match(PLAYERSNUM, false);
        assertEquals(PLAYERSNUM, match.getTotalPlayersNum());
        match.addPlayer("Vaia", "BLACK", "WIZARD1");
        assertEquals(1, match.getCurrentPlayersNum());
        this.students = new ArrayList[5];

        for(int i = 0; i < this.students.length; ++i) {
            this.students[i] = new ArrayList(0);
        }

        Student student = new Student(0, Color.RED);
        this.students[0].add(student);
        student = new Student(0, Color.YELLOW);
        this.students[4].add(student);
        student = new Student(1, Color.BLUE);
        this.students[2].add(student);
        student = new Student(2, Color.YELLOW);
        this.students[4].add(student);
    }

    @Test
    void showBag() throws NoMoreStudentsException {
        System.out.println(Bag.removeStudents(Bag.getSTUDENTSNUMCOLOR()*5));
    }


    @Test
    void maxPlayersThrowsException() throws MaxNumberException, WrongDataplayerException, WrongColorException {
        match.addPlayer("Island", "WHITE", "WIZARD2");
        assertThrows(MaxNumberException.class,()->match.addPlayer("Tonsi","GREEN", "WIZARD3"));
        assertEquals(PLAYERSNUM, match.getCurrentPlayersNum());
    }

    @Test
    void addPlayersMatchFourPlayersTest() throws MaxNumberException, WrongDataplayerException, WrongColorException {
        match=new Match(4, false);
        assertThrows(WrongColorException.class, ()->match.addPlayer("Vaia", "GREY", "WIZARD1"));
        match.addPlayer("Tonsi", "BLACK", "WIZARD2");
        match.addPlayer("Island", "BLACK", "WIZARD3");
        assertThrows(WrongDataplayerException.class,()->match.addPlayer("Zambo", "BLACK", "WIZARD1"));
        match.addPlayer("Vaia", "WHITE", "WIZARD1");
        match.addPlayer("Zambo", "WHITE", "WIZARD4");

        assertThrows(MaxNumberException.class,()->match.addPlayer("Cugola", "WHITE", "WIZARD5"));
    }

    @Test
    void sameDataExceptionAddPlayers() throws MaxNumberException, WrongDataplayerException, WrongColorException {
        assertThrows(WrongDataplayerException.class,()->match.addPlayer("Tonsi", "BLACK", "WIZARD2"));
        assertThrows(WrongDataplayerException.class,()->match.addPlayer("Vaia", "WHITE", "WIZARD2"));
        assertThrows(WrongDataplayerException.class,()->match.addPlayer("Tonsi", "WHITE", "WIZARD1"));
        match.addPlayer("Tonsi", "WHITE", "WIZARD2");
    }

    //It tests if it's possible to create a Match with the wrong number of players
    @Test
    void TooManyPlayersException() {
        Match tmp1;
        tmp1 = new Match(Match.getMAXPLAYERSNUM() + 1, false);
        tmp1=new Match(Match.getMINPLAYERSNUM() - 1, false);
    }


    //it tests the presence of a wrong choice (or not) into
    // the parameters of the moveStudentsFromCloudToEntrance's method
    @Test
    void moveStudentsFromCloudParamWrongAndCorrect() throws MaxNumberException, WrongDataplayerException, WrongColorException {
        Match tmp=new Match(PLAYERSNUM, false);
        tmp.addPlayer("Vaia", "BLACK", "WIZARD1");
        Bag.restoreBag();
        System.out.println(Bag.getStudentsNum());

        int chosenCloud=PLAYERSNUM+1;
        match.moveStudentsFromCloudToEntrance(chosenCloud);
        chosenCloud=0;
        match.moveStudentsFromCloudToEntrance(chosenCloud);

        chosenCloud=PLAYERSNUM;
        Set<Student> entranceTest=  new HashSet<>(match.showCurrentPlayerDashboard().showEntrance());
        match.moveStudentsFromCloudToEntrance(chosenCloud);
        assertNotSame(match.showCurrentPlayerDashboard().showEntrance(),entranceTest);
    }

    //It tests if the method refillClouds() correctly by refilling without students
    // (and with,that is a possible error) on the clouds
    @Test
    void refillCloudsTest() throws MaxNumberException, AlreadyFilledCloudException {
        System.out.println(match.toStringStudentsOnCloud());
        for(int i=0; i<PLAYERSNUM; i++){
            match.moveStudentsFromCloudToEntrance(i+1);
            System.out.println(match.toStringStudentsOnCloud());
        }
        assertEquals("",match.toStringStudentsOnCloud());
        match.refillClouds();
        System.out.println(match.toStringStudentsOnCloud());

        match.refillClouds();
        System.out.println(match.toStringStudentsOnCloud());

        //assertEquals("there's a cloud that is already filled",e.getMessage());
    }

    //It tests if the cards are returned and shown correctly(method used by the view)
    @Test
    void showCardsMethodDisplay(){
        for (Card c:match.showCards()) {
            System.out.println(c.toString());
        }
    }

    @Test
    void chooseCardMethod() throws CardNotFoundException {
        Card tmp=new Card(2,2,1);

        assertTrue(match.showCards().contains(tmp));
        match.chooseCard(tmp);

        assertEquals(tmp, match.showCurrentPlayerDashboard().getCurrentCard());
        System.out.println(match.showCurrentPlayerDashboard().getCurrentCard().toString());

        assertFalse(match.showCards().contains(match.showCurrentPlayerDashboard().getCurrentCard()));

        Card card=new Card(1,3,2);
        match.chooseCard(card);

    }

    //Start Vaia
    //Testing if the returned Island is the correct one under every circumstances
    @Test
    void PreviousAndNextIslandTest() throws NoIslandException, NegativeNumberOfTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers {
        int tmp = 0;
        Tower tower = new Tower(TowerColor.BLACK,0);
        ArrayList<Tower> tmpTowers = new ArrayList<Tower>();
        tmpTowers.add(tower);
        assertEquals(1, match.nextIsland(tmp));
        tmp= 11;
        assertEquals(0, match.nextIsland(tmp));
        assertThrows(NoIslandException.class, ()->match.nextIsland(12));
        tmp = 0;
        assertEquals(11, match.previousIsland(tmp));
        tmp= 11;
        assertEquals(10, match.previousIsland(tmp));
        assertThrows(NoIslandException.class, ()->match.previousIsland(12));
        match.setIslandsTowers(1, tmpTowers);
        match.mergeIsland(1);
        tmp = 0;
        assertEquals(2, match.nextIsland(tmp));
        assertEquals(0, match.previousIsland(2));
        assertThrows(NoIslandException.class, ()->match.previousIsland(-1));
        assertThrows(NoIslandException.class, ()->match.nextIsland(-2));
        match.setIslandsTowers(11, tmpTowers);
        match.mergeIsland(11);
        tmp = 10;
        assertEquals(0, match.nextIsland(tmp));
        tmp = 0;
        assertEquals(10, match.previousIsland(tmp));
    }

    //check if it calls the merging method when actually is needed (i could have spared some time by testiing just this method ans not the previous one)
    @Test
    void TestCheckNearbyIsland() throws InvalidNumberOfTowers, NoListOfSameColoredTowers, NegativeNumberOfTowerException, NoTowerException, NoIslandException {
        Tower tower = new Tower(TowerColor.BLACK,0);
        ArrayList<Tower> tmpTowers = new ArrayList<Tower>();
        tmpTowers.add(tower);
        match.setIslandsTowers(0,tmpTowers);
        match.setIslandsTowers(1,tmpTowers);
        match.checkNearbyIslands();
        assertEquals(2,match.nextIsland(0));
        assertEquals(11,match.previousIsland(0));
        match.setIslandsTowers(11,tmpTowers);
        match.checkNearbyIslands();
        assertEquals(10,match.previousIsland(0));
    }

    @Test
    void TestMergeIslands() throws NegativeNumberOfTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers {
        Tower tower = new Tower(TowerColor.BLACK,0);
        ArrayList<Tower> tmpTowers = new ArrayList<Tower>();
        tmpTowers.add(tower);
        match.setIslandsTowers(1,tmpTowers);
        match.setIslandsStudents(0,students);
        match.setIslandsStudents(1,students);
        match.mergeIsland(1);
        for (int i = 0; i < 5; i++)
            students[i].addAll(students[i]);
        for (int i = 0; i < 5; i++)
            assertEquals(students[i].size(),match.getStudentsOnIsland(0)[i].size());
        assertEquals(1, match.getTowersNumOnIsland(0));
        match.setIslandsTowers(5,tmpTowers);
        match.mergeIsland(5);
        for (int i = 0; i < 5; i++)
            assertEquals(students[i].size(),match.getStudentsOnIsland(0)[i].size());
        assertEquals(2, match.getTowersNumOnIsland(0));
    }

    @Test
    void TestMoveMotherNature() throws NoIslandException {
        assertEquals(true, match.checkMotherNatureOnIsland(0));
        match.moveMotherNature(3);
        assertEquals(true, match.checkMotherNatureOnIsland(3));
        assertEquals(false, match.checkMotherNatureOnIsland(0));
        match.moveMotherNature(9);
        assertEquals(true, match.checkMotherNatureOnIsland(0));
    }
}
