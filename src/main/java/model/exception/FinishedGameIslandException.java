package model.exception;

import model.*;

public class FinishedGameIslandException extends FinishedGameExceptions{
    public FinishedGameIslandException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public void manageException(Match match) {
        match.setCurrentPlayerWinners();
        match.notifyEndMatch();
    }
}
