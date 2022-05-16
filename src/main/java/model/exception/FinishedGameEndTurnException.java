package model.exception;

import model.Match;

public class FinishedGameEndTurnException extends FinishedGameExceptions{

    public FinishedGameEndTurnException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public void manageException(Match match) {
        //match.calculateWinner();
        match.notifyEndMatch();
    }
}
