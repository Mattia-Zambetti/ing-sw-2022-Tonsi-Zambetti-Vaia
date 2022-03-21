package model;

public class WrongColorException extends Exception{
    public WrongColorException(String errorMessage){
        super(errorMessage);
    }
}
