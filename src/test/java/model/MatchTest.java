package model;

import junit.framework.TestCase;
import model.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchTest extends TestCase {
    Match match;
    static final int PLAYERSNUM = 2;
    ArrayList<Student>[] students;


    @BeforeEach void init() throws MaxNumberException, WrongDataplayerException, WrongColorException {
        match=new NormalMatch(PLAYERSNUM, false);
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
        for (int i=0; i<Bag.getSTUDENTSNUMCOLOR()*5;i++) {
            System.out.println(Bag.removeStudent());
        }
    }

    @Test
    void showAllTogetherBag() throws NoMoreStudentsException {
            Set<Student> students=Bag.removeStudents(Bag.getSTUDENTSNUMCOLOR());
            for(Student s: students){
                System.out.println(s);
            }
    }



    @Test
    void maxPlayersThrowsException() throws MaxNumberException, WrongDataplayerException, WrongColorException {
        match.addPlayer("Island", "WHITE", "WIZARD2");
        assertThrows(MaxNumberException.class,()->match.addPlayer("Tonsi","GREEN", "WIZARD3"));
        assertEquals(PLAYERSNUM, match.getCurrentPlayersNum());
    }

    @Test
    void addPlayersMatchFourPlayersTest() throws MaxNumberException, WrongDataplayerException, WrongColorException {
        match=new NormalMatch(4, false);
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
        tmp1 = new NormalMatch(Match.getMAXPLAYERSNUM() + 1, false);
        tmp1=new NormalMatch(Match.getMINPLAYERSNUM() - 1, false);
    }


    //it tests the presence of a wrong choice (or not) into
    // the parameters of the moveStudentsFromCloudToEntrance's method
    @Test
    void moveStudentsFromCloudParamWrongAndCorrect() throws MaxNumberException, WrongDataplayerException, WrongColorException {
        Match tmp=new NormalMatch(PLAYERSNUM, false);
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

    //Test Zambo

    //This test checks that masters are correctly created when a Match Object is created
    @Test
    void initialMasterTest () {

        for ( Color c: Color.values() ) {
            assertTrue ( match.getMasters().containsKey(c) );
            assertEquals( match.getMasters().get(c).getColor(), c);
        }
    }

    //this test checks that students are correctly moved from Entrance to DR for the currentPlayerDashboard
    @Test
    void moveStudentFromEntranceToDRTest () throws WrongColorException {
        int redStudents=0, blueStudents=0, greenStudents=0, yellowStudents=0, pinkStudents=0;

        match.initializeAllEntrance();
        if ( PLAYERSNUM == 2 || PLAYERSNUM == 4 )
            assertEquals(7, match.showCurrentPlayerDashboard().showEntrance().size());
        else
            assertEquals(9, match.showCurrentPlayerDashboard().showEntrance().size());

        for ( Student s: match.showCurrentPlayerDashboard().showEntrance()) {
            switch (s.getColor()) {
                case RED -> redStudents++;
                case BLUE -> blueStudents++;
                case GREEN -> greenStudents++;
                case YELLOW -> yellowStudents++;
                case PINK -> pinkStudents++;
            }
            System.out.println(s.toString());
        }
        for ( Student s: match.showCurrentPlayerDashboard().showEntrance()) {
            match.moveStudentFromEntranceToDR( s );
        }
        assertEquals(0, match.showCurrentPlayerDashboard().showEntrance().size());
        assertEquals(redStudents, match.showCurrentPlayerDashboard().getStudentsNumInDR(Color.RED));
        assertEquals(blueStudents, match.showCurrentPlayerDashboard().getStudentsNumInDR(Color.BLUE));
        assertEquals(greenStudents, match.showCurrentPlayerDashboard().getStudentsNumInDR(Color.GREEN));
        assertEquals(yellowStudents, match.showCurrentPlayerDashboard().getStudentsNumInDR(Color.YELLOW));
        assertEquals(pinkStudents, match.showCurrentPlayerDashboard().getStudentsNumInDR(Color.PINK));

    }

    //This test checks that moveStudentFromEntranceToIsland works
    @Test
    void moveStudentFromEntranceToIslandTest() throws NoIslandException {

        assertEquals( 12, match.getIslandPositions().size());

        match.initializeAllEntrance();
        if ( PLAYERSNUM == 2 || PLAYERSNUM == 4 )
            assertEquals(7, match.showCurrentPlayerDashboard().showEntrance().size());
        else
            assertEquals(9, match.showCurrentPlayerDashboard().showEntrance().size());

        //UTILIZZATO PER CONTARE GLI STUDENTI SU OGNI ISOLA
        ArrayList<Student>[][] studentsOnEachIsland = new ArrayList[12][5];
        int i=0;
        int totalStudent;
        for (ArrayList<Student>[] islandStudentLists : studentsOnEachIsland) {
            //System.out.println("Island "+i+" before adding students:");
            totalStudent = 0;
            islandStudentLists = match.getStudentsOnIsland(i);
            for ( ArrayList<Student> stud: islandStudentLists ) {
                totalStudent += stud.size();
                //System.out.println(stud.toString());
            }
            if ( i!=0 && i!=6 )
                assertEquals(1, totalStudent);
            else
                assertEquals(0, totalStudent);
            i++;
        }

        List<Student> studentsInEntrance = match.showCurrentPlayerDashboard().showEntrance().stream().toList();
        for ( int index: match.getIslandPositions() ) {
            if ( index%2==0 ) {
                Student s = studentsInEntrance.get(index/2);
                //System.out.println(s.toString());
                match.moveStudentFromEntranceToIsland( s , index );
            }
        }

        if ( PLAYERSNUM == 2 || PLAYERSNUM == 4 )
            assertEquals(1, match.showCurrentPlayerDashboard().showEntrance().size());
        else
            assertEquals(3, match.showCurrentPlayerDashboard().showEntrance().size());


        i=0;
        for (ArrayList<Student>[] islandStudentLists : studentsOnEachIsland) {
            //System.out.println("Island "+i+" after adding students:");
            totalStudent = 0;
            islandStudentLists = match.getStudentsOnIsland(i);
            for ( ArrayList<Student> stud: islandStudentLists ) {
                totalStudent += stud.size();
                //System.out.println(stud.toString());
            }
            if ( i%2 == 0) {
                if (i != 0 && i != 6)
                    assertEquals(2, totalStudent);
                else
                    assertEquals(1, totalStudent);
            }
            else
                assertEquals(1, totalStudent);
            i++;
        }

    }

    //Test that checks if exception is correctly thrown by moveStudentFromEntranceToIsland(...) method
    @Test
    void moveStudentFromEntranceToIslandExceptionTest() {

        match.initializeAllEntrance();
        List<Student> studentsInEntrance = match.showCurrentPlayerDashboard().showEntrance().stream().toList();

        assertThrows(NoIslandException.class, ()->match.moveStudentFromEntranceToIsland(studentsInEntrance.get(0), 12));

    }





    //End Test Zambo

}
