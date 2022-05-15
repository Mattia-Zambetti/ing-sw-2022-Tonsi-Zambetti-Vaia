package controller.choice;

import model.ExpertMatch;
import model.Match;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;

public class GrannyGrassChoice extends FigureCardActionChoice{
    private int blockedIslanID;

    public int getBlockedIslanID() {
        return blockedIslanID;
    }

    public void setBlockedIslanID(int blockedIslanID) {
        this.blockedIslanID = blockedIslanID;
    }

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)) {
            setBlockedIslanID(Integer.parseInt(input));
            return false;
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMoreBlockCardsException, NoIslandException {
        ((ExpertMatch)match).placeForbiddenCards(this.getBlockedIslanID());
    }
}
