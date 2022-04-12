package model.exception;

public class ZeroCardsRemainingException extends Exception {
    public ZeroCardsRemainingException(String errorMessage){
        super(errorMessage);
    }
}
