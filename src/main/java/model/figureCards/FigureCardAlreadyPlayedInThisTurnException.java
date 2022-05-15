package model.figureCards;

import model.exception.Exceptions;
import model.*;


public class FigureCardAlreadyPlayedInThisTurnException extends Exceptions {
    public FigureCardAlreadyPlayedInThisTurnException(String message) {
        super(message);
    }

    public void manageException(Match match){
        this.printStackTrace();
    }
}
