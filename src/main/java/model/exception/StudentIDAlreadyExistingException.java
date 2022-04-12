package model.exception;

public class StudentIDAlreadyExistingException extends Exception{
    public StudentIDAlreadyExistingException(String errorMessage){
        super(errorMessage);
    }
}