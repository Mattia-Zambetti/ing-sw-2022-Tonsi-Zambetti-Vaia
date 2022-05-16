package model.exception;

import model.*;

public class NoMoreStudentsException extends FinishedGameExceptions {


    public NoMoreStudentsException(String s) {
        super(s);
    }

    @Override
    public void manageException(Match match) {
        match.setMatchFinishedAtEndOfRound();
    }
}
