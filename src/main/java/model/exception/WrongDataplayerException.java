package model.exception;

import model.*;

public class WrongDataplayerException extends Exceptions {

    private static int idPossessor=0;

    public WrongDataplayerException(String errorMessage) {
        super(errorMessage);
    }

    public void manageException(Match match){
        super.manageException(match);
    }

    public static void setIdPossessor(int idPossessor) {
        WrongDataplayerException.idPossessor = idPossessor;
    }

    public int getIdPossessor() {
        return idPossessor;
    }
}
