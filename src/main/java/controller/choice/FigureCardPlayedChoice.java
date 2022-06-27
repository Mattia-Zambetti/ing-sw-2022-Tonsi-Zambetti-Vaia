package controller.choice;

import model.ExpertMatch;
import model.Match;
import model.MatchDataInterface;
import model.exception.*;
import model.figureCards.FigureCard;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;

import java.util.ArrayList;
import java.util.List;

public class FigureCardPlayedChoice extends Choice{
    private int chosenFigureCard;
    private final List<FigureCard> figureCardsList;
    public FigureCardPlayedChoice(List<FigureCard> currentFigureCards){
        this.figureCardsList =new ArrayList<>();
        for (FigureCard f: currentFigureCards) {
            figureCardsList.add(f);
        }
    }

    public void setChosenFigureCard(int cardIdFigureCard) {
        this.chosenFigureCard = cardIdFigureCard;
    }

    public FigureCard getChosenFigureCard() {
        return figureCardsList.get(chosenFigureCard);
    }

    @Override
    public String toString(MatchDataInterface match) {
        StringBuilder tmp = new StringBuilder();
        int counter = 1;
        tmp.append("Choose a figure card by inserting its card id: ");
        for (FigureCard f : figureCardsList){
            tmp.append("\n"+counter+") "+ f.toString());
            counter++;
        }
        return tmp.toString();
    }

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)) {
            if(Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= 3)
                setChosenFigureCard(Integer.parseInt(input)-1);
            else{
                System.out.println(getRedString("Please insert an existing character card"));
                return true;
            }
            completed = true;
            return false;
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMasterException, WrongColorException {
        //Control on type of match needed
        ((ExpertMatch)match).playFigureCard(this.getChosenFigureCard());
    }

    public String whichChoicePhase(){
        return "It's the figure card played choice phase";
    }
}
