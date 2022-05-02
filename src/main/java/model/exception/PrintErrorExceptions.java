package model.exception;

import view.RemoteView;
import view.choice.Choice;

public abstract class PrintErrorExceptions extends Exceptions{
    public PrintErrorExceptions(String errorMessage) { super(errorMessage);}

    public void manageException(RemoteView remoteView, Choice choice){
        this.printStackTrace();
    }
}
