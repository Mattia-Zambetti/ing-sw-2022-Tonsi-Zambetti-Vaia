package model.exception;

public class NoMoreCardException extends FinishedGameExceptions {
    private static boolean endTurn=false;
    public NoMoreCardException(String s) {
        super(s);
    }
    public static boolean isEndTurn() {
        return endTurn;
    }
}
