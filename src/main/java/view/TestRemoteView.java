package view;

import model.MatchDataInterface;

import java.util.Observable;

public class TestRemoteView extends RemoteView {

    MatchDataInterface currentMatch;


    public TestRemoteView() {
        super(null);
    }


    public MatchDataInterface getCurrentMatch() {
        return currentMatch;
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        if(o instanceof MatchDataInterface){
            if ( arg instanceof MatchDataInterface)
                currentMatch = (MatchDataInterface)arg;
        }
    }

    public void setChangedForObservers() {
        setChanged();
    }
}
