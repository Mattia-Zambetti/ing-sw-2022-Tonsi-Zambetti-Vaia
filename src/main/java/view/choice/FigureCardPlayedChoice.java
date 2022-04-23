package view.choice;

import model.figureCards.FigureCard;

public class FigureCardPlayedChoice implements Choice{
    private FigureCard chosenFigureCard;

    public FigureCardPlayedChoice(){

    }

    public void setChosenFigureCard(FigureCard chosenFigureCard) {
        this.chosenFigureCard = chosenFigureCard;
    }

    public FigureCard getChosenFigureCard() {
        return chosenFigureCard;
    }
}
