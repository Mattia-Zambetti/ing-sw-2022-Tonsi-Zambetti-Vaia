package model.exception;

import model.*;

public class NegativeNumberOfTowerException extends FinishedGameExceptions{
    public NegativeNumberOfTowerException(String errorMessage){
        super(errorMessage);
    }

    @Override
    public void manageException(Match match) {
        match.setCurrentPlayerWinners();
        match.notifyEndMatch();
    }
}
