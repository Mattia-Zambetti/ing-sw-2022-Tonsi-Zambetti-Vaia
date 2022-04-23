package view.choice;

import model.Color;

public class MushroomCollectorChoice extends FigureCardActionChoice{
    private Color blockedColor;

    public Color getBlockedColor() {
        return blockedColor;
    }

    public void setBlockedColor(Color blockedColor) {
        this.blockedColor = blockedColor;
    }
}
