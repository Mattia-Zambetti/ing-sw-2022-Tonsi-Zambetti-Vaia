package model.exception;

import model.*;

public abstract class PrintErrorExceptions extends Exceptions{
    public PrintErrorExceptions(String errorMessage) { super(errorMessage);}

    public void manageException(Match match){
        this.printStackTrace();
    }
}
