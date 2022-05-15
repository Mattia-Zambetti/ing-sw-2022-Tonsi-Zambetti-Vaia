package model.exception;

import model.*;

public class StudentIDAlreadyExistingException extends Exceptions{
    public StudentIDAlreadyExistingException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(Match match){
        this.printStackTrace();
    }
}