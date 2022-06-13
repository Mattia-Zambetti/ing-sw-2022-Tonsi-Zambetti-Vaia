package controller.choice;

import model.ExpertMatch;
import model.Match;
import model.MatchDataInterface;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;

import java.util.ArrayList;
import java.util.List;

public class GrannyGrassChoice extends FigureCardActionChoice{
    private int blockedIslanID;
    private List<Integer> islandPositionTmp = new ArrayList<>();

    public int getBlockedIslanID() {
        return blockedIslanID;
    }

    public void setBlockedIslanID(int blockedIslanID) {
        this.blockedIslanID = blockedIslanID;
    }

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)) {
            if(islandPositionTmp.contains(Integer.parseInt(input)))
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
        islandPositionTmp = match.getIslandPositions();
        tmp.append("Choose the Island you want to block: ");
        for(int i = 0;i < match.getIslandPositions().size(); i++){
            tmp.append("\n"+ match.getIslandPositions().get(i) + ") island");
        }

        return tmp.toString();
    }

    public String whichChoicePhase(){
        return "Granny grass played from the current player";
    }
}

