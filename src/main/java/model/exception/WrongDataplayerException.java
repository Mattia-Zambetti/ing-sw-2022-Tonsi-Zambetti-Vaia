package model.exception;

import model.*;

public class WrongDataplayerException extends Exceptions {


    public WrongDataplayerException(String errorMessage) {
        super(errorMessage);
    }

    public void manageException(Match match){
        super.manageException(match);
    }
}
