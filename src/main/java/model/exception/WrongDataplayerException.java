package model.exception;

import view.RemoteView;
import view.choice.Choice;

public class WrongDataplayerException extends Exceptions {
    public WrongDataplayerException(String errorMessage) {
        super(errorMessage);
    }

    public void manageException(RemoteView remoteView, Choice choice){
        this.printStackTrace();
    }
}
