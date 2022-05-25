package server;


import controller.choice.Choice;
import model.Message.PlayerDisconnectedMessage;

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

    private boolean isActive=false;

    private int id;


    public Connection(Socket socket, Server server, int id) throws IOException {
        this.clientSocket=socket;
        this.server=server;
        this.id=id;
    }


    public int getId() {
        return id;
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return scannerIn.readObject();
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

    private synchronized void closeThisConnection() throws IOException, ClassNotFoundException {
        isActive=false;
        try{
            scannerIn.close();
            writeOut.close();
            clientSocket.close();
        }catch (IOException e){
            System.out.println("Error closing the socket\n"+e.getMessage());
        }
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private synchronized boolean isActive() {
        return isActive;
    }

    public void run (){
        Object o;
        Choice choice;
        try {
            writeOut=new ObjectOutputStream(clientSocket.getOutputStream());
            scannerIn=new ObjectInputStream(clientSocket.getInputStream());


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
            try {
                System.out.println("Deregistering all connections");
                closeThisConnection();
                server.deregisterConnections(this);
                System.out.println("Done!");

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
