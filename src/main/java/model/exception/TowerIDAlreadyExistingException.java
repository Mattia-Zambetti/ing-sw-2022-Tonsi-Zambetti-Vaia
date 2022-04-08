package model.exception;

public class TowerIDAlreadyExistingException extends Exception{
    public TowerIDAlreadyExistingException(String errorMessage){
        super(errorMessage);
    }
}
