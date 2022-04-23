package view.choice;

import model.FigureCards.FigureCard;

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
