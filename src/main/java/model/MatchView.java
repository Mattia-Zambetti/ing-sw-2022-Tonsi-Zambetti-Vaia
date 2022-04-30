package model;


import java.util.Observable;
import java.util.Observer;

public class MatchView extends Observable implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Match && arg instanceof Match){
            setChanged();
            notifyObservers(arg.toString());
        }
    }
}
