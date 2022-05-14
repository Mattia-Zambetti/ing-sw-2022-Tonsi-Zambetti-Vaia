package view.choice;

import model.Match;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;

import java.io.Serializable;

public abstract class Choice implements Serializable {

    private static final long serialVersionUID =445345454;

    public abstract boolean setChoiceParam(String input);

    public abstract void manageUpdate(Match match) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMoreBlockCardsException, NoIslandException, SameInfluenceException, WrongDataplayerException, NoMasterException, NegativeNumberOfTowerException, TowerIDAlreadyExistingException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException, FinishedGameIslandException, CardAlreadyPlayedException;


    public boolean isItAnInt(String input){
        try{
            Integer.parseInt(input);
            return true;
        }catch (NumberFormatException e){
            System.out.println("That isn't a number, please try again: ");
            return false;
        }
    }

    public String retryMessage(){
        return  "please try again";
    }
}
