package controller.choice;

import model.Color;
import model.ExpertMatch;
import model.Match;
import model.MatchDataInterface;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;

public class ThiefChoice extends FigureCardActionChoice{
    private Color chosenColor;
    

    public void setChosenColor(int chosenColor){
        this.chosenColor = Color.values()[chosenColor];
    }

    @Override
    public boolean setChoiceParam(String input) {
        try {
            if(isItAnInt(input))
                setChosenColor(Integer.parseInt(input) - 1);
            completed = true;
            return false;
        }catch (IndexOutOfBoundsException e){
            System.out.println(getRedString("Colour not found, ")+retryMessage());
            return true;
        }
    }

    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMasterException {
        ((ExpertMatch)match).removeStudentsPerColor(chosenColor,3);
    }

    @Override
    public String toString(MatchDataInterface match) {
        StringBuilder tmp = new StringBuilder();
        int counter = 1;
        tmp.append("Please choose the color: ");
        for (Color c: Color.values()){
            tmp.append("\n"+ counter + ") "+c.toString());
            counter++;
        }
        return tmp.toString();
    }
}
