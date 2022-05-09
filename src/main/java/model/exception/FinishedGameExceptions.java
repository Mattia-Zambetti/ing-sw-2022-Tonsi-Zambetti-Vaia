package model.exception;

import model.Player;
import view.RemoteView;
import view.choice.Choice;

import java.util.Map;

public abstract class FinishedGameExceptions extends Exception{
    public FinishedGameExceptions(String errorMessage) { super(errorMessage);}

    public void manageException(Map<Player,RemoteView> remoteViewMap){
        remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(this.getMessage())));
    }
}