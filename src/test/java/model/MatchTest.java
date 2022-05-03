package model;

import model.figureCards.*;
import graphicAssets.CLIgraphicsResources;
import model.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {
    Match match;
    static final int PLAYERSNUM = 2;
    ArrayList<Student>[] students;


    @BeforeEach void init() throws MaxNumberException, WrongDataplayerException, WrongColorException, NoMoreStudentsException {
        match=new NormalMatch(PLAYERSNUM);
        match.refillCloudsNoNotify();

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
    void maxPlayersThrowsException() throws MaxNumberException, WrongDataplayerException, WrongColorException {
        match.addPlayer("Island", "WHITE", "WIZARD2");
        assertThrows(MaxNumberException.class,()->match.addPlayer("Tonsi","GREEN", "WIZARD3"));
        assertEquals(PLAYERSNUM, match.getCurrentPlayersNum());
    }

    @Test
    void addPlayersMatchFourPlayersTest() throws MaxNumberException, WrongDataplayerException, WrongColorException {
        match=new NormalMatch(4);
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
        tmp1 = new NormalMatch(Match.getMAXPLAYERSNUM() + 1);
        tmp1=new NormalMatch(Match.getMINPLAYERSNUM() - 1);
    }


    //it tests the presence of a wrong choice (or not) into
    // the parameters of the moveStudentsFromCloudToEntrance's method
    @Test
    void moveStudentsFromCloudParamWrongAndCorrect() throws MaxNumberException, WrongDataplayerException, WrongColorException, WrongCloudNumberException, NoMoreStudentsException, NoMasterException {
        Match tmp=new NormalMatch(PLAYERSNUM);

        tmp.addPlayer("Vaia", "BLACK", "WIZARD1");
        tmp.addPlayer("Tonsi", "WHITE","WIZARD2");
        tmp.refillClouds();


        tmp.moveStudentFromEntranceToDR(tmp.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0));
        tmp.moveStudentFromEntranceToDR(tmp.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0));
        tmp.moveStudentFromEntranceToDR(tmp.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0));

        int chosenCloud=PLAYERSNUM+1;
        assertThrows(WrongCloudNumberException.class,()->tmp.moveStudentsFromCloudToEntrance(chosenCloud));
        int chosenCloud1=-1;
        assertThrows(WrongCloudNumberException.class,()->tmp.moveStudentsFromCloudToEntrance(chosenCloud1));

        Set<Student> entranceTest=  new HashSet<>(tmp.showCurrentPlayerDashboard().showEntrance());
        tmp.moveStudentsFromCloudToEntrance(PLAYERSNUM-1);
        assertNotSame(tmp.showCurrentPlayerDashboard().showEntrance(),entranceTest);
        assertThrows(WrongCloudNumberException.class,()->tmp.moveStudentsFromCloudToEntrance(PLAYERSNUM-1));
    }

    //It tests if the method refillClouds() correctly by refilling without students
    // (and with,that is a possible error) on the clouds
    @Test
    void refillCloudsTest() throws MaxNumberException, AlreadyFilledCloudException, NoMoreStudentsException, WrongCloudNumberException {
        System.out.println(match.toStringStudentsOnCloud());
        for(int i=0; i<PLAYERSNUM; i++){
            match.moveStudentsFromCloudToEntrance(i);
            System.out.println(match.toStringStudentsOnCloud());
        }
        assertEquals("\n==================================\n            CLOUD 0               \n\n==================================\n==================================\n            CLOUD 1               \n\n==================================",match.toStringStudentsOnCloud());
        match.refillClouds();
        String studentsAndCloudsTest=match.toStringStudentsOnCloud();

        //catcha exception
        match.refillClouds();

        assertEquals(studentsAndCloudsTest,match.toStringStudentsOnCloud());
    }

    //It tests if the cards are returned and shown correctly(method used by the view)
    @Test
    void showCardsMethodDisplay(){
        for (Card c:match.showCards()) {
            System.out.println(c.toString());
        }
    }

    @Test
    void chooseCardMethod() throws CardNotFoundException, NoMoreCardException {
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
    //Testing if the returned Island is the correct one under every circumstance
    @Test
    void PreviousAndNextIslandTest() throws NoIslandException, NegativeNumberOfTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers, FinishedGameIslandException {
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
        match.addIslandsTowers(1, tmpTowers);
        match.mergeIsland(1);
        tmp = 0;
        assertEquals(2, match.nextIsland(tmp));
        assertEquals(0, match.previousIsland(2));
        assertThrows(NoIslandException.class, ()->match.previousIsland(-1));
        assertThrows(NoIslandException.class, ()->match.nextIsland(-2));
        match.addIslandsTowers(11, tmpTowers);
        match.mergeIsland(11);
        tmp = 10;
        assertEquals(0, match.nextIsland(tmp));
        tmp = 0;
        assertEquals(10, match.previousIsland(tmp));
    }


    //check if it calls the merging method when actually is needed (i could have spared some time by testing just this method and not the previous one)
    @Test
    void TestCheckNearbyIsland() throws InvalidNumberOfTowers, NoListOfSameColoredTowers, NegativeNumberOfTowerException, NoTowerException, NoIslandException, FinishedGameIslandException {
        Tower tower = new Tower(TowerColor.BLACK,0);
        ArrayList<Tower> tmpTowers = new ArrayList<Tower>();
        tmpTowers.add(tower);
        match.addIslandsTowers(0,tmpTowers);
        match.addIslandsTowers(1,tmpTowers);
        match.checkNearbyIslands();
        assertEquals(2,match.nextIsland(0));
        assertEquals(11,match.previousIsland(0));
        match.addIslandsTowers(11,tmpTowers);
        match.checkNearbyIslands();
        assertEquals(10,match.previousIsland(0));
    }

    @Test
    void TestMergeIslands() throws NegativeNumberOfTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers, FinishedGameIslandException {
        Tower tower = new Tower(TowerColor.BLACK,0);
        ArrayList<Tower> tmpTowers = new ArrayList<Tower>();
        tmpTowers.add(tower);
        match.addIslandsTowers(1,tmpTowers);
        match.setIslandsStudents(0,students);
        match.setIslandsStudents(1,students);
        match.mergeIsland(1);
        for (int i = 0; i < 5; i++)
            students[i].addAll(students[i]);
        for (int i = 0; i < 5; i++)
            assertEquals(students[i].size(),match.getStudentsOnIsland(0)[i].size());
        assertEquals(1, match.getTowersNumOnIsland(0));
        match.addIslandsTowers(6,tmpTowers);
        match.mergeIsland(6);
        for (int i = 0; i < 5; i++)
            assertEquals(students[i].size(),match.getStudentsOnIsland(0)[i].size());
        assertEquals(2, match.getTowersNumOnIsland(0));
    }

    @Test
    void changeTowerColorOnIsland() throws SameInfluenceException, NoTowerException, CardNotFoundException, InvalidNumberOfTowers, NoListOfSameColoredTowers, NegativeNumberOfTowerException, NoIslandException, NoMoreBlockCardsException, MaxNumberException, NoMoreCardException, TowerIDAlreadyExistingException, MaxNumberOfTowerPassedException, FinishedGameIslandException {
        Dashboard dashboard0 = new Dashboard(6,TowerColor.GREY,Wizard.WIZARD1,"Rebecca",1);
        Dashboard dashboard1 = new Dashboard(6,TowerColor.BLACK,Wizard.WIZARD1,"Rebecco",2);
        Master masterR = new Master(Color.RED);
        Master masterB = new Master(Color.BLUE);
        Master masterY = new Master(Color.YELLOW);
        Master masterP = new Master(Color.PINK);
        Master masterG = new Master(Color.GREEN);
        match.initializeDashboardsForTesting(dashboard0);
        //match.initializeDashboardsForTesting(dashboard0);
        match.setDashboardMaster(0,masterB); // 1
        match.setDashboardMaster(1,masterG);
        match.setDashboardMaster(1,masterR); // 1
        match.setDashboardMaster(1,masterY); // 2
        match.setDashboardMaster(0,masterP);
        match.setIslandsStudents(0,students);
        assertEquals(dashboard0.getTowerColor(), match.checkDashboardWithMoreInfluence().getTowerColor());
        Tower tower = new Tower(TowerColor.BLACK,7);
        ArrayList<Tower> tmpTowers = new ArrayList<Tower>();
        tmpTowers.add(tower);
        match.changeTowerColorOnIsland();
        assertEquals(TowerColor.GREY,  match.getTowerColorFromIsland(0));
        match.addIslandsTowers(1,tmpTowers);
        match.removeTowersFromDashboard(0,1);
        match.chooseCard(new Card(5,5,2));
        Island.setCentaurEffect(false);
        match.moveMotherNature(1);
        //match.changeTowerColorOnIsland();
        assertEquals(TowerColor.BLACK,  match.getTowerColorFromIsland(1));
    }

    @Test
    void TestMoveMotherNature() throws NoIslandException, SameInfluenceException, NoMoreBlockCardsException, MaxNumberException, NoMoreCardException, CardNotFoundException, NegativeNumberOfTowerException, TowerIDAlreadyExistingException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException, FinishedGameIslandException {
        assertEquals(true, match.checkMotherNatureOnIsland(0));
        match.chooseCard(new Card(5,10,5));
        match.moveMotherNature(3);
        assertEquals(true, match.checkMotherNatureOnIsland(3));
        assertEquals(false, match.checkMotherNatureOnIsland(0));
        match.moveMotherNature(9);
        assertEquals(true, match.checkMotherNatureOnIsland(0));
    }

    //Test Zambo

    //This test checks that Entrance is filled correctly with initializeAllEntrance(..) and also that entrance.toString() works
    @Test
    void initializeAllEntranceTest() {
        match.initializeAllEntrance();
        if ( PLAYERSNUM == 2 || PLAYERSNUM == 4 )
            assertEquals(7, match.showCurrentPlayerDashboard().showEntrance().size());
        else
            assertEquals(9, match.showCurrentPlayerDashboard().showEntrance().size());

        System.out.println(match.showCurrentPlayerDashboard().toString());
    }
    //TODO test più giocatori per initializeAllEntrance()

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
    void moveStudentFromEntranceToDRTest () throws WrongColorException, NoMasterException {
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

    //This test checks that dashboard order is correctly set after that players have played cards - only 2 players
    @Test
    void setDashboardOrderTest2Player() throws MaxNumberException, WrongDataplayerException, WrongColorException, NoMoreCardException, CardNotFoundException {

        HashSet<Card> playerCards;

        match.addPlayer("Tonsi", "WHITE", "WIZARD2");

        //playerCards = (HashSet<Card>) match.showCurrentPlayerDashboard().showCards();
        match.chooseCard(new Card(3,2,3));
        assertTrue(match.setNextCurrDashboard());
        match.chooseCard(new Card(2,1,2));
        assertFalse(match.setNextCurrDashboard());

        match.setDashboardOrder();
        assertEquals(match.showCurrentPlayerDashboard().getPlayer().getNickname(), "Tonsi");
        assertTrue(match.setNextCurrDashboard());
        assertEquals(match.showCurrentPlayerDashboard().getPlayer().getNickname(), "Vaia");
        assertFalse(match.setNextCurrDashboard());
    }

    //This test checks that dashboard order is correctly set after that players have played cards - only 3 players
    @Test
    void setDashboardOrderTest3Player() throws MaxNumberException, WrongDataplayerException, WrongColorException, NoMoreCardException, CardNotFoundException {

        match=new NormalMatch(3);

        match.addPlayer("Vaia", "BLACK", "WIZARD1");
        match.addPlayer("Tonsi", "WHITE", "WIZARD2");
        match.addPlayer("SamboIsland", "GREY", "WIZARD3");

        match.chooseCard(new Card(3,2,3));
        assertTrue(match.setNextCurrDashboard());
        match.chooseCard(new Card(2,1,2));
        assertTrue(match.setNextCurrDashboard());
        match.chooseCard(new Card(1,1,1));
        assertFalse(match.setNextCurrDashboard());

        match.setDashboardOrder();
        assertEquals(match.showCurrentPlayerDashboard().getPlayer().getNickname(), "SamboIsland");
        assertTrue(match.setNextCurrDashboard());
        assertEquals(match.showCurrentPlayerDashboard().getPlayer().getNickname(), "Tonsi");
        assertTrue(match.setNextCurrDashboard());
        assertEquals(match.showCurrentPlayerDashboard().getPlayer().getNickname(), "Vaia");
        assertFalse(match.setNextCurrDashboard());
    }

    //This test checks that dashboard order is correctly set after that players have played cards - only 4 players
    @Test
    void setDashboardOrderTest4Player() throws MaxNumberException, WrongDataplayerException, WrongColorException, NoMoreCardException, CardNotFoundException {

        match=new NormalMatch(4);

        match.addPlayer("Vaia", "BLACK", "WIZARD1");
        match.addPlayer("Tonsi", "BLACK", "WIZARD2");
        match.addPlayer("SamboIsland", "WHITE", "WIZARD3");
        match.addPlayer("Cugola", "WHITE", "WIZARD4");

        match.chooseCard(new Card(7,3,7));
        assertTrue(match.setNextCurrDashboard());
        match.chooseCard(new Card(2,1,2));
        assertTrue(match.setNextCurrDashboard());
        match.chooseCard(new Card(4,2,4));
        assertTrue(match.setNextCurrDashboard());
        match.chooseCard(new Card(1,1,1));
        assertFalse(match.setNextCurrDashboard());

        match.setDashboardOrder();
        assertEquals(match.showCurrentPlayerDashboard().getPlayer().getNickname(), "Cugola");
        assertTrue(match.setNextCurrDashboard());
        assertEquals(match.showCurrentPlayerDashboard().getPlayer().getNickname(), "Tonsi");
        assertTrue(match.setNextCurrDashboard());
        assertEquals(match.showCurrentPlayerDashboard().getPlayer().getNickname(), "SamboIsland");
        assertTrue(match.setNextCurrDashboard());
        assertEquals(match.showCurrentPlayerDashboard().getPlayer().getNickname(), "Vaia");
        assertFalse(match.setNextCurrDashboard());
    }

    //Test that checkAndMoveMasters(..) works, to check that it works between different dashboard it's necessary to manually watch the result of toSting()
    //because of the randomness of Bag.removeStudents used in initializeAllDashboard()
    @Test
    void checkAndMoveMastersTest() throws MaxNumberException, WrongDataplayerException, WrongColorException, NoMasterException {
        System.out.println(CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_RESET);
        //System.out.println(CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_BLACK_BACKGROUND);
        //System.out.println(CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR);
        match.addPlayer("Tonsi", "WHITE", "WIZARD2");

        match.initializeAllEntrance();

        List<Student> studentsInEntrance = match.showCurrentPlayerDashboard().showEntrance().stream().toList();
        Student studentToMove = studentsInEntrance.get(0);
        System.out.println(studentToMove.toString());
        match.moveStudentFromEntranceToDR(studentToMove);
        match.checkAndMoveMasters();
        assertTrue(match.showCurrentPlayerDashboard().haveMaster(studentToMove.getColor()));
        //System.out.println(match.showCurrentPlayerDashboard().toString());

        match.setNextCurrDashboard();

        for ( Student s : match.showCurrentPlayerDashboard().showEntrance() ) {
            match.moveStudentFromEntranceToDR(s);
        }
        match.checkAndMoveMasters();
        match.setNextCurrDashboard();
        System.out.println(/*"Player " + match.showCurrentPlayerDashboard().getPlayer().getNickname() + */"\n" + match.showCurrentPlayerDashboard().toString());
        match.setNextCurrDashboard();
        System.out.println(/*"Player " + match.showCurrentPlayerDashboard().getPlayer().getNickname() + */"\n" + match.showCurrentPlayerDashboard().toString());

        match.setNextCurrDashboard();
        for ( Student s : match.showCurrentPlayerDashboard().showEntrance() ) {
            match.moveStudentFromEntranceToDR(s);
        }
        match.checkAndMoveMasters();
        System.out.println("Player " + match.showCurrentPlayerDashboard().getPlayer().getNickname() + "\n" + match.showCurrentPlayerDashboard().toString());
        match.setNextCurrDashboard();
        System.out.println("Player " + match.showCurrentPlayerDashboard().getPlayer().getNickname() + "\n" + match.showCurrentPlayerDashboard().toString());

        System.out.println(CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_RESET);
    }
    //TODO test con più di due dashboard

    @Test
    void IslandToStringTest() throws NoMoreBlockCardsException, MaxNumberException, SameInfluenceException, NoIslandException, WrongDataplayerException, WrongColorException, NoMasterException, NoTowerException, NoMoreCardException, CardNotFoundException, NegativeNumberOfTowerException, TowerIDAlreadyExistingException, InvalidNumberOfTowers, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException, FinishedGameIslandException {

        match=new NormalMatch(PLAYERSNUM);

        match.addPlayer("Vaia", "WHITE", "WIZARD1");
        match.addPlayer("Tonsi", "BLACK", "WIZARD2");

        List<Student> studentsInEntrance = match.showCurrentPlayerDashboard().showEntrance().stream().toList();
        Student studentToMove = studentsInEntrance.get(0);
        System.out.println(studentToMove.toString());
        match.moveStudentFromEntranceToDR(studentToMove);
        match.checkAndMoveMasters();
        assertTrue(match.showCurrentPlayerDashboard().haveMaster(studentToMove.getColor()));

        match.chooseCard(new Card(3,2,3));

       /* for ( Island island : match.islands ) {
            match.moveMotherNature(1);
            if ( match.getStudentsOnIsland(island.getPosition())[studentToMove.getColor().ordinal()].size()>0 )
                assertEquals(TowerColor.WHITE, match.getTowerColorFromIsland(island.getPosition()));
        }*/
        for ( Island i : match.islands ) {
            System.out.println(i);
        }
    }

    @Test
    void ExpertMatchIslandToStringTest() throws NoMoreBlockCardsException, MaxNumberException, SameInfluenceException, NoIslandException, WrongDataplayerException, WrongColorException, NoMasterException, NoTowerException, NoMoreCardException, CardNotFoundException, NegativeNumberOfTowerException, TowerIDAlreadyExistingException, InvalidNumberOfTowers, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException, FinishedGameIslandException {

        match=new ExpertMatch(PLAYERSNUM);

        match.addPlayer("Vaia", "WHITE", "WIZARD1");
        match.addPlayer("Tonsi", "BLACK", "WIZARD2");

        ((ExpertMatch)match).setCentaurEffect(true);

        List<Student> studentsInEntrance = match.showCurrentPlayerDashboard().showEntrance().stream().toList();
        Student studentToMove = studentsInEntrance.get(0);
        System.out.println(studentToMove.toString());
        match.moveStudentFromEntranceToDR(studentToMove);
        match.checkAndMoveMasters();
        assertTrue(match.showCurrentPlayerDashboard().haveMaster(studentToMove.getColor()));

        match.chooseCard(new Card(3,2,3));

        for ( Island island : match.islands ) {
            match.moveMotherNature(1);
            if ( match.getStudentsOnIsland(island.getPosition())[studentToMove.getColor().ordinal()].size()>0 )
                assertEquals(TowerColor.WHITE, match.getTowerColorFromIsland(island.getPosition()));
        }
        for ( Island i : match.islands ) {
            System.out.println(i);
        }
    }

    @Test
    void matchToStringTest() throws MaxNumberException, WrongDataplayerException, WrongColorException, NoMoreStudentsException {
        match=new NormalMatch(PLAYERSNUM);
        match.addPlayer("Island", "BLACK", "WIZARD1");
        match.addPlayer("Tower", "WHITE", "WIZARD2");
        match.refillClouds();

        System.out.println(match);

    }

    //End Test Zambo

}
