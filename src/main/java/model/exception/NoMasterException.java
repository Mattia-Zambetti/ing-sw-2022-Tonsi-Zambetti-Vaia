package model.exception;

import model.*;

public class NoMasterException extends Exceptions{
    public NoMasterException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(Match match){
        this.printStackTrace();
    }
}
