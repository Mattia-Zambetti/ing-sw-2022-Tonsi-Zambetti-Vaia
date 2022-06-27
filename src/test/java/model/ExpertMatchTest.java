package model;

import model.exception.*;
import model.figureCards.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExpertMatchTest implements Observer {
    Match expertMatch;
    FigureCard figureCardTest;
    ArrayList<Student>[] students;

    @BeforeEach
    void init(){
        expertMatch=new ExpertMatch(3);

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
        student = new Student(2, Color.BLUE);
        this.students[2].add(student);
    }

    //it shows the type of figure cards created, and if the students in the bag and on
    //the figure cards is correct
    @Test
    void randomCardCreationTest(){
        List<FigureCard> figureCards= ((ExpertMatch)expertMatch).showFigureCardsInGame();
        for (FigureCard f: figureCards) {
            System.out.println(f.toString());
        }
        System.out.println("There are " +Bag.getStudentsNum()+ " students in the bag. The " +
                "initial number was "+Bag.getSTUDENTSNUMCOLOR()*5);
    }

    @Test
    private void creationOfTheRightCard(FigureCard figureCardWanted) throws MaxNumberException, WrongDataplayerException, WrongColorException, NoMoreStudentsException {
        do {
            expertMatch = new ExpertMatch(2);

        }while(!(((ExpertMatch)expertMatch).showFigureCardsInGame()).contains(figureCardWanted));

        expertMatch.addPlayer("Tonsi", "BLACK", "WIZARD1",1);
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();

        expertMatch.addPlayer("Zambo", "WHITE", "WIZARD2",2);
    }



    //it tests if the centaur card is correctly played when it's called and if it throws the exception if it's called
    //two times in the same turn
    @Test
    void centaurTest() throws FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, MaxNumberException, WrongDataplayerException, WrongColorException, NoMoreStudentsException, NoMasterException {
        figureCardTest = new Centaur();

        creationOfTheRightCard(figureCardTest);
        assertEquals(3, new Centaur().getPRICECARD());
        assertEquals("Centaur card(price: 3 coins)", new Centaur().toString());

        assertEquals(3,figureCardTest.getPrice());

        ((ExpertMatch)expertMatch).playFigureCard(figureCardTest);

        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();


        assertThrows(FigureCardAlreadyPlayedInThisTurnException.class, ()->((ExpertMatch)expertMatch).playFigureCard(figureCardTest));

        assertEquals(3, figureCardTest.getPrice());
        assertEquals(0, expertMatch.showCurrentPlayerDashboard().getCoinsNumber());


        assertTrue(((ExpertMatchInterface) expertMatch).isCentaurEffect());

        expertMatch.setNextCurrDashboard();
        assertFalse(((ExpertMatchInterface) expertMatch).isCentaurEffect());

        assertThrows(InsufficientCoinException.class, ()->((ExpertMatch)expertMatch).playFigureCard(figureCardTest));
    }

    //it tests with the to string method if the figure card is sent correctly to the observer
    //(in this case this test class), and from the update method it tests every operation
    //and exception applied on the cards
    @Test
    void notifyFigureCardTest() throws Exception, WrongDataplayerException {
        creationOfTheRightCard(new Merchant());
        expertMatch.addObserver(this);

        //merchant call:
        ((ExpertMatch)expertMatch).playFigureCard(new Merchant());

        creationOfTheRightCard(new Jester());
        expertMatch.addObserver(this);

        //jester call:
        ((ExpertMatch)expertMatch).playFigureCard(new Jester());

        creationOfTheRightCard(new Princess());
        expertMatch.addObserver(this);

        //princess call:
        ((ExpertMatch)expertMatch).playFigureCard(new Princess());

        creationOfTheRightCard(new GrannyGrass());
        expertMatch.addObserver(this);

        //Granny grass call:
        ((ExpertMatch)expertMatch).playFigureCard(new GrannyGrass());

        creationOfTheRightCard(new MushroomCollector());
        expertMatch.addObserver(this);

        //Mushroom collector call:
        ((ExpertMatch)expertMatch).playFigureCard(new MushroomCollector());
    }

    @Test
    void testPostmanFigureCard() throws MaxNumberException, WrongDataplayerException, WrongColorException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, NoMoreBlockCardsException, NoIslandException, NoMoreTowerException, TowerIDAlreadyExistingException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException, SameInfluenceException, FinishedGameIslandException, NoMoreStudentsException, CardAlreadyPlayedException, FinishedGameEndTurnException, NoMasterException {
        creationOfTheRightCard(new Postman());
        assertEquals(1, new Postman().getPRICECARD());
        assertEquals("Postman card(price: 1 coin)", new Postman().toString());

        assertFalse(expertMatch.isPostManValue());
        ((ExpertMatch)expertMatch).playFigureCard(new Postman());
        assertTrue(expertMatch.isPostManValue());

        expertMatch.chooseCard(new Card(6,3,6));
        expertMatch.chooseCard(new Card(10,5,10));
        expertMatch.moveMotherNature(5);
        expertMatch.moveMotherNature(3);
        assertThrows(MaxNumberException.class, ()-> expertMatch.moveMotherNature(5));
    }

    //test to check if the influence calculation is right even with 4 players
    @Test
    void fourPlayersMatchTest() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException, FinishedGameIslandException, NoMoreBlockCardsException, NoMoreTowerException, TowerIDAlreadyExistingException, SameInfluenceException, InvalidNumberOfTowers, NoIslandException, NoTowerException, NoListOfSameColoredTowers, CardNotFoundException, MaxNumberOfTowerPassedException, CardAlreadyPlayedException, WrongCloudNumberException, NoMasterException {
        Match match = new ExpertMatch(4);

        match.addPlayer("1", "BLACK", "WIZARD1", 1);
        match.addPlayer("2", "BLACK", "WIZARD2", 2);
        match.addPlayer("3", "WHITE", "WIZARD3", 3);
        match.addPlayer("4", "WHITE", "WIZARD4", 4);

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

        Master masterR = new Master(Color.RED);
        Master masterB = new Master(Color.BLUE);
        Master masterY = new Master(Color.YELLOW);
        Master masterP = new Master(Color.PINK);
        Master masterG = new Master(Color.GREEN);

        match.setDashboardMaster(0,masterB); // 2
        match.setDashboardMaster(1,masterG);
        match.setDashboardMaster(1,masterR); // 1
        match.setDashboardMaster(2,masterY); // 2
        match.setDashboardMaster(3,masterP); // 2

        match.setIslandsStudents(1,students);

        match.chooseCard(new Card(5,5,5));
        match.chooseCard(new Card(5,5,4));
        match.chooseCard(new Card(5,5,3));
        match.chooseCard(new Card(5,5,2));
        //match.showCurrentPlayerDashboard().playChosenCard(new Card(5,5,5));
        match.moveMotherNature(1);
        Island.setCentaurEffect(false);

        assertThrows(NoTowerException.class , ()-> match.getTowerColorFromIsland(1));

        students[Color.PINK.ordinal()].add(new Student(78,Color.PINK));

        match.setIslandsStudents(2,students);

        match.moveMotherNature(1);

        assertEquals(TowerColor.WHITE , match.getTowerColorFromIsland(2));

        students[Color.BLUE.ordinal()].add(new Student(79,Color.BLUE));
        students[Color.PINK.ordinal()].add(new Student(76,Color.PINK));

        match.setIslandsStudents(3,students);

        match.moveMotherNature(1);

        assertEquals(TowerColor.WHITE , match.getTowerColorFromIsland(3));

        students[Color.BLUE.ordinal()].add(new Student(80,Color.BLUE));
        students[Color.BLUE.ordinal()].add(new Student(81,Color.BLUE));

        match.setIslandsStudents(4,students);

        match.moveMotherNature(1);

        assertEquals(TowerColor.BLACK , match.getTowerColorFromIsland(4));
    }

    @Test
    void testKnightFigureCard() throws MaxNumberException, WrongDataplayerException, WrongColorException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, SameInfluenceException, NoTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers, NoMoreTowerException, NoMoreBlockCardsException, NoIslandException, TowerIDAlreadyExistingException, MaxNumberOfTowerPassedException, FinishedGameIslandException, NoMoreStudentsException, CardAlreadyPlayedException, FinishedGameEndTurnException, NoMasterException {
        creationOfTheRightCard(new Knight());

        //Knight call:
        ((ExpertMatch)expertMatch).playFigureCard(new Knight());
        assertEquals(2,new Knight().getPRICECARD());
        assertEquals("Knight card(price: 2 coins)", new Knight().toString());

        Master masterR = new Master(Color.RED);
        Master masterB = new Master(Color.BLUE);
        Master masterY = new Master(Color.YELLOW);
        Master masterP = new Master(Color.PINK);
        Master masterG = new Master(Color.GREEN);

        expertMatch.setDashboardMaster(0,masterB); // 2 influence
        expertMatch.setDashboardMaster(1,masterG);
        expertMatch.setDashboardMaster(1,masterR); // 1 influence
        expertMatch.setDashboardMaster(1,masterY); // 2 influence
        expertMatch.setDashboardMaster(0,masterP);

        expertMatch.setIslandsStudents(0,students);

        Tower tower = new Tower(TowerColor.GREY,0);
        ArrayList<Tower> tmpTowers = new ArrayList<>();
        tmpTowers.add(tower);

        expertMatch.changeTowerColorOnIsland();
        assertEquals(TowerColor.BLACK,  expertMatch.getTowerColorFromIsland(0));

        expertMatch.addIslandsTowers(1,expertMatch.removeTowersFromDashboard(1,1));
        expertMatch.chooseCard(new Card(5,5,2));
        expertMatch.chooseCard(new Card(10,5,10));

        expertMatch.moveMotherNature(1);
        expertMatch.changeTowerColorOnIsland();
        assertEquals(TowerColor.WHITE,  expertMatch.getTowerColorFromIsland(1));
    }

    @Override
    public void update(Observable o, Object arg) {

        if(o instanceof ExpertMatch && arg instanceof Merchant) {
            System.out.println(arg);
            assertEquals(1,((Merchant) arg).getPRICECARD());

            Set<Student> extractionTest = new HashSet<>();
            extractionTest.add(((Merchant)arg).getStudentsOnCard().stream().toList().get(0));

            try {
                ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, 1);
                assertThrows(InexistentStudentException.class,()->((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, 1));

                assertFalse(((Merchant)arg).getStudentsOnCard().containsAll(extractionTest));
                assertEquals( ((Merchant)arg).getStudentsNumOnCard(),((Merchant)arg).getStudentsOnCard().size());

                extractionTest.clear();
                extractionTest.add(((Merchant)arg).getStudentsOnCard().stream().toList().get(1));
                extractionTest.add(((Merchant)arg).getStudentsOnCard().stream().toList().get(2));
                assertThrows(MaxNumberException.class,()->((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, 1));

            } catch (MaxNumberException | InexistentStudentException | StudentIDAlreadyExistingException | WrongColorException | NoMoreStudentsException | NoIslandException e) {
                e.printStackTrace();
            }

        }
        else if(o instanceof ExpertMatch && arg instanceof Jester) {
            System.out.println(arg);
            assertEquals(1,((Jester) arg).getPRICECARD());

            Set<Student> extractionTest = new HashSet<>();
            Set<Student> studentFromEntranceTest=new HashSet<>();

            studentFromEntranceTest.add(expertMatch.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0));

            assertThrows(MaxNumberException.class,()->((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest,studentFromEntranceTest ));

            extractionTest.add(((Jester)arg).getStudentsOnCard().stream().toList().get(0));

            try {
                ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, studentFromEntranceTest);

                studentFromEntranceTest.clear();
                studentFromEntranceTest.add(expertMatch.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0));

                assertThrows(InexistentStudentException.class,()->((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, studentFromEntranceTest));

                assertFalse(((Jester)arg).getStudentsOnCard().containsAll(extractionTest));
                assertEquals(((Jester)arg).getStudentsNumOnCard(),((Jester)arg).getStudentsOnCard().size());

                extractionTest.clear();
                extractionTest.add(((Jester)arg).getStudentsOnCard().stream().toList().get(0));
                extractionTest.add(((Jester)arg).getStudentsOnCard().stream().toList().get(3));
                extractionTest.add(((Jester)arg).getStudentsOnCard().stream().toList().get(1));
                extractionTest.add(((Jester)arg).getStudentsOnCard().stream().toList().get(2));

                studentFromEntranceTest.add(expertMatch.showCurrentPlayerDashboard().showEntrance().stream().toList().get(1));
                studentFromEntranceTest.add(expertMatch.showCurrentPlayerDashboard().showEntrance().stream().toList().get(2));
                studentFromEntranceTest.add(expertMatch.showCurrentPlayerDashboard().showEntrance().stream().toList().get(3));

                assertThrows(MaxNumberException.class,()->((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, studentFromEntranceTest));

                Set<Student> extractionTest2 = new HashSet<>();
                extractionTest2.add(((Jester)arg).getStudentsOnCard().stream().toList().get(3));
                extractionTest2.add(((Jester)arg).getStudentsOnCard().stream().toList().get(1));
                extractionTest2.add(((Jester)arg).getStudentsOnCard().stream().toList().get(2));

                studentFromEntranceTest.clear();
                studentFromEntranceTest.add(expertMatch.showCurrentPlayerDashboard().showEntrance().stream().toList().get(1));
                studentFromEntranceTest.add(expertMatch.showCurrentPlayerDashboard().showEntrance().stream().toList().get(2));
                studentFromEntranceTest.add(expertMatch.showCurrentPlayerDashboard().showEntrance().stream().toList().get(3));

                ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest2, studentFromEntranceTest);

                assertFalse(((Jester)arg).getStudentsOnCard().containsAll(extractionTest2));
                assertEquals(((Jester)arg).getStudentsNumOnCard(),((Jester)arg).getStudentsOnCard().size());


            } catch (MaxNumberException | InexistentStudentException | StudentIDAlreadyExistingException | NoMoreStudentsException e) {
                e.printStackTrace();
            }
        }


        else if(o instanceof ExpertMatch && arg instanceof Princess) {
            System.out.println(arg);
            assertEquals(2,((Princess) arg).getPRICECARD());

            Set<Student> extractionTest = new HashSet<>();
            extractionTest.add(((Princess) arg).getStudentsOnCard().stream().toList().get(0));

            try {
                ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest);
                assertThrows(InexistentStudentException.class, () -> ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest));

                assertFalse(((Princess) arg).getStudentsOnCard().containsAll(extractionTest));
                assertEquals(((Princess) arg).getStudentsNumOnCard(), ((Princess) arg).getStudentsOnCard().size());

                extractionTest.clear();
                extractionTest.add(((Princess) arg).getStudentsOnCard().stream().toList().get(0));
                extractionTest.add(((Princess) arg).getStudentsOnCard().stream().toList().get(1));

                assertThrows(MaxNumberException.class, () -> ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest));

            } catch (MaxNumberException | InexistentStudentException | StudentIDAlreadyExistingException | WrongColorException | NoMoreStudentsException e) {
                e.printStackTrace();
            } catch (NoMasterException e) {
                throw new RuntimeException(e);
            }
        }

        else if(o instanceof ExpertMatch && arg instanceof GrannyGrass){
            System.out.println(arg);
            assertEquals(2,((GrannyGrass) arg).getPRICECARD());

            try{
            Master masterR = new Master(Color.RED);
            Master masterB = new Master(Color.BLUE);
            Master masterY = new Master(Color.YELLOW);
            Master masterP = new Master(Color.PINK);
            Master masterG = new Master(Color.GREEN);

            expertMatch.setDashboardMaster(1,masterB); // 2 influence
            expertMatch.setDashboardMaster(1,masterG);
            expertMatch.setDashboardMaster(1,masterR); // 1 influence
            expertMatch.setDashboardMaster(1,masterY); // 2 influence
            expertMatch.setDashboardMaster(0,masterP);

            expertMatch.setIslandsStudents(0,students);


            ((ExpertMatch) expertMatch).placeForbiddenCards(0);

            ArrayList<Tower> tmpTowers = new ArrayList<>(expertMatch.removeTowersFromDashboard(0,1));
            expertMatch.chooseCard(new Card(5,5,2));
            expertMatch.addIslandsTowers(0,tmpTowers);
            expertMatch.moveMotherNature(0);

            assertEquals(TowerColor.BLACK, expertMatch.getTowerColorFromIsland(0));
            expertMatch.moveMotherNature(0);

            assertEquals(TowerColor.WHITE, expertMatch.getTowerColorFromIsland(0));

            ((ExpertMatch) expertMatch).placeForbiddenCards(0);
            ((ExpertMatch) expertMatch).placeForbiddenCards(1);
            ((ExpertMatch) expertMatch).placeForbiddenCards(2);

            ((ExpertMatch) expertMatch).placeForbiddenCards(3);
            assertThrows(NoMoreBlockCardsException.class, ()-> ((ExpertMatch) expertMatch).placeForbiddenCards(4));
            assertEquals(0,expertMatch.getRemainingBlockCards());
            }
            catch (Exception e){ e.printStackTrace();}
        }

        else if(o instanceof ExpertMatch && arg instanceof MushroomCollector){
            System.out.println(arg);
            assertEquals(3,((MushroomCollector) arg).getPRICECARD());

            ((ExpertMatch) expertMatch).blockColorForInfluence(Color.RED);

            Master masterR = new Master(Color.RED);
            Master masterB = new Master(Color.BLUE);
            Master masterY = new Master(Color.YELLOW);
            Master masterP = new Master(Color.PINK);
            Master masterG = new Master(Color.GREEN);

            expertMatch.setDashboardMaster(0,masterB); // 2 influence
            expertMatch.setDashboardMaster(1,masterG);
            expertMatch.setDashboardMaster(1,masterR); // 1 influence
            expertMatch.setDashboardMaster(1,masterY); // 2 influence
            expertMatch.setDashboardMaster(0,masterP);

            expertMatch.setIslandsStudents(0,students);
            try {
                expertMatch.moveMotherNature(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            expertMatch.setNextCurrDashboard();

            try {
                expertMatch.moveMotherNature(0);
                assertEquals(TowerColor.WHITE,expertMatch.getTowerColorFromIsland(0));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //assertEquals(TowerColor.BLACK, expertMatch.getTowerColorFromIsland(0));}


        }

    }

    //Test Zambo endMatch
    //This test checks that a player win the Game when ends all his towers in a match with 2 player
    @Test
    void endMatchTowerFinishedTest() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException, NoMasterException, FinishedGameIslandException, NoMoreBlockCardsException, NoMoreTowerException, TowerIDAlreadyExistingException, SameInfluenceException, InvalidNumberOfTowers, NoIslandException, NoTowerException, NoListOfSameColoredTowers, CardNotFoundException, MaxNumberOfTowerPassedException, CardAlreadyPlayedException, FinishedGameEndTurnException {
        Match match = new ExpertMatch(2);
        match.addPlayer("1", "BLACK", "WIZARD1",1);
        match.addPlayer("2", "WHITE", "WIZARD2",2);
        boolean exception = false;

        assertEquals(match.showCurrentPlayerDashboard().getPlayer().getNickname(), "1");

        for ( Color k : Color.values() ) {
            match.dashboardsCollection.get(0).insertMaster(match.getMasters().get(k));
            assertTrue(match.dashboardsCollection.get(0).haveMaster(k));
        }

        match.islands.get(0).addStudent(new Student(500, Color.RED));
        match.islands.get(6).addStudent(new Student(501, Color.RED));

        match.dashboardsCollection.get(0).playChosenCard(new Card(10, 5, 10));

        do {
            match.moveMotherNature(1);
        }while(match.dashboardsCollection.get(0).getTowersNum()>1);

        try {
            match.moveMotherNature(1);
        } catch ( NoMoreTowerException e ) {
            exception = true;
            e.manageException(match);
        }

        assertTrue(exception);

        assertTrue(match.getWinnerPlayers().contains(match.dashboardsCollection.get(0).getPlayer()));
        assertFalse(match.getWinnerPlayers().contains(match.dashboardsCollection.get(1).getPlayer()));

    }

    //This test checks that player win the game when there are only 3 group of islands, and both players have the same number of tower on islands
    @Test
    void endMatch3IslandSameTowerTest() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException, NoMasterException, FinishedGameIslandException, NoMoreBlockCardsException, NoMoreTowerException, TowerIDAlreadyExistingException, SameInfluenceException, InvalidNumberOfTowers, NoIslandException, NoTowerException, NoListOfSameColoredTowers, CardNotFoundException, MaxNumberOfTowerPassedException, CardAlreadyPlayedException, FinishedGameEndTurnException {
        Match match = new ExpertMatch(2);
        match.addPlayer("1", "BLACK", "WIZARD1", 1);
        match.addPlayer("2", "WHITE", "WIZARD2", 2);
        boolean exception = false;

        assertEquals(match.dashboardsCollection.get(0).getPlayer().getNickname(), "1");
        assertEquals(match.dashboardsCollection.get(1).getPlayer().getNickname(), "2");

        for (Color k : Color.values()) {
            match.dashboardsCollection.get(0).insertMaster(match.getMasters().get(k));
            assertTrue(match.dashboardsCollection.get(0).haveMaster(k));
        }

        match.islands.get(0).addStudent(new Student(500, Color.RED));
        match.islands.get(6).addStudent(new Student(501, Color.RED));

        match.dashboardsCollection.get(0).playChosenCard(new Card(10, 5, 10));

        for ( int i=0; i<4; i++ ) {
            match.moveMotherNature(1);
        }

        for (Color k : Color.values()) {
            match.dashboardsCollection.get(1).insertMaster(match.dashboardsCollection.get(0).removeMaster(k));
            assertTrue(match.dashboardsCollection.get(1).haveMaster(k));
        }

        for ( int i=0; i<6; i++ ) {
            match.moveMotherNature(1);
        }

        for (Color k : Color.values()) {
            match.dashboardsCollection.get(0).insertMaster(match.dashboardsCollection.get(1).removeMaster(k));
            assertTrue(match.dashboardsCollection.get(0).haveMaster(k));
        }

        match.moveMotherNature(1);

        try {
            match.moveMotherNature(1);
        } catch ( FinishedGameIslandException e ) {
            exception = true;
            e.manageException(match);
        }

        assertTrue(exception);
        assertTrue(match.getWinnerPlayers().contains(match.dashboardsCollection.get(0).getPlayer()));
        assertFalse(match.getWinnerPlayers().contains(match.dashboardsCollection.get(1).getPlayer()));



    }

    //This test checks that player win the game when there are only 3 group of islands, and he has the major number of Towers on islands
    @Test
    void endMatch3IslandTest() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException, NoMasterException, FinishedGameIslandException, NoMoreBlockCardsException, NoMoreTowerException, TowerIDAlreadyExistingException, SameInfluenceException, InvalidNumberOfTowers, NoIslandException, NoTowerException, NoListOfSameColoredTowers, CardNotFoundException, MaxNumberOfTowerPassedException, CardAlreadyPlayedException, FinishedGameEndTurnException {
        Match match = new ExpertMatch(2);
        match.addPlayer("1", "BLACK", "WIZARD1", 1);
        match.addPlayer("2", "WHITE", "WIZARD2", 2);
        boolean exception = false;

        assertEquals(match.dashboardsCollection.get(0).getPlayer().getNickname(), "1");
        assertEquals(match.dashboardsCollection.get(1).getPlayer().getNickname(), "2");

        for (Color k : Color.values()) {
            match.dashboardsCollection.get(0).insertMaster(match.getMasters().get(k));
            assertTrue(match.dashboardsCollection.get(0).haveMaster(k));
        }

        match.islands.get(0).addStudent(new Student(500, Color.RED));
        match.islands.get(6).addStudent(new Student(501, Color.RED));

        match.dashboardsCollection.get(0).playChosenCard(new Card(10, 5, 10));

        for ( int i=0; i<2; i++ ) {
            match.moveMotherNature(1);
        }

        for (Color k : Color.values()) {
            match.dashboardsCollection.get(1).insertMaster(match.dashboardsCollection.get(0).removeMaster(k));
            assertTrue(match.dashboardsCollection.get(1).haveMaster(k));
        }

        for ( int i=0; i<7; i++ ) {
            match.moveMotherNature(1);
        }

        for (Color k : Color.values()) {
            match.dashboardsCollection.get(0).insertMaster(match.dashboardsCollection.get(1).removeMaster(k));
            assertTrue(match.dashboardsCollection.get(0).haveMaster(k));
        }

        match.moveMotherNature(1);
        match.moveMotherNature(1);

        try {
            match.moveMotherNature(1);
        } catch ( FinishedGameIslandException e ) {
            exception = true;
            e.manageException(match);
        }

        assertTrue(exception);
        assertFalse(match.getWinnerPlayers().contains(match.dashboardsCollection.get(0).getPlayer()));
        assertTrue(match.getWinnerPlayers().contains(match.dashboardsCollection.get(1).getPlayer()));

    }

    //This test checks that both players with same number of tower and same number of masters win the game when there are only 3 group of islands
    @Test
    void endMatch3IslandDrawTest() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException, NoMasterException, FinishedGameIslandException, NoMoreBlockCardsException, NoMoreTowerException, TowerIDAlreadyExistingException, SameInfluenceException, InvalidNumberOfTowers, NoIslandException, NoTowerException, NoListOfSameColoredTowers, CardNotFoundException, MaxNumberOfTowerPassedException, CardAlreadyPlayedException, FinishedGameEndTurnException {
        Match match = new ExpertMatch(2);
        match.addPlayer("1", "BLACK", "WIZARD1", 1);
        match.addPlayer("2", "WHITE", "WIZARD2", 2);
        boolean exception = false;

        assertEquals(match.dashboardsCollection.get(0).getPlayer().getNickname(), "1");
        assertEquals(match.dashboardsCollection.get(1).getPlayer().getNickname(), "2");

        for (Color k : Color.values()) {
            match.dashboardsCollection.get(0).insertMaster(match.getMasters().get(k));
            assertTrue(match.dashboardsCollection.get(0).haveMaster(k));
        }

        match.islands.get(0).addStudent(new Student(500, Color.RED));
        match.islands.get(6).addStudent(new Student(501, Color.RED));

        match.dashboardsCollection.get(0).playChosenCard(new Card(10, 5, 10));

        for ( int i=0; i<4; i++ ) {
            match.moveMotherNature(1);
        }

        for (Color k : Color.values()) {
            match.dashboardsCollection.get(1).insertMaster(match.dashboardsCollection.get(0).removeMaster(k));
            assertTrue(match.dashboardsCollection.get(1).haveMaster(k));
        }

        for ( int i=0; i<6; i++ ) {
            match.moveMotherNature(1);
        }

        for (Color k : Color.values()) {
            match.dashboardsCollection.get(0).insertMaster(match.dashboardsCollection.get(1).removeMaster(k));
            assertTrue(match.dashboardsCollection.get(0).haveMaster(k));
        }

        match.moveMotherNature(1);

        match.dashboardsCollection.get(1).insertMaster(match.dashboardsCollection.get(0).removeMaster(Color.GREEN));
        match.dashboardsCollection.get(1).insertMaster(match.dashboardsCollection.get(0).removeMaster(Color.BLUE));
        match.dashboardsCollection.get(0).removeMaster(Color.PINK);

        try {
            match.moveMotherNature(1);
        } catch ( FinishedGameIslandException e ) {
            exception = true;
            e.manageException(match);
        }

        assertTrue(exception);
        assertTrue(match.getWinnerPlayers().contains(match.dashboardsCollection.get(0).getPlayer()));
        assertTrue(match.getWinnerPlayers().contains(match.dashboardsCollection.get(1).getPlayer()));

    }

    //This test checks that the Match ends correctly when there are no more students in the bag
    @Test
    void studentsFinishedEndOfMatchTest() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException, WrongCloudNumberException, FinishedGameIslandException, NoMoreBlockCardsException, NoMoreTowerException, TowerIDAlreadyExistingException, SameInfluenceException, InvalidNumberOfTowers, NoIslandException, NoTowerException, NoListOfSameColoredTowers, CardNotFoundException, MaxNumberOfTowerPassedException, FinishedGameEndTurnException, NoMasterException, CardAlreadyPlayedException {
        Match match2 = new Match(2);
        match2.addPlayer("1", "BLACK", "WIZARD1", 1);
        match2.addPlayer("2", "WHITE", "WIZARD2", 2);
        boolean exception = false;

        assertEquals(match2.dashboardsCollection.get(0).getPlayer().getNickname(), "1");
        assertEquals(match2.dashboardsCollection.get(1).getPlayer().getNickname(), "2");

        for (Color k : Color.values()) {
            match2.dashboardsCollection.get(0).insertMaster(match2.getMasters().get(k));
            assertTrue(match2.dashboardsCollection.get(0).haveMaster(k));
        }

        match2.chooseCard(new Card(9, 5, 9));
        match2.chooseCard(new Card(10, 5, 10));

        for ( int j = 0; j<3; j++ )
            match2.moveStudentFromEntranceToDR(match2.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0));
        match2.moveStudentsFromCloudToEntrance(0);
        for ( int j = 0; j<3; j++ )
            match2.moveStudentFromEntranceToIsland(match2.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0), 0);
        match2.moveStudentsFromCloudToEntrance(1);

        for( int i=0; i<16; i++) {
            try {
                for ( int j = 0; j<3; j++ )
                    match2.moveStudentFromEntranceToIsland(match2.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0), 0);
                match2.moveStudentsFromCloudToEntrance(0);
                for ( int j = 0; j<3; j++ )
                    match2.moveStudentFromEntranceToIsland(match2.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0), 0);
                match2.moveStudentsFromCloudToEntrance(1);
            } catch ( WrongCloudNumberException e ) {
                System.out.println(e);
            }
        }

        for ( int j = 0; j<3; j++ )
            match2.moveStudentFromEntranceToDR(match2.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0));
        match2.setNextCurrDashboard();
        for ( int j = 0; j<3; j++ )
            match2.moveStudentFromEntranceToIsland(match2.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0), 0);
        match2.setNextCurrDashboard();

        match2.moveMotherNature(1);

        try {
            match2.moveStudentsFromCloudToEntrance(0);
            match2.moveStudentsFromCloudToEntrance(1);
        } catch ( FinishedGameEndTurnException e ) {
            exception = true;
            e.manageException(match2);
        }

        assertTrue(exception);
        assertTrue(match2.getWinnerPlayers().contains(new Player("1")));
        assertFalse(match2.getWinnerPlayers().contains(new Player("2")));

    }

    //This test checks that the Match ends correctly when a player finishes his cards
    @Test
    void cardFinishedEndOfMatchTest() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException, FinishedGameIslandException, NoMoreBlockCardsException, NoMoreTowerException, TowerIDAlreadyExistingException, SameInfluenceException, InvalidNumberOfTowers, NoIslandException, NoTowerException, NoListOfSameColoredTowers, CardNotFoundException, MaxNumberOfTowerPassedException, CardAlreadyPlayedException, WrongCloudNumberException, NoMasterException {
        Match match = new ExpertMatch(2);
        match.addPlayer("1", "BLACK", "WIZARD1", 1);
        match.addPlayer("2", "WHITE", "WIZARD2", 2);
        boolean exception = false;

        for ( int i=1; i<=10; i++ ) {
            match.chooseCard(new Card(10, 5, i));
            match.chooseCard(new Card(10, 5, ((i+1)%10)+1 ) );
        }

        for (Color k : Color.values()) {
            match.dashboardsCollection.get(0).insertMaster(match.getMasters().get(k));
            assertTrue(match.dashboardsCollection.get(0).haveMaster(k));
        }

        for ( int j = 0; j<3; j++ )
            match.moveStudentFromEntranceToDR(match.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0));
        match.setNextCurrDashboard();
        for ( int j = 0; j<3; j++ )
            match.moveStudentFromEntranceToIsland(match.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0), 0);
        match.setNextCurrDashboard();

        try {
            match.moveStudentsFromCloudToEntrance(0);
            match.moveStudentsFromCloudToEntrance(1);
        } catch ( FinishedGameEndTurnException e ) {
            exception = true;
            e.manageException(match);
        }

        assertTrue(exception);
        assertTrue(match.getWinnerPlayers().contains(match.dashboardsCollection.get(0).getPlayer()));
        assertFalse(match.getWinnerPlayers().contains(match.dashboardsCollection.get(1).getPlayer()));

    }

}
