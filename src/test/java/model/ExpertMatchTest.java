package model;

import junit.framework.TestCase;
import model.FigureCards.*;
import model.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpertMatchTest extends TestCase implements Observer {
    Match expertMatch;
    FigureCard figureCardTest;


    @BeforeEach
    void init(){
        expertMatch=new ExpertMatch(3);

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

    }
}
