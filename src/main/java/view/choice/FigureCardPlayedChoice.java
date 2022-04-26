package view.choice;

import model.figureCards.FigureCard;

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
}
