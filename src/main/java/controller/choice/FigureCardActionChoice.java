package controller.choice;

import model.figureCards.FigureCard;

public abstract class FigureCardActionChoice extends Choice{
    private FigureCard figureCardPlayed;

    /*public FigureCard getFigureCardPlayed() {
        return figureCardPlayed;
    }*/ //NEVER USED

    /*public void setFigureCardPlayed(FigureCard figureCardPlayed) {
        this.figureCardPlayed = figureCardPlayed;
    }*/ //NEVER USED

    @Override
    public String whichChoicePhase() {
        return "generic figure card";
    }
}
