package view.choice;

import model.ExpertMatch;
import model.Match;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.Princess;
import view.RemoteView;

public class PrincessChoice extends FigureCardWithStudentsChoice{
    @Override
    public boolean setChoiceParam(String input) {
        //TODO
        return false;
    }

    @Override
    public void manageUpdate(Match match, RemoteView remoteView) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException {
        ((ExpertMatch)match).takeStudentsOnFigureCard(this.getChosenStudents(), (Princess) this.getFigureCardPlayed());
    }
}
