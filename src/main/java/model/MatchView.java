package model;


import java.util.Observable;
import java.util.Observer;

//TODO CLASSE INUTILIZZATA DA RIMUOVERE

public class MatchView extends Observable implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof MatchDataInterface && arg instanceof MatchDataInterface){
            setChanged();
            notifyObservers(arg);
            notifyObservers(((MatchDataInterface)arg).toString());
        }
    }
}
