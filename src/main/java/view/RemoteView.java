package view;

import model.MatchDataInterface;
import model.Player;
import model.figureCards.FigureCard;
import server.Connection;
import view.choice.Choice;

import java.util.Observable;
import java.util.Observer;

public class RemoteView extends Observable implements Observer {
    //private Player player;
    private final Connection connection;

    public RemoteView(Connection connection) {
        //this.player = player;
        this.connection = connection;
        //connection doesn't notify any more
        //connection.addObserver(this);
    }

    public synchronized void choiceUser(Choice choice) {
        /*choice=connection.sendAndReceive(choice);
        setChanged();
        notifyObservers((Choice)choice);*/
        //TODO
    }

    /*public Player getPlayer() {
        return player;
    }*/

    public Connection getConnection() {
        return connection;
    }

    public void sendError(String message){
        connection.send(message);
    }



    @Override
    public synchronized void update(Observable o, Object arg) {
        if(o instanceof MatchDataInterface){
            if ( arg instanceof MatchDataInterface)
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