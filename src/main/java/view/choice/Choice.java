package view.choice;

import model.Match;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;
import view.RemoteView;

import java.io.Serializable;

public abstract class Choice implements Serializable {

    public abstract boolean setChoiceParam(String input);

    public abstract void manageUpdate(Match match, RemoteView remoteView) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMoreBlockCardsException, NoIslandException, SameInfluenceException;


    public boolean isItAnInt(String input){
        try{
            Integer.parseInt(input);
            return true;
        }catch (NumberFormatException e){
            System.out.println("That isn't a number, please try again: ");
            return false;
        }
    }
}
