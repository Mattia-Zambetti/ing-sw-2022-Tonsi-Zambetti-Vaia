//Tonsi
package model.exception;

import model.*;

public class CardNotFoundException extends Exceptions{
    public CardNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
