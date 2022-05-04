package model.exception;

import model.*;

public class SameInfluenceException extends Exceptions{
    public SameInfluenceException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(Match match){
        //remoteView.sendError(this.getMessage());
    }
}
