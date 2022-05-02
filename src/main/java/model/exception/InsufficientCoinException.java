//Zambo
package model.exception;

import view.RemoteView;
import view.choice.Choice;

public class InsufficientCoinException extends Exceptions{
    public InsufficientCoinException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(RemoteView remoteView, Choice choice){
        remoteView.sendError("You don't have enough coins");
    }
}
