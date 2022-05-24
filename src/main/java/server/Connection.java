package server;


import controller.choice.Choice;
import controller.choice.StartingMatchChoice;
import model.Message.PlayerDisconnectedMessage;
import model.Message.RegistrationConfirmed;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

public class Connection extends Observable implements Runnable{

    private Socket clientSocket;
    private ObjectInputStream scannerIn;
    private ObjectOutputStream writeOut;
    private final Server server;

    private boolean isActive=true;


    public Connection(Socket socket, Server server) throws IOException {
        this.clientSocket=socket;
        this.server=server;
    }

    public void send(Object obj){
        try {
            writeOut.writeObject(obj);
            writeOut.flush();
            writeOut.reset();
        } catch (IOException e) {
            System.out.println("You tried to send an object to a connection already closed");
        }
    }

    public synchronized void closeConnection() {
        isActive = false;
        send(new PlayerDisconnectedMessage());
        try{
            scannerIn.close();
            writeOut.close();
            clientSocket.close();
        }catch (IOException e){
            System.out.println("Error closing others clients' socket\n"+e.getMessage());
        }
    }

    private synchronized void closeAllConnections(){
        isActive=false;
        try{
            scannerIn.close();
            writeOut.close();
            clientSocket.close();
        }catch (IOException e){
            System.out.println("Error closing the socket\n"+e.getMessage());
        }
        System.out.println("Deregistering all connections");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    private synchronized boolean isActive() {
        return isActive;
    }

    public void run (){
        try {
            writeOut=new ObjectOutputStream(clientSocket.getOutputStream());
            scannerIn=new ObjectInputStream(clientSocket.getInputStream());
            Choice choice;
            Object o;


            writeOut.writeObject(new RegistrationConfirmed(server.getConnectionsSize()));

            if (server.getConnectionsSize() == 1) {
                choice = new StartingMatchChoice();
                send(choice);
                Choice startChoice = (Choice) scannerIn.readObject();
                server.setMatchParams(((StartingMatchChoice) startChoice).getTotalPlayersNumMatch(), ((StartingMatchChoice) startChoice).getMatchType());
            }



            server.lobby(this);



            while(isActive()) {
                o = scannerIn.readObject();
                if (o instanceof Choice) {
                    choice = (Choice) o;
                    setChanged();
                    notifyObservers(choice);
                }
            }
        } catch (IOException e) {
            System.out.println("Connection closed");
            closeAllConnections();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
