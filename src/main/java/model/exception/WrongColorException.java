package model.exception;

import model.*;

public class WrongColorException extends Exceptions{
    public WrongColorException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(Match match){
        this.printStackTrace();
    }
}
