package server;

import controller.Controller;
import controller.choice.DataPlayerChoice;
import model.*;
import view.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private static final int PORT = 12345;


    private static Set<String> nicknames;


    private ServerSocket serverSocket;

    private ExecutorService executor;
    private List<Connection> connections;
    private List<RemoteView> remoteViewList = new ArrayList<>();

    private int totalPlayerNumber;
    private int matchType;
    private HashSet<Connection> waitingRoom;

    //STATICS:

    private static final int TOTAL_NUM_MATCH_TYPE=2;




    public Server() {
        try {
            executor = Executors.newFixedThreadPool(128);
            connections = new ArrayList<>();
            waitingRoom = new HashSet<>();
            serverSocket = new ServerSocket(PORT);
            nicknames = new HashSet<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int getTOTAL_NUM_MATCH_TYPE() {
        return TOTAL_NUM_MATCH_TYPE;
    }


    @Override
    public void run() {
        do {
            try {
                Socket clientSocket = serverSocket.accept();


                Connection clientConnection = new Connection(clientSocket, this);

                connections.add(clientConnection);

                System.out.println("Connection number: "+ connections.size());

                executor.submit(clientConnection);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(true);

    }



    public synchronized int getConnectionsSize() {
        return connections.size();
    }

    public synchronized void setMatchParams(int totalPlayerNumber, int matchType){
        this.totalPlayerNumber = totalPlayerNumber;
        this.matchType = matchType;
    }

    public synchronized void lobby(Connection c) {
        Match match = null;
        waitingRoom.add(c);


        if (waitingRoom.size() == totalPlayerNumber) {



            for (Connection connection : connections) {
                remoteViewList.add(new RemoteView(connection));
            }

            switch (matchType) {
                case 1:
                    match = new NormalMatch(totalPlayerNumber);
                    break;
                case 2:
                    match = new ExpertMatch(totalPlayerNumber);
                    break;
            }

            Controller controller = new Controller(match);

            if (match != null) {
                for (RemoteView remoteView : remoteViewList) {
                    match.addObserver(remoteView);
                    remoteView.addObserver(controller);
                    remoteView.getConnection().addObserver(remoteView);


                }
                System.out.println("Starting match");
                for(int i=1; i<=totalPlayerNumber; i++){
                    match.setChoicePhase( new DataPlayerChoice(totalPlayerNumber, i));
                    match.notifyMatchObservers();
                }
            }else {
                System.out.println("Error, match not created");
                return;
            }

            waitingRoom.clear();
            nicknames.clear();
        }
    }

}
