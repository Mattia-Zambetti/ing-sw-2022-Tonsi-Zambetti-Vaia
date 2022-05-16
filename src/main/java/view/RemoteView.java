package view;

import model.MatchDataInterface;
import model.figureCards.FigureCard;
import server.Connection;

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

    public void sendError(String message){
        connection.send(message);
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        if(o instanceof MatchDataInterface){
            connection.send(arg);
        }else if (arg instanceof FigureCard) {
            setChanged();
            notifyObservers(arg);
        }
        else if(o instanceof Connection && o==connection){
            setChanged();
            notifyObservers(arg);
        }
    }

}