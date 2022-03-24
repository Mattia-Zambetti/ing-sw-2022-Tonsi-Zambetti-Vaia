package model.exception;

public class InexistentStudentException extends Exception{
    public InexistentStudentException(String errorMessage){
        super(errorMessage);
    }
}
