package model;


import java.util.Observable;
import java.util.Observer;

public class MatchView extends Observable implements Observer {

    private Match matchClone;



    //TODO
    @Override
    public String toString() {
        return "Situazione attuale nella mappa:"+ matchClone;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Match && arg instanceof Match){
            matchClone=new Match((Match) arg);
            setChanged();
            notifyObservers();
        }
    }
}
