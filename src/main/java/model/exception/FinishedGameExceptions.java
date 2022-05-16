package model.exception;

import model.*;

import view.RemoteView;

import java.util.Map;

public abstract class FinishedGameExceptions extends Exception{
    public FinishedGameExceptions(String errorMessage) { super(errorMessage);}

    public void manageException(Match match) {
        match.notifyMatchObservers();
    }
}
