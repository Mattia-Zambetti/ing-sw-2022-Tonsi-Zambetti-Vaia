package model.exception;

public class WrongColorException extends Exception{
    public WrongColorException(String errorMessage){
        super(errorMessage);
    }
}
