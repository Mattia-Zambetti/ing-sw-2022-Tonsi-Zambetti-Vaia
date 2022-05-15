package view.choice;

import model.ExpertMatch;
import model.Match;
import model.exception.*;
import model.figureCards.FigureCard;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FigureCardPlayedChoice extends Choice{
    private int chosenFigureCard;
    private final Map<Integer, FigureCard> figureCardsMap;
    public FigureCardPlayedChoice(List<FigureCard> currentFigureCards){
        this.figureCardsMap=new HashMap<>();
        for (FigureCard f: currentFigureCards) {
            figureCardsMap.put(f.hashCode(),f);
        }
    }

    public void setChosenFigureCard(int cardIdFigureCard) {
        this.chosenFigureCard = cardIdFigureCard;
    }

    public FigureCard getChosenFigureCard() {
        return figureCardsMap.get(chosenFigureCard);
    }

    @Override
    public String toString() {
        return "Choose a figure card by inserting its card id: ";
    }

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)) {
            setChosenFigureCard(Integer.parseInt(input));
            return false;
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException {
        ((ExpertMatch)match).playFigureCard(this.getChosenFigureCard());
    }
}
