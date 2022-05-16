package model.exception;

import model.*;

public class WrongColorException extends Exceptions{
    private static int idPossessor=0;

    public WrongColorException(String errorMessage){
        super(errorMessage);
    }

    public void manageException(Match match){
        this.printStackTrace();
    }

    public static void setIdPossessor(int idPossessor) {
        WrongColorException.idPossessor = idPossessor;
    }

    public int getIdPossessor() {
        return idPossessor;
    }
}
