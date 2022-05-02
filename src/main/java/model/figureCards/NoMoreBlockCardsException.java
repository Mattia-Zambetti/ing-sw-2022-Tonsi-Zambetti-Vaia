package model.figureCards;

import model.exception.Exceptions;
import view.RemoteView;
import view.choice.Choice;

public class NoMoreBlockCardsException extends Exceptions {
    public NoMoreBlockCardsException (String message) {super(message);}

    public void manageException(RemoteView remoteView, Choice choice){
        this.printStackTrace();
    }
}
