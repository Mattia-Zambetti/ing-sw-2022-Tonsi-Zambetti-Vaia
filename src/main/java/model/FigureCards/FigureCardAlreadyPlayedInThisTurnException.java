package model.FigureCards;

public class FigureCardAlreadyPlayedInThisTurnException extends Exception {
    public FigureCardAlreadyPlayedInThisTurnException(String message) {
        super(message);
    }
}
