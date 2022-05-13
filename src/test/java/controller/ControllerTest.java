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

    @BeforeEach void init() {

        //initServer();


        match = new NormalMatch(2);
        controller = new Controller(match);

        RemoteView testRemoteView1 = new TestRemoteView();
        RemoteView testRemoteView2 = new TestRemoteView();

        match.addObserver(testRemoteView1);
        match.addObserver(testRemoteView2);
        testRemoteView1.addObserver(controller);
        testRemoteView2.addObserver(controller);


    }

    @Test
    void addPlayerTest() {

    }

    /*
    @Test
    void startMatchTest() {
        controller.startMatch();

        assertEquals(TowerColor.WHITE, normalMatch2P. )
    }*/

}
