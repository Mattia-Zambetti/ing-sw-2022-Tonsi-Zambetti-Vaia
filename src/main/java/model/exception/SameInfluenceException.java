package model.exception;

import view.RemoteView;
import view.choice.Choice;

public class SameInfluenceException extends Exceptions{
    public SameInfluenceException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(RemoteView remoteView, Choice choice){
        remoteView.sendError(this.getMessage());
    }
}
