package model;


import utils.Observer;

import java.util.Observable;

public class MatchView extends Observable implements Observer<Match>  {

    private Match matchClone;





    @Override
    public void update(Match message) {
        matchClone=new Match(message);
        setChanged();
        notifyObservers();
    }


    //TODO
    @Override
    public String toString() {
        return "Situazione attuale nella mappa:"+ matchClone;
    }
}
