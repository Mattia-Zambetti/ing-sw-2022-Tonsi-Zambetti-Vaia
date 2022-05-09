package controller;

import client.Client;
import graphicAssets.CLIgraphicsResources;
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

    Match normalMatch2P;
    List<RemoteView> remoteViewList = new ArrayList<>();
    List<Player> playerList = new ArrayList<>();
    Controller controller;
    Server server;
    StringBufferInputStream inputBuffer1 = new StringBufferInputStream(PLAYER1);
    StringBufferInputStream inputBuffer2 = new StringBufferInputStream(PLAYER2);

    private ExecutorService executor = Executors.newFixedThreadPool(128);

    static final Integer PLAYERSNUM = 2;
    static final Integer MATCH_TYPE = 1;
    static final String PLAYER1 = "Tonsi";
    static final String PLAYER2 = "Vaia";
    static Connection NULL_CONNECTION;

    static {
        try {
            NULL_CONNECTION = new Connection(null,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @BeforeEach void init() {

        //initServer();

        remoteViewList = new ArrayList<>();
        playerList = new ArrayList<>();

        /*System.setIn(inputBuffer1);
        System.setOut(System.out);
        initClient();
        System.setOut(System.out);
        System.setIn(inputBuffer2);
        System.setOut(System.out);
        initClient();
        System.setOut(System.out);*/

        playerList.add(0, new Player(PLAYER1));
        playerList.add(1, new Player(PLAYER2));

        for ( int i=0; i<PLAYERSNUM; i++) {
            remoteViewList.add(new TestRemoteView(playerList.get(i)));
        }

        setAllInputChoicePlayer1();
        setAllInputChoicePlayer2();

        Choice startChoice = new StartingMatchChoice();
        remoteViewList.get(0).choiceUser( startChoice );

        assertEquals(PLAYERSNUM,((StartingMatchChoice) startChoice).getTotalPlayersNumMatch());
        assertEquals(MATCH_TYPE,((StartingMatchChoice) startChoice).getMatchType());

        normalMatch2P = new NormalMatch(PLAYERSNUM);

        controller = new Controller(normalMatch2P, remoteViewList, false);

        for (RemoteView remoteView : remoteViewList) {
            normalMatch2P.addObserver(remoteView);
            remoteView.addObserver(controller);
        }

    }

    void initServer() {
        server = new Server();
        Thread t = new Thread( server );
        t.start();
    }

    void initClient() {
        Client client=new Client("127.0.0.1", 12345);
        Thread t = new Thread( client );
        t.start();
    }

    void writeInBuffer1( String s ) {
        inputBuffer1 = new StringBufferInputStream(s);
    }

    void writeInBuffer2( String s ) {
        inputBuffer2 = new StringBufferInputStream(s);
    }

    void setAllInputChoicePlayer1() {
        ((TestRemoteView)remoteViewList.get(0)).setInputString(0,PLAYERSNUM.toString());
        ((TestRemoteView)remoteViewList.get(0)).setInputString(1,MATCH_TYPE.toString());
        ((TestRemoteView)remoteViewList.get(0)).setInputString(2,"1");
        ((TestRemoteView)remoteViewList.get(0)).setInputString(3,"1");
    }

    void setAllInputChoicePlayer2() {
        ((TestRemoteView)remoteViewList.get(1)).setInputString(0,"1");
        ((TestRemoteView)remoteViewList.get(1)).setInputString(1,"1");
    }

    /*@Test
    void initTest() {
    }*/

    /*
    @Test
    void startMatchTest() {
        controller.startMatch();

        assertEquals(TowerColor.WHITE, normalMatch2P. )
    }*/
}
