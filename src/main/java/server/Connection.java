package server;


import controller.choice.Choice;
import controller.choice.StartingMatchChoice;
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
            e.printStackTrace();
        }
    }

    public synchronized void closeConnection() {
        send("One player logged off, connection closed");
        try{
            clientSocket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        isActive = false;
    }

    private void close(){
        closeConnection();
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
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

}
