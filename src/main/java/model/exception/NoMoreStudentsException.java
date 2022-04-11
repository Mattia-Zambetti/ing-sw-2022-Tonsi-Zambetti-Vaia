package model.exception;

public class NoMoreStudentsException extends Exception {
    private static boolean endTurn=false;


    public NoMoreStudentsException(String s) {
        super(s);
        endTurn=true;
    }

    public static boolean isEndTurn() {
        return endTurn;
    }
}
