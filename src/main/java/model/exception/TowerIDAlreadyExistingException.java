package model.exception;

import model.*;

public class TowerIDAlreadyExistingException extends PrintErrorExceptions{
    public TowerIDAlreadyExistingException(String errorMessage){
        super(errorMessage);
    }
}
