package model.exception;

import view.RemoteView;
import view.choice.Choice;

public abstract class Exceptions extends Exception{
    public Exceptions(String errorMessage) { super(errorMessage);}

    public void manageException(RemoteView remoteView, Choice choice){
        remoteView.sendError(this.getMessage());
        remoteView.choiceUser(choice);
    }
}
