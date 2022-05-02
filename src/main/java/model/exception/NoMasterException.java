package model.exception;

import view.RemoteView;
import view.choice.Choice;

public class NoMasterException extends Exceptions{
    public NoMasterException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(RemoteView remoteView, Choice choice){
        this.printStackTrace();
    }
}
