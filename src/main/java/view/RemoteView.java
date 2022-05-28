package view;

import model.MatchDataInterface;
import model.Message.MatchEndedMessage;
import server.Connection;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class RemoteView extends Observable implements Observer {
    private final Connection connection;

    public RemoteView(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }


    @Override
    public synchronized void update(Observable o, Object arg) {
        if(o instanceof MatchDataInterface){
            if ( arg instanceof MatchEndedMessage )
                connection.sendAndClose(arg);
            else {
                try {
                    connection.send(arg);
                } catch (IOException e) {
                    System.out.println("Tried to send a MatchInferface object from remoteView to a closed connection");
                }
            }
        }
        else if(o instanceof Connection && o==connection){
            setChanged();
            notifyObservers(arg);
        }
    }

}