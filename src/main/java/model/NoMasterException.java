package model;

public class NoMasterException extends Exception{
    public NoMasterException(String errorMessage){
        super(errorMessage);
    }
}
