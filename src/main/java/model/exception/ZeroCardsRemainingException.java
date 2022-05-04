package model.exception;

import model.*;

public class ZeroCardsRemainingException extends Exception {
    public ZeroCardsRemainingException(String errorMessage){
        super(errorMessage);
    }
}
