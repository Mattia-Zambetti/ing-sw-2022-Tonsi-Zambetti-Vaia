package view.choice;

import model.figureCards.FigureCard;

public abstract class FigureCardActionChoice implements Choice{
    private FigureCard figureCardPlayed;

    public FigureCard getFigureCardPlayed() {
        return figureCardPlayed;
    }

    public void setFigureCardPlayed(FigureCard figureCardPlayed) {
        this.figureCardPlayed = figureCardPlayed;
    }
}
