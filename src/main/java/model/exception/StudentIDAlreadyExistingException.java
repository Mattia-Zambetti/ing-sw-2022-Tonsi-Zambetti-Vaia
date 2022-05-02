package model.exception;

import view.RemoteView;
import view.choice.Choice;

public class StudentIDAlreadyExistingException extends Exceptions{
    public StudentIDAlreadyExistingException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(RemoteView remoteView, Choice choice){
        this.printStackTrace();
    }
}