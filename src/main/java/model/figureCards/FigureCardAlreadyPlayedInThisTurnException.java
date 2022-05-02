package model.figureCards;

import model.exception.Exceptions;
import view.RemoteView;
import view.choice.Choice;

public class FigureCardAlreadyPlayedInThisTurnException extends Exceptions {
    public FigureCardAlreadyPlayedInThisTurnException(String message) {
        super(message);
    }

    public void manageException(RemoteView remoteView, Choice choice){
        this.printStackTrace();
    }
}
