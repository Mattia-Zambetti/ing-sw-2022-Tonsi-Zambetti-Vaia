package model;

import model.figureCards.*;
import model.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void centaurTest() throws FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, MaxNumberException, WrongDataplayerException, WrongColorException, NoMoreStudentsException {
        figureCardTest = new Centaur();

        creationOfTheRightCard(figureCardTest);



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
    void testPostmanFigureCard() throws MaxNumberException, WrongDataplayerException, WrongColorException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, NoMoreBlockCardsException, NoIslandException, NoMoreTowerException, TowerIDAlreadyExistingException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException, SameInfluenceException, FinishedGameIslandException, NoMoreStudentsException, CardAlreadyPlayedException {
        creationOfTheRightCard(new Postman());

        ((ExpertMatch)expertMatch).playFigureCard(new Postman());

        expertMatch.chooseCard(new Card(6,3,6));
        expertMatch.chooseCard(new Card(10,5,10));
        expertMatch.moveMotherNature(5);
        expertMatch.moveMotherNature(3);
        assertThrows(MaxNumberException.class, ()-> expertMatch.moveMotherNature(5));
    }

    @Test
    void testKnightFigureCard() throws MaxNumberException, WrongDataplayerException, WrongColorException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, SameInfluenceException, NoTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers, NoMoreTowerException, NoMoreBlockCardsException, NoIslandException, TowerIDAlreadyExistingException, MaxNumberOfTowerPassedException, FinishedGameIslandException, NoMoreStudentsException, CardAlreadyPlayedException {
        creationOfTheRightCard(new Knight());

        //Knight call:
        ((ExpertMatch)expertMatch).playFigureCard(new Knight());


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
            }
            catch (Exception e){ e.printStackTrace();}
        }

        else if(o instanceof ExpertMatch && arg instanceof MushroomCollector){
            System.out.println(arg);

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
}
