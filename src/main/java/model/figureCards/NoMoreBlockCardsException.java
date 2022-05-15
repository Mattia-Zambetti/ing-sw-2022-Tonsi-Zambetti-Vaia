package model.figureCards;

import model.exception.Exceptions;
import model.*;


public class NoMoreBlockCardsException extends Exceptions {
    public NoMoreBlockCardsException (String message) {super(message);}

    public void manageException(Match match){
        this.printStackTrace();
    }
}
