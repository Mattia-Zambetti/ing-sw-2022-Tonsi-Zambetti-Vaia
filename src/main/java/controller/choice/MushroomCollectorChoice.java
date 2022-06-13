package controller.choice;

import model.Color;
import model.ExpertMatch;
import model.Match;
import model.MatchDataInterface;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;

public class MushroomCollectorChoice extends FigureCardActionChoice{
    private Color blockedColor;

    public Color getBlockedColor() {
        return blockedColor;
    }

    public void setBlockedColor(int blockedColor){
        this.blockedColor = Color.values()[blockedColor];
    }

    @Override
    public boolean setChoiceParam(String input) {
        try {
            if(isItAnInt(input))
                setBlockedColor(Integer.parseInt(input) - 1);
            completed = true;
            return false;
        }catch (IndexOutOfBoundsException e){
            System.out.println(getRedString("Colour not found, ")+retryMessage());
            return true;
        }
    }

    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException {
        ((ExpertMatch)match).blockColorForInfluence(this.getBlockedColor());
    }

    @Override
    public String toString(MatchDataInterface match) {
        StringBuilder tmp = new StringBuilder();
        int counter = 1;
        tmp.append("Please choose the color you want to block: ");
        for (Color c: Color.values()){
            tmp.append("\n"+ counter + ") "+c.toString());
            counter++;
        }
        return tmp.toString();
    }

    public String whichChoicePhase(){
        return "Mushroom collector played from the current player";
    }
}
