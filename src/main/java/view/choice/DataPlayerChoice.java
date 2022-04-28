package view.choice;

import model.Match;
import model.TowerColor;
import model.Wizard;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;
import view.RemoteView;

import java.util.ArrayList;

public class DataPlayerChoice extends Choice{


    private int wizard, numChoice = 0;
    private int counterTowers = 0;
    private int towerColor;
    private int numPlayers;
    private  ArrayList<Wizard> wizards = new ArrayList<Wizard>();
    private String nickname;
    private  static ArrayList<TowerColor> towersChoice = new ArrayList<TowerColor>();
    private  static ArrayList<Wizard> wizardChoice = new ArrayList<Wizard>();
    private   ArrayList<TowerColor> towersChoiceTmp = new ArrayList<TowerColor>();
    private   ArrayList<Wizard> wizardChoiceTmp = new ArrayList<Wizard>();
    private static int initialized = 0;

    public DataPlayerChoice(int numPlayers, String nickname){
        this.numPlayers = numPlayers;
        if (numPlayers == 4)
            counterTowers = 1;
        this.nickname=nickname;
        if(initialized == 0){
            for (int i = 0; i < TowerColor.values().length; i++)
                towersChoice.add(TowerColor.values()[i]);
            for (int i = 0; i < Wizard.values().length; i++)
                wizardChoice.add(Wizard.values()[i]);
            initialized = 1;
        }
        towersChoiceTmp = towersChoice;
        wizardChoiceTmp = wizardChoice;
    }

    public void setTowerChoice(ArrayList<TowerColor> tmp){
        this.towersChoice = tmp;
    }

    public void removeWizard(Wizard wizard){
        wizardChoice.remove(wizard);
    }

    @Override
    public boolean setChoiceParam(String input) {

        switch (numChoice) {
            case 0:
                if(isItAnInt(input)){
                    if(Integer.parseInt(input)>0 && Integer.parseInt(input) < TowerColor.values().length && TowerColor.values()[Integer.parseInt(input)-1].getCounter()<=counterTowers) {
                        towerColor = towersChoice.get(Integer.parseInt(input)-1).ordinal();
                        towersChoice.get(Integer.parseInt(input)-1).counterplus();
                        towersChoice.remove(Integer.parseInt(input)-1);
                        numChoice++;
                        StringBuilder tmp = new StringBuilder("\nChoose your Wizard: ");
                        int counter=1;
                        for (Wizard w: Wizard.values()){
                            if(w.getCounter() == 0){
                                wizardChoice.add(w);
                                tmp.append("\n" + counter + ") " + w.toString());
                                counter++;
                            }
                        }
                        System.out.println(tmp);
                    }
                    else
                        System.out.println("Error try again with another number");
                }
                return true;
            case 1:
                if(isItAnInt(input)){
                    if(Integer.parseInt(input)>0 && Integer.parseInt(input) < Wizard.values().length && Wizard.values()[Integer.parseInt(input)-1].getCounter() == 0) {
                        wizard = wizardChoice.get(Integer.parseInt(input)-1).ordinal();
                        wizardChoice.get(Integer.parseInt(input)-1).counterplus();
                        System.out.println("Wait now the other players...");
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
    public void manageUpdate(Match match, RemoteView remoteView) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMoreBlockCardsException, NoIslandException, SameInfluenceException, WrongDataplayerException {
        setTowerChoice(towersChoiceTmp);
        match.addPlayer(nickname,TowerColor.values()[towerColor].toString(), Wizard.values()[wizard].toString());
    }



    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("Choose your tower color:");
        int counter = 1;
        if (numPlayers == 2 || numPlayers == 4) {
            towersChoice.remove(TowerColor.GREY);
            for (TowerColor t : towersChoice) {
                    tmp.append("\n" + counter + ") " + t.toString() + " " + t.getCounter());
                    counter++;
            }

        } else if (numPlayers == 3) {
            for (TowerColor t : TowerColor.values()) {
                if(t.getCounter() == 0){
                    towersChoice.add(t);
                    tmp.append("\n" + counter + ") " + t.toString());
                    counter++;
                }
            }
        }
        return tmp.toString();

    }
}
