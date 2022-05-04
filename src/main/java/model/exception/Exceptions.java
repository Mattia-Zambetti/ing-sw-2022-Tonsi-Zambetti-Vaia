package model.exception;

import model.*;

public abstract class Exceptions extends Exception{
    public Exceptions(String errorMessage) { super(errorMessage);}

    public void manageException(Match match){
        match.setErrorMessage(getMessage());
        match.notifyMatchObservers();
        match.setErrorMessage("");
    }
}
