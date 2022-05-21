package controller.choice;

import model.Match;
import model.MatchDataInterface;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;

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
            completed = true;
            return false;
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMoreBlockCardsException, NoIslandException, SameInfluenceException, NoMoreTowerException, TowerIDAlreadyExistingException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException, FinishedGameIslandException {
        match.moveMotherNature(this.getMovement());
    }

    @Override
    public String toString(MatchDataInterface match) {
        return "Insert the number of mother nature movement you want to do: " ;
    }
}
