package view.choice;

import model.ExpertMatch;
import model.Match;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.Merchant;
import view.RemoteView;

public class MerchantChoice extends FigureCardWithStudentsChoice {
    private int chosenIslandID;

    public int getChosenIslandID() {
        return chosenIslandID;
    }

    public void setChosenIslandID(int chosenIslandID) {
        this.chosenIslandID = chosenIslandID;
    }

    @Override
    public boolean setChoiceParam(String input) {
        //TODO
        return false;
    }

    @Override
    public void manageUpdate(Match match, RemoteView remoteView) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException {
        ((ExpertMatch)match).takeStudentsOnFigureCard(this.getChosenStudents(), (Merchant) this.getFigureCardPlayed(),this.getChosenIslandID());

    }
}
