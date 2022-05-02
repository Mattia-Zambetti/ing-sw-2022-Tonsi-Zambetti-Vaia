package view.choice;

import model.Match;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;
import view.RemoteView;

public class MoveMotherNatureChoice extends Choice{
    private int movement;

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)) {
            setMovement(Integer.parseInt(input));
            return false;
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match, RemoteView remoteView) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMoreBlockCardsException, NoIslandException, SameInfluenceException, NegativeNumberOfTowerException, TowerIDAlreadyExistingException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException, FinishedGameIslandException {
        match.moveMotherNature(this.getMovement());
    }

    @Override
    public String toString() {
        return "Insert the number of mother nature movement you want to do: " ;
    }
}
