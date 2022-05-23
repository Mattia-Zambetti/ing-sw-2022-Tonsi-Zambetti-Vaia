package controller.choice;

import model.ExpertMatch;
import model.Match;
import model.MatchDataInterface;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;

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
                System.out.println(getRedString("Island not found, try again:"));
                return true;
            }
            completed = true;
            return false;
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMoreBlockCardsException, NoIslandException {
        ((ExpertMatch)match).placeForbiddenCards(this.getBlockedIslanID());
    }


    public String toString(MatchDataInterface match){
        StringBuilder tmp = new StringBuilder();
        islandPositionSize = match.getIslandPositions().size();
        tmp.append("Choose the Island you want to block: ");
        for(int i = 0;i < match.getIslandPositions().size(); i++){
            tmp.append("\n"+ i + ") island");
        }

        return tmp.toString();
    }
}
