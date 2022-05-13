package server;

import controller.Controller;
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


    public Server() {
        try {
            executor = Executors.newFixedThreadPool(128);
            connections = new ArrayList<>();
            waitingRoom = new HashSet<>();
            serverSocket = new ServerSocket(PORT);
            nicknames= new HashSet<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addNickname(String nickname){
        nicknames.add(nickname);
    }

    public synchronized Set getNicknameSet(){
        return nicknames;
    }

    public synchronized int getTotalPlayerNumber() {
        return totalPlayerNumber;
    }

    public synchronized TowerColor[] getTowerColors(){
        return TowerColor.values();
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
                    System.out.println("Starting match");

                }
                match.notifyMatchObservers();
            }else {
                System.out.println("Error, match not created");
                return;
            }

            waitingRoom.clear();
            nicknames.clear();
        }
    }

}

    /*


    //Deregister connection
    public synchronized void deregisterConnection(Connection c){
        connections.remove(c);
        Connection opponent = playingConnection.get(c);
        if(opponent != null){
            //opponent.closeConnection();
            playingConnection.remove(c);
            playingConnection.remove(opponent);
            //Iterator<String> iterator = waitingConnection.keySet().iterator();
            //while(iterator.hasNext()){
            //    if(waitingConnection.get(iterator.next())==c){
            //        iterator.remove();
            //    }
            //}
        }
    }

    public synchronized void lobby(Connection c, String name){
        List<RemoteView> remoteViewList=new ArrayList<>();
        waitingConnection.put(name, c);
        if(waitingConnection.size() == 2){
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Connection c1 = waitingConnection.get(keys.get(0));
            Connection c2 = waitingConnection.get(keys.get(1));

            RemoteView player1 = new RemoteView(new Player("Mariangello", 2), c1);
            RemoteView player2 = new RemoteView(new Player("IlTOnsi",8),c2);

            remoteViewList.add(player1);
            remoteViewList.add(player2);

            Match match = new NormalMatch(2,true);
            Controller controller = new Controller(match,remoteViewList,true);
            match.addObserver(player1);
            match.addObserver(player2);
            player1.addObserver((Observer) controller);
            player2.addObserver((Observer) controller);
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            waitingConnection.clear();
        }

    }



    public void run(){
        int connections = 0;
        System.out.println("Server listening on port: " + PORT);
        while(true){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection number: " + connections);
                connections++;
                Connection connection = new Connection(socket, this);
                registerConnection(connection);
                //executor.submit(connection);
            } catch (IOException e){
                System.err.println("Connection error!");
            }
        }
    }
*/
