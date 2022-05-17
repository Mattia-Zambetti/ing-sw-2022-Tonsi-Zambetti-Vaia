package model.exception;

import model.*;

public class NoMoreTowerException extends FinishedGameExceptions{
    public NoMoreTowerException(String errorMessage){
        super(errorMessage);
    }

    @Override
    public void manageException(Match match) {
        match.setCurrentPlayerWinners();
        match.notifyEndMatch();
    }
}
