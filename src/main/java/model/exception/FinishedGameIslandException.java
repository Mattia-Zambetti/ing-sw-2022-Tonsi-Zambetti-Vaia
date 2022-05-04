package model.exception;

import model.*;

public class FinishedGameIslandException extends FinishedGameExceptions{
    public FinishedGameIslandException(String errorMessage) {
        super(errorMessage);
    }
}
