package server;

import model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private static final int PORT = 12345;


    private ServerSocket serverSocket;

    private ExecutorService executor;
    private List<Connection> connections;


    private Map<Player, Connection> waitingRoom;
    private Map<Connection, Connection> playingConnection;

    public Server() {
        try {
            executor = Executors.newFixedThreadPool(4);
            connections = new ArrayList<>();
            waitingRoom = new HashMap<>();
            playingConnection = new HashMap<>();
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public int getConnectionsSize() {
        return connections.size();
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
