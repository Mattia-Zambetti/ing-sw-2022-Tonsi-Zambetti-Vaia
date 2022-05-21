package controller.choice;

import model.Match;
import model.MatchDataInterface;
import model.exception.*;

public class CloudChoice extends Choice{
    private int chosenCloudID;

    public CloudChoice(){
    }

    public int getChosenCloudID() {
        return chosenCloudID;
    }

    public void setChosenCloudID(int chosenCloudID) {
        this.chosenCloudID = chosenCloudID;
    }

    @Override
    public String toString(MatchDataInterface match) {
        return "Insert cloud number you want to take: ";
    }

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input))
            setChosenCloudID(Integer.parseInt(input));
        else return true;
        completed = true;
        return false;
    }

    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, NoMoreStudentsException, FinishedGameEndTurnException {
        match.moveStudentsFromCloudToEntrance(this.getChosenCloudID());
    }
}
