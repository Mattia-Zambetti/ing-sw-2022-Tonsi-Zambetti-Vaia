package view.choice;

import model.Match;
import model.TowerColor;
import model.Wizard;
import model.exception.*;

import java.util.ArrayList;
import java.util.Set;

public class DataPlayerChoice extends Choice{

    private String name;
    private int wizard, numChoice = 0;
    private int towerColor;

    @Override
    public boolean setChoiceParam(String input) {

        switch (numChoice) {
            case 0:
                name = input;
                numChoice++;
                return true;
            case 1:
                if(isItAnInt(input)){
                    if(Integer.parseInt(input)>0 && Integer.parseInt(input) <= TowerColor.values().length) {
                        towerColor = (Integer.parseInt(input)-1);
                        numChoice++;
                    }
                    else
                        System.out.println("Error try again with another number");
                }
                return true;
            case 2:
                if(isItAnInt(input)){
                    if(Integer.parseInt(input)>0 && Integer.parseInt(input) <= Wizard.values().length ) {
                        wizard = Integer.parseInt(input)-1;
                        return false;
                    }
                    else{
                        System.out.println("Error try again with another number");
                        return true;
                    }
                }
                else
                    return true;
        }

        return false;
    }

    @Override
    public void manageUpdate(Match match) {
        match.addPlayer(name,TowerColor.values()[towerColor].toString(), Wizard.values()[wizard].toString());
    }

    @Override
    public String toString() {

        String OutputString = switch (numChoice) {
            case 0 -> "Insert your nickname: ";
            case 1 -> "Insert your tower color: ";
            case 2 -> "Insert your wizard: ";
            default -> "";
        };

        return OutputString;
    }

}
