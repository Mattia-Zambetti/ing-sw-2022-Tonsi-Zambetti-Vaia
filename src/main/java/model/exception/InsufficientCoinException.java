//Zambo
package model.exception;

public class InsufficientCoinException extends Exception{
    public InsufficientCoinException(String errorMessage){
        super(errorMessage);
    }
}
