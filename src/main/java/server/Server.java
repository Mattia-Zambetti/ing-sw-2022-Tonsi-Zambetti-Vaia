package server;

import controller.Controller;
import controller.choice.Choice;
import controller.choice.DataPlayerChoice;
import controller.choice.StartingMatchChoice;
import model.ExpertMatch;
import model.Match;
import model.Message.FullLobbyMessage;
import model.Message.RegistrationConfirmed;
import model.NormalMatch;
import view.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private static final int PORT = 12345;


    private static int idClient=0;


    private ServerSocket serverSocket;

    private ExecutorService executor;
    private Queue<Connection> waitingConnections;
    private List<RemoteView> remoteViewList = new ArrayList<>();

    private int totalPlayerNumber=0;
    private int matchType;

    private HashSet<Connection> playersConnections;

    //STATICS:

    private static final int TOTAL_NUM_MATCH_TYPE=2;




    public Server() {
        try {
            executor = Executors.newFixedThreadPool(128);
            waitingConnections =new ArrayDeque<>();
            playersConnections = new HashSet<>();
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int getTOTAL_NUM_MATCH_TYPE() {
        return TOTAL_NUM_MATCH_TYPE;
    }


    public void initNewMatch() throws IOException, ClassNotFoundException, InterruptedException {
        for(int i=0; i<waitingConnections.size(); i++){
            Connection c=waitingConnections.peek();
            c.setActive(true);
            lobby(c);
        }
    }

    @Override
    public void run() {
        do {
            try {
                Socket clientSocket = serverSocket.accept();


                Connection clientConnection = new Connection(clientSocket, this, ++idClient);

                System.out.println("Connection number: "+ (waitingConnections.size()+1));

                executor.submit(clientConnection);

                waitingConnections.add(clientConnection);





            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(true);

    }


    public void setMatchParams(int totalPlayerNumber, int matchType){
        this.totalPlayerNumber = totalPlayerNumber;
        this.matchType = matchType;
    }

    public synchronized void lobby(Connection c) throws IOException, ClassNotFoundException, InterruptedException {
        Match match = null;
        Choice choice;

        //Bisogna sistemare il fatto che il terzo giocatore potrebbe rimanere escluso se entra nel metodo lobby  prima del secondo, anche se la partita è per tre giocatori
        while ( !waitingConnections.peek().equals(c) ) {
            wait();
        }

        if (playersConnections.size() == 0) {
            choice = new StartingMatchChoice();
            c.send(choice);
            Choice startChoice = (Choice) c.readObject();
            setMatchParams(((StartingMatchChoice) startChoice).getTotalPlayersNumMatch(), ((StartingMatchChoice) startChoice).getMatchType());
        }
        if (playersConnections.size() < totalPlayerNumber) {
            c.send(new RegistrationConfirmed(c.getId()));
            c.setActive(true);
            playersConnections.add(c);
            waitingConnections.remove();
        } else {
            c.send(new FullLobbyMessage());
            waitingConnections.remove(c);
            notifyAll();
            return;
        }

        if (playersConnections.size() == totalPlayerNumber) {
            for (Connection connection : playersConnections) {
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
                for (Connection connection : playersConnections) {
                    match.setChoicePhase(new DataPlayerChoice(totalPlayerNumber, connection.getId()));
                    match.notifyMatchObservers();
                }
            } else {
                System.out.println("Error, match not created");
            }
            //Actually the server maintains all the connections in the waiting room, so, when a connection is interrupted, all others connections are closed
            //waitingRoom.clear();
            //nicknames.clear();
        }
        notifyAll();
    }

    public synchronized void deregisterConnections(Connection c) throws IOException, ClassNotFoundException {
        playersConnections.remove(c);
        for ( Connection connection : playersConnections ) {
            connection.closeConnection();
            playersConnections.remove(connection);
        }
        try {
            initNewMatch();
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception in deregisterConnections by initMatch()");
            e.printStackTrace();
        }
    }

}
