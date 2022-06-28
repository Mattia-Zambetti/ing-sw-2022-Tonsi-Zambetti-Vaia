package server;


import controller.choice.Choice;
import model.Message.PlayerDisconnectedMessage;
import model.Message.WaitingMatchCreationMessage;

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

    /** Each connection has a unique ID */
    public int getId() {
        return id;
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return scannerIn.readObject();
    }

    public void send(Object obj) throws IOException {
        writeOut.writeObject(obj);
        writeOut.flush();
        writeOut.reset();
    }

    public void sendAndClose(Object obj){
        setActive(false);
        try {
            writeOut.writeObject(obj);
            writeOut.flush();
            writeOut.reset();
            closeThisConnection();
        } catch (IOException e) {
            System.out.println("You tried to send an object to a connection already closed");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Close this connection when a Server routine happen (for example after a player has disconnected).
     * Send to the relative client a PlayerDisconnectedMessage, so the Client could close his own connection (socket)
     * with the server and reports the problem to the User */
    public synchronized void closeConnection() {
        isActive = false;
        try{
            send(new PlayerDisconnectedMessage());
            System.out.println("Closing connection "+getId()+" in the Server side");
            scannerIn.close();
            writeOut.close();
            clientSocket.close();
            System.out.println("Connection "+getId()+" correctly closed");
        }catch (IOException e){
            System.out.println("Error closing others clients' socket\n"+e.getMessage());
        }
    }

    /** Close this connection after the Client has disconnected (no need to notify the Client, it has closed the connection) */
    public synchronized void closeThisConnection() throws IOException, ClassNotFoundException {
        isActive=false;
        try{
            System.out.println("Closing connection "+getId()+" in the Server side");
            scannerIn.close();
            writeOut.close();
            clientSocket.close();
            System.out.println("Connection "+getId()+" correctly closed");
        }catch (IOException e){
            System.out.println("Error closing the connection: "+e.getMessage());
        }
    }

    public synchronized void setActive(boolean active) {
        isActive = active;
    }

    private synchronized boolean isActive() {
        return isActive;
    }

    /** Thread routine for the connection. Tries to connect to the lobby, when the connection (and so the player) has entered the lobby, wait for
     * a new Object from the input stream of the socket (that is always a Choice) and notifies the Observers (RemoteView) that a new Choice has arrived.*/
    public void run (){
        Object o;
        Choice choice;
        try {
            writeOut=new ObjectOutputStream(clientSocket.getOutputStream());
            scannerIn=new ObjectInputStream(clientSocket.getInputStream());

            if ( server.getPlayersConnectionNumber()>=1 ) {
                send(new WaitingMatchCreationMessage());
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
            System.out.println("Connection "+getId()+" closed from Client");
            try {
                if ( isActive() ) {
                    closeThisConnection();
                    server.deregisterConnections(this);
                }

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
