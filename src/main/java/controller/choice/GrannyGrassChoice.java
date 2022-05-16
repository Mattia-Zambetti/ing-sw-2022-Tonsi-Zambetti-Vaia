package controller.choice;

import model.ExpertMatch;
import model.Match;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;

import java.util.List;

public class GrannyGrassChoice extends FigureCardActionChoice{
    private int blockedIslanID, islandPositionSize;

    public int getBlockedIslanID() {
        return blockedIslanID;
    }

    public void setBlockedIslanID(int blockedIslanID) {
        this.blockedIslanID = blockedIslanID;
    }

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)) {
            if(Integer.parseInt(input) >= 0 && Integer.parseInt(input) < islandPositionSize)
            setBlockedIslanID(Integer.parseInt(input));
            else {
                System.out.println("Island not found, try again:");
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMoreBlockCardsException, NoIslandException {
        ((ExpertMatch)match).placeForbiddenCards(this.getBlockedIslanID());
    }

    @Override
    public String toString(){
        return "";
    }
    public String toString(List<Integer> islandPositions){
        StringBuilder tmp = new StringBuilder();
        islandPositionSize = islandPositions.size();
        tmp.append("Choose the Island you want to block: ");
        for(int i = 0;i < islandPositions.size(); i++)
            tmp.append(i + ") island");
        return "";
    }
}
