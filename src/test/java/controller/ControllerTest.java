package controller;

import controller.choice.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.*;
import view.choice.*;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    Match match;
    Controller controller;
    TestRemoteView testRemoteView1;
    TestRemoteView testRemoteView2;

    @BeforeEach void init() {

        //initServer();


        match = new NormalMatch(2);
        controller = new Controller(match);

        testRemoteView1 = new TestRemoteView();
        testRemoteView2 = new TestRemoteView();

        match.addObserver(testRemoteView1);
        match.addObserver(testRemoteView2);
        testRemoteView1.addObserver(controller);
        testRemoteView2.addObserver(controller);

        match.notifyMatchObservers();
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

        assertEquals(2, match.showAllPlayersNickname().size());
        assertTrue( match.getChoice() instanceof CardChoice);

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
        assertFalse(toDoChoice.setChoiceParam("1"));
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

    }


}
