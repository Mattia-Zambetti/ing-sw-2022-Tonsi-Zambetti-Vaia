package view.choice;

import model.Match;
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
    public String toString() {
        return "Insert cloud number you want to take: ";
    }

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input))
            setChosenCloudID(Integer.parseInt(input));
        else return true;
        return false;
    }

    @Override
    public void manageUpdate(Match match) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, NoMoreStudentsException {
        match.moveStudentsFromCloudToEntrance(this.getChosenCloudID());
    }
}
