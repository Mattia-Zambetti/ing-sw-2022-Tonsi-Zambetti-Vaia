package controller.choice;

import model.ExpertMatch;
import model.Match;
import model.MatchDataInterface;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;

import java.util.ArrayList;
import java.util.List;

public class HeraldChoice extends FigureCardActionChoice{
    private int chosenIsland;
    private List<Integer> islandPositionTmp = new ArrayList<>();

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)) {
            if(islandPositionTmp.contains(Integer.parseInt(input)))
                chosenIsland = Integer.parseInt(input);
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
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMoreBlockCardsException, NoIslandException, NoMoreTowerException, TowerIDAlreadyExistingException, SameInfluenceException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException, FinishedGameIslandException {
        ((ExpertMatch)match).calculateInfluenceOnChosenIsland(chosenIsland);
    }


    public String toString(MatchDataInterface match){
        StringBuilder tmp = new StringBuilder();
        islandPositionTmp = match.getIslandPositions();
        tmp.append("Choose the Island where you want to calculate the influence: ");
        for(int i = 0;i < match.getIslandPositions().size(); i++){
            tmp.append("\n"+ match.getIslandPositions().get(i) + ") island");
        }
        return tmp.toString();
    }
}
