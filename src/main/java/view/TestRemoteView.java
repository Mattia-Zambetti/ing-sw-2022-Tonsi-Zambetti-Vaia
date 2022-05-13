package view;

import model.MatchDataInterface;
import model.Player;
import model.figureCards.FigureCard;
import server.Connection;
import view.choice.Choice;

import java.util.*;

public class TestRemoteView extends RemoteView {

    MatchDataInterface currentMatch;


    public TestRemoteView() {
        super(null);
    }

    public void notifyChoice( Choice c ) {
        setChanged();
        notifyObservers(c);
    }

    public MatchDataInterface getCurrentMatch() {
        return currentMatch;
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        if(o instanceof MatchDataInterface){
            if ( arg instanceof MatchDataInterface)
                currentMatch = (MatchDataInterface) arg;
        }
    }
}
