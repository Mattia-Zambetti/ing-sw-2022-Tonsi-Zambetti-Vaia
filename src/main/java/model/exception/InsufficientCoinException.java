//Zambo
package model.exception;

import model.*;

public class InsufficientCoinException extends Exceptions{
    public InsufficientCoinException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(Match match){
        match.setErrorMessage("You don't have enough coins");
        match.notifyMatchObservers();
        match.setErrorMessage("");
    }
}
