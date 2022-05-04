package view.choice;

import model.Match;
import model.Player;
import model.TowerColor;
import model.Wizard;

public class DataPlayerChoice extends Choice{

    private String name;
    private int wizard, numChoice = 0;
    private int towerColor;

    int playerNum;

    public DataPlayerChoice(int totalPlayersNum) {
        playerNum = totalPlayersNum;
    }

    @Override
    public boolean setChoiceParam(String input) {

        switch (numChoice) {
            case 0:
                name = input;
                numChoice++;
                return true;
            case 1:
                int maxNumberChoosableTowers;
                if(playerNum == 2 || playerNum == 4)
                    maxNumberChoosableTowers = 2;
                else
                    maxNumberChoosableTowers = 3;
                if(isItAnInt(input)){
                    if(Integer.parseInt(input)>0 && Integer.parseInt(input) <= maxNumberChoosableTowers) {
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

        StringBuilder OutputString = new StringBuilder();
        int counter = 1;

        switch (numChoice){
            case 0:
                OutputString.append("Insert your name:");
                break;
            case 1:
                OutputString.append("Choose your tower color: ");
                if (playerNum == 2 || playerNum == 4) {
                    for (TowerColor t : TowerColor.values()) {
                        if(!t.equals(TowerColor.GREY)){
                            OutputString.append("\n" + counter + ") " + t.toString());
                            counter++;
                        }
                    }
                } else if (playerNum == 3) {
                    for (TowerColor t : TowerColor.values()) {
                        OutputString.append("\n" + counter + ") " + t.toString());
                        counter++;
                    }
                }
                break;
            case 2:
                OutputString.append("Choose your wizard: ");
                for (Wizard w: Wizard.values()){
                    OutputString.append("\n" + counter + ") " + w.toString());
                    counter++;
                }
        }

        return OutputString.toString();
    }

    public Player getPlayer(){
        return new Player(name);
    }

}
