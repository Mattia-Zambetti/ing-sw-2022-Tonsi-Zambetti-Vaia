package controller;

import controller.choice.*;
import model.Match;
import model.NormalMatch;
import model.exception.CardNotFoundException;
import model.exception.MaxNumberException;
import model.exception.WrongCloudNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.TestRemoteView;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    static final int TOTALPLAYERSNUMBER=2;
    Match match;
    Controller controller;
    TestRemoteView testRemoteView1;
    TestRemoteView testRemoteView2;

    @BeforeEach void init() {

        //initServer();


        match = new NormalMatch(TOTALPLAYERSNUMBER);
        controller = new Controller(match);

        testRemoteView1 = new TestRemoteView();
        testRemoteView2 = new TestRemoteView();

        match.addObserver(testRemoteView1);
        match.addObserver(testRemoteView2);
        testRemoteView1.addObserver(controller);
        testRemoteView2.addObserver(controller);

        for(int i=1; i<=TOTALPLAYERSNUMBER; i++){
            match.setChoicePhase( new DataPlayerChoice(TOTALPLAYERSNUMBER, i));
            match.notifyMatchObservers();
        }
    }

    @Test
    void matchTest() {
        Choice toDoChoice;
        assertTrue( match.getChoice() instanceof DataPlayerChoice);
        toDoChoice = new DataPlayerChoice(2);
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("Player1"));
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("1"));
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        toDoChoice = new DataPlayerChoice(2);
        System.out.println(toDoChoice);
        toDoChoice.setChoiceParam("Player2");
        System.out.println(toDoChoice);
        toDoChoice.setChoiceParam("2");
        System.out.println(toDoChoice);
        toDoChoice.setChoiceParam("2");
        testRemoteView2.setChangedForObservers();
        testRemoteView2.notifyObservers(toDoChoice);

        assertEquals(2, match.showAllPlayers().size());
        assertTrue( match.getChoice() instanceof CardChoice );

        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        toDoChoice = testRemoteView2.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView2.setChangedForObservers();
        testRemoteView2.notifyObservers(toDoChoice);
        assertTrue( match.getChoice() instanceof CardChoice );
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("3"));
        testRemoteView2.setChangedForObservers();
        testRemoteView2.notifyObservers(toDoChoice);

        assertTrue( match.getChoice() instanceof MoveStudentChoice);
        assertEquals( match.showCurrentPlayerDashboard().getPlayer().getNickname(), "Player1");



        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("1"));
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("1"));
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("1"));
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("2"));
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("aa"));
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("2"));
        assertEquals(2, ((MoveStudentChoice)toDoChoice).getIslandID());
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);

        assertTrue( match.getChoice() instanceof MoveMotherNatureChoice);

        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("75"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        assertTrue( match.getChoice() instanceof MoveMotherNatureChoice );
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);

        assertTrue( match.getChoice() instanceof CloudChoice);
        toDoChoice.toString();

        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("aa"));
        assertFalse(toDoChoice.setChoiceParam("1")); //VIENE LANCIATA ECCEZIONE SUL FATTO CHE NON CI SIANO TORI SULL'ISOLA, ERRATO
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);

        assertEquals( match.showCurrentPlayerDashboard().getPlayer().getNickname(), "Player2");
        assertTrue( match.getChoice() instanceof MoveStudentChoice );

        assertEquals("please try again",toDoChoice.retryMessage());
    }


    //It tests how does data player choice work with every type of input(wrong and correct).
    //The Choice has to reject wrong typing and it has to increment the counter of inputs correctly
    @Test
    void DataPlayerChoiceTest2Players(){
        DataPlayerChoice toDoChoice = new DataPlayerChoice(TOTALPLAYERSNUMBER);

        assertTrue(toDoChoice.setChoiceParam("Zambo"));
        assertEquals("Zambo", toDoChoice.getPlayer().getNickname());

        assertTrue(toDoChoice.setChoiceParam("40"));
        assertEquals(1, toDoChoice.getNumChoice());
        assertTrue(toDoChoice.setChoiceParam("1"));
        assertEquals(2, toDoChoice.getNumChoice());

        assertTrue(toDoChoice.setChoiceParam("40"));
        assertEquals(2, toDoChoice.getNumChoice());
        assertFalse(toDoChoice.setChoiceParam("1"));


    }


    @Test
    void DataPlayerChoiceTest3Players(){
        match = new NormalMatch(TOTALPLAYERSNUMBER+1);
        controller = new Controller(match);

        testRemoteView1 = new TestRemoteView();
        testRemoteView2 = new TestRemoteView();
        TestRemoteView testRemoteView3 = new TestRemoteView();

        match.addObserver(testRemoteView1);
        match.addObserver(testRemoteView2);
        match.addObserver(testRemoteView3);
        testRemoteView1.addObserver(controller);
        testRemoteView2.addObserver(controller);
        testRemoteView3.addObserver(controller);

        for(int i=1; i<=TOTALPLAYERSNUMBER+1; i++){
            match.setChoicePhase( new DataPlayerChoice(TOTALPLAYERSNUMBER+1, i));
            match.notifyMatchObservers();
        }

        DataPlayerChoice toDoChoice = new DataPlayerChoice(TOTALPLAYERSNUMBER+1);

        assertTrue(toDoChoice.setChoiceParam("Zambo"));
        assertEquals("Zambo", toDoChoice.getPlayer().getNickname());

        System.out.println(toDoChoice);

        assertTrue(toDoChoice.setChoiceParam("40"));
        assertEquals(1, toDoChoice.getNumChoice());
        assertTrue(toDoChoice.setChoiceParam("1"));
        assertEquals(2, toDoChoice.getNumChoice());

        assertTrue(toDoChoice.setChoiceParam("40"));
        assertTrue(toDoChoice.setChoiceParam("aaa"));
        assertEquals(2, toDoChoice.getNumChoice());
        assertFalse(toDoChoice.setChoiceParam("3"));


        toDoChoice.setPossessor(1);
        assertEquals(1, toDoChoice.getPossessor());
    }

    @Test
    void StartingMatchChoiceTest() throws WrongCloudNumberException, MaxNumberException, CardNotFoundException {
        StartingMatchChoice choice=new StartingMatchChoice();

        System.out.println(choice);

        choice.setChoiceParam("aa");
        assertEquals(0, choice.getNumChoice());
        choice.setChoiceParam("6");
        assertEquals(0, choice.getNumChoice());
        choice.setChoiceParam("2");
        assertEquals(1, choice.getNumChoice());

        System.out.println(choice);

        choice.setChoiceParam("aa");
        assertEquals(1, choice.getNumChoice());
        choice.setChoiceParam("0");
        assertEquals(1, choice.getNumChoice());
        assertFalse(choice.setChoiceParam("1"));

        assertEquals(1, choice.getMatchType());
        assertEquals(2, choice.getTotalPlayersNumMatch());
    }


}
