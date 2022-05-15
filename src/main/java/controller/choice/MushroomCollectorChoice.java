package controller.choice;

import model.Color;
import model.ExpertMatch;
import model.Match;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;

public class MushroomCollectorChoice extends FigureCardActionChoice{
    private Color blockedColor;

    public Color getBlockedColor() {
        return blockedColor;
    }

    public void setBlockedColor(String blockedColor){
        this.blockedColor = Color.valueOf(blockedColor);
    }

    @Override
    public boolean setChoiceParam(String input) {
        try {
            setBlockedColor(input);
            return false;
        }catch (IllegalArgumentException e){
            System.out.println("You must choose one of this color: ");
            for (Color c: Color.values()){
                System.out.println(" - "+c.toString());
            }
            return true;
        }
    }

    @Override
    public void manageUpdate(Match match) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException {
        ((ExpertMatch)match).blockColorForInfluence(this.getBlockedColor());
    }
}
