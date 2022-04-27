package view.choice;

import model.Match;
import model.*;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;
import view.RemoteView;

public class DataPlayerChoice extends Choice{

    String name;
    int wizard, numChoice = 0,counterTowers = 0,towerColor,numPlayers;

    public DataPlayerChoice(int numPlayers){
        this.numPlayers = numPlayers;
        if (numPlayers == 4)
            counterTowers = 1;
    }

    @Override
    public boolean setChoiceParam(String input) {
        switch (numChoice){
            case 0:
                name = input;
                StringBuilder tmp = new StringBuilder("Choose your tower color:");
                int counter = 1;
                if(numPlayers == 2 || numPlayers == 4){
                    for (TowerColor t:TowerColor.values()) {
                        if(!t.equals(TowerColor.GREY))
                            if(t.getCounter() <= counterTowers){
                                tmp.append("\n" + counter + ") " + t.toString());
                                counter++;
                            }

                    }
                }
                else if (numPlayers == 3){
                        for (TowerColor t:TowerColor.values()) {
                            if(t.getCounter() == 0){
                                tmp.append("\n" + counter + ") " + t.toString());
                                counter++;
                            }
                    }
                }
                numChoice++;
                System.out.println(tmp);
                return true;
            case 1:
                if(isItAnInt(input)){
                    if(Integer.parseInt(input) <= TowerColor.values().length && TowerColor.values()[Integer.parseInt(input)].getCounter() <= counterTowers) {
                        towerColor = Integer.parseInt(input);
                        TowerColor.values()[Integer.parseInt(input)].counterplus();
                    }
                    else{
                        System.out.println("Error try again with another number");
                        return true;
                    }
                }
                else
                    return true;
                tmp = new StringBuilder("\nChoose your Wizard: ");
                counter = 1;
                for (Wizard w: Wizard.values()){
                    if(w.getCounter() == 0){
                        tmp.append("\n" + counter + ") " + w.toString());
                        counter++;
                    }
                }
                System.out.println(tmp);
                numChoice++;
                return false;
        }
        if(isItAnInt(input)){
            if(Integer.parseInt(input) <= Wizard.values().length && Wizard.values()[Integer.parseInt(input)].getCounter() == 0) {
                wizard = Integer.parseInt(input);
                Wizard.values()[Integer.parseInt(input)].counterplus();
            }
            else{
                System.out.println("Error try again with another number");
                return true;
            }
        }
        else
            return true;
        return false;
    }

    @Override
    public void manageUpdate(Match match, RemoteView remoteView) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMoreBlockCardsException, NoIslandException, SameInfluenceException {
    }

    public Player getPlayer(){
        return new Player(name);
    }

    @Override
    public String toString(){
        return  "Insert your name: ";
    }
}
