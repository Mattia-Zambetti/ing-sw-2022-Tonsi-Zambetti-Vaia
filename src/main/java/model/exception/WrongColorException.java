package model.exception;

import view.RemoteView;
import view.choice.Choice;

public class WrongColorException extends Exceptions{
    public WrongColorException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(RemoteView remoteView, Choice choice){
        this.printStackTrace();
    }
}
