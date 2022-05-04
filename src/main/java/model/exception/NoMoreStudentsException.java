package model.exception;

import model.*;

public class NoMoreStudentsException extends FinishedGameExceptions {
    private static boolean endTurn=false;


    public NoMoreStudentsException(String s) {
        super(s);
        endTurn=true;
    }

    public static boolean isEndTurn() {
        return endTurn;
    }
}
