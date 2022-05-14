package controller;

import client.Client;
import model.*;
import server.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.*;
import view.choice.*;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    Match match;
    Controller controller;
    RemoteView testRemoteView1;
    RemoteView testRemoteView2;

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


    }

    @Test
    void matchTest() {
        Choice addPlayerChoice = new DataPlayerChoice(2);
        assertTrue(addPlayerChoice.setChoiceParam("Player1"));
        assertTrue(addPlayerChoice.setChoiceParam("1"));
        assertFalse(addPlayerChoice.setChoiceParam("1"));
        ((TestRemoteView)testRemoteView1).setChangedForObservers();
        testRemoteView1.notifyObservers(addPlayerChoice);
        addPlayerChoice = new DataPlayerChoice(2);
        addPlayerChoice.setChoiceParam("Player2");
        addPlayerChoice.setChoiceParam("2");
        addPlayerChoice.setChoiceParam("2");
        ((TestRemoteView)testRemoteView2).setChangedForObservers();
        testRemoteView2.notifyObservers(addPlayerChoice);

        assertEquals(match.showAllPlayersNickname().size(), 2);
        assertTrue( match.getChoice() instanceof CardChoice );

        assertFalse(match.getChoice().setChoiceParam("1"));




    }

    /*
    @Test
    void startMatchTest() {
        controller.startMatch();

        assertEquals(TowerColor.WHITE, normalMatch2P. )
    }*/

}
