package model.exception;

import model.Match;

public class CardAlreadyPlayedException extends Exceptions {
    public CardAlreadyPlayedException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(Match match){
        match.setErrorMessage(getMessage());
        match.notifyMatchObservers();
        match.setErrorMessage("");
    }
}
