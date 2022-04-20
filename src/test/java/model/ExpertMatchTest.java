package model;

import model.FigureCards.*;
import model.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpertMatchTest implements Observer {
    Match expertMatch;
    FigureCard figureCardTest;
    ArrayList<Student> students[];

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
    private void creationOftheRightCard(FigureCard figureCardWanted) throws MaxNumberException, WrongDataplayerException, WrongColorException {
        do {
            expertMatch = new ExpertMatch(3);

        }while(!(((ExpertMatch)expertMatch).showFigureCardsInGame()).contains(figureCardWanted));

        expertMatch.addPlayer("Tonsi", "BLACK", "WIZARD1");
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();

        expertMatch.addPlayer("Zambo", "GREY", "WIZARD2");
    }



    //it tests if the centaur card is correctly played when it's called and if it throws the exception if it's called
    //two times in the same turn
    @Test
    void centaurTest() throws FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, MaxNumberException, WrongDataplayerException, WrongColorException {
        figureCardTest = new Centaur();

        creationOftheRightCard(figureCardTest);



        assertEquals(3,figureCardTest.getPrice());

        ((ExpertMatch)expertMatch).playFigureCard(figureCardTest);

        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();


        assertThrows(FigureCardAlreadyPlayedInThisTurnException.class, ()->((ExpertMatch)expertMatch).playFigureCard(figureCardTest));

        assertEquals(4, figureCardTest.getPrice());
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
        creationOftheRightCard(new Merchant());
        expertMatch.addObserver(this);

        //merchant call:
        ((ExpertMatch)expertMatch).playFigureCard(new Merchant());

        creationOftheRightCard(new Jester());
        expertMatch.addObserver(this);

        //jester call:
        ((ExpertMatch)expertMatch).playFigureCard(new Jester());

        creationOftheRightCard(new Princess());
        expertMatch.addObserver(this);

        //princess call:
        ((ExpertMatch)expertMatch).playFigureCard(new Princess());

        creationOftheRightCard(new Witch());
        expertMatch.addObserver(this);

        //princess call:
        ((ExpertMatch)expertMatch).playFigureCard(new Witch());

        creationOftheRightCard(new MushroomCollector());
        expertMatch.addObserver(this);

        //princess call:
        ((ExpertMatch)expertMatch).playFigureCard(new MushroomCollector());
    }

    @Test
    void testPostmanFigureCard() throws MaxNumberException, WrongDataplayerException, WrongColorException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, NoMoreBlockCardsException, SameInfluenceException, NoIslandException {
        creationOftheRightCard(new Postman());

        ((ExpertMatch)expertMatch).playFigureCard(new Postman());

        expertMatch.chooseCard(new Card(5,3,1));
        expertMatch.moveMotherNature(5);
        expertMatch.moveMotherNature(3);
        assertThrows(MaxNumberException.class, ()-> expertMatch.moveMotherNature(5));
    }

    @Test
    void testKnightFigureCard() throws MaxNumberException, WrongDataplayerException, WrongColorException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, SameInfluenceException, NoTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers, NegativeNumberOfTowerException, NoMoreBlockCardsException, NoIslandException {
        creationOftheRightCard(new Knight());

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
        ArrayList<Tower> tmpTowers = new ArrayList<Tower>();
        tmpTowers.add(tower);

        expertMatch.changeTowerColorOnIsland();
        assertEquals(TowerColor.BLACK,  expertMatch.getTowerColorFromIsland(0));

        expertMatch.addIslandsTowers(1,expertMatch.removeTowersFromDashboard(1,1));
        expertMatch.chooseCard(new Card(5,5,2));
        expertMatch.moveMotherNature(1);
        expertMatch.changeTowerColorOnIsland();
        assertEquals(TowerColor.GREY,  expertMatch.getTowerColorFromIsland(1));
    }


    private void takeStudentsFromFigureCardTest() throws Exception, WrongDataplayerException {
        creationOftheRightCard(new Merchant());
        expertMatch.addObserver(this);

    }

    @Override
    public void update(Observable o, Object arg) {

        if(o instanceof ExpertMatch && arg instanceof Merchant) {
            System.out.println(arg);

            Set<Student> extractionTest = new HashSet<Student>();
            extractionTest.add(((Merchant)arg).getStudentsOnCard().stream().toList().get(0));

            try {
                ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, (Merchant)arg, 1);
                assertThrows(InexistentStudentException.class,()->((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, (Merchant)arg, 1));

                assertFalse(((Merchant)arg).getStudentsOnCard().containsAll(extractionTest));
                assertEquals( ((Merchant)arg).getStudentsNumOnCard(),((Merchant)arg).getStudentsOnCard().size());

                extractionTest.add(((Merchant)arg).getStudentsOnCard().stream().toList().get(1));
                extractionTest.add(((Merchant)arg).getStudentsOnCard().stream().toList().get(2));
                assertThrows(MaxNumberException.class,()->((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, (Merchant)arg, 1));

            } catch (MaxNumberException | InexistentStudentException | StudentIDAlreadyExistingException | WrongColorException | NoMoreStudentsException e) {
                e.printStackTrace();
            }

        }
        else if(o instanceof ExpertMatch && arg instanceof Jester) {
            System.out.println(arg);

            Set<Student> extractionTest = new HashSet<>();
            assertThrows(MaxNumberException.class,()->((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, (Jester)arg, 1));

            extractionTest.add(((Jester)arg).getStudentsOnCard().stream().toList().get(0));

            try {
                ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, (Jester)arg, 1);
                assertThrows(InexistentStudentException.class,()->((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, (Jester)arg, 1));

                assertFalse(((Jester)arg).getStudentsOnCard().containsAll(extractionTest));
                assertEquals(((Jester)arg).getStudentsNumOnCard(),((Jester)arg).getStudentsOnCard().size());

                extractionTest.add(((Jester)arg).getStudentsOnCard().stream().toList().get(3));
                extractionTest.add(((Jester)arg).getStudentsOnCard().stream().toList().get(1));
                extractionTest.add(((Jester)arg).getStudentsOnCard().stream().toList().get(2));
                assertThrows(MaxNumberException.class,()->((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, (Jester)arg, 1));

                Set<Student> extractionTest2 = new HashSet<>();
                extractionTest2.add(((Jester)arg).getStudentsOnCard().stream().toList().get(3));
                extractionTest2.add(((Jester)arg).getStudentsOnCard().stream().toList().get(1));
                extractionTest2.add(((Jester)arg).getStudentsOnCard().stream().toList().get(2));
                ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest2, (Jester)arg, 1);

                assertFalse(((Jester)arg).getStudentsOnCard().containsAll(extractionTest2));
                assertEquals(((Jester)arg).getStudentsNumOnCard(),((Jester)arg).getStudentsOnCard().size());


            } catch (MaxNumberException | InexistentStudentException | StudentIDAlreadyExistingException | WrongColorException | NoMoreStudentsException e) {
                e.printStackTrace();
            }
        }


        else if(o instanceof ExpertMatch && arg instanceof Princess) {
            System.out.println(arg);

            Set<Student> extractionTest = new HashSet<Student>();
            extractionTest.add(((Princess) arg).getStudentsOnCard().stream().toList().get(0));

            try {
                ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, (Princess) arg, 1);
                assertThrows(InexistentStudentException.class, () -> ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, (Princess) arg, 1));

                assertFalse(((Princess) arg).getStudentsOnCard().containsAll(extractionTest));
                assertEquals(((Princess) arg).getStudentsNumOnCard(), ((Princess) arg).getStudentsOnCard().size());

                extractionTest.add(((Princess) arg).getStudentsOnCard().stream().toList().get(1));
                assertThrows(MaxNumberException.class, () -> ((ExpertMatch) expertMatch).takeStudentsOnFigureCard(extractionTest, (Princess) arg, 1));

            } catch (MaxNumberException | InexistentStudentException | StudentIDAlreadyExistingException | WrongColorException | NoMoreStudentsException e) {
                e.printStackTrace();
            }
        }

        else if(o instanceof ExpertMatch && arg instanceof Witch){
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
            Tower tower = new Tower(TowerColor.BLACK,0);
            ArrayList<Tower> tmpTowers = new ArrayList<Tower>();
            tmpTowers.addAll(expertMatch.removeTowersFromDashboard(0,1));
            expertMatch.chooseCard(new Card(5,5,2));
            expertMatch.addIslandsTowers(0,tmpTowers);
            expertMatch.moveMotherNature(0);

            assertEquals(TowerColor.BLACK, expertMatch.getTowerColorFromIsland(0));
            expertMatch.moveMotherNature(0);

            assertEquals(TowerColor.GREY, expertMatch.getTowerColorFromIsland(0));

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
                assertEquals(TowerColor.GREY,expertMatch.getTowerColorFromIsland(0));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //assertEquals(TowerColor.BLACK, expertMatch.getTowerColorFromIsland(0));}


        }

    }
}
