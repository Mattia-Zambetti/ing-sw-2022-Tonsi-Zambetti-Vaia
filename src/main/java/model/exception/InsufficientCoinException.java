//Zambo
package model.exception;

import model.*;

public class InsufficientCoinException extends Exceptions{
    public InsufficientCoinException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(Match match){
        //remoteView.sendError("You don't have enough coins");
    }
}
