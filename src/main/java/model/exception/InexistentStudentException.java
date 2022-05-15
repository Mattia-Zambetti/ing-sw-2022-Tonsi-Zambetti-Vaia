package model.exception;

import model.*;

public class InexistentStudentException extends Exceptions{
    public InexistentStudentException(String errorMessage){
        super(errorMessage);
    }
}
