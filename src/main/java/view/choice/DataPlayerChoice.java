package view.choice;

import model.Match;
import model.TowerColor;
import model.Wizard;
import model.exception.*;

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

    public void setWizardChoice(ArrayList<Wizard> tmp) {
        this.wizardChoice = tmp;
    }


    @Override
    public boolean setChoiceParam(String input) {

        switch (numChoice) {
            case 0:
                if(isItAnInt(input)){
                    if(Integer.parseInt(input)>0 && Integer.parseInt(input) < towersChoiceTmp.size()+1) {
                        towerColor = towersChoiceTmp.get(Integer.parseInt(input)-1).ordinal();
                        towersChoiceTmp.remove(Integer.parseInt(input)-1);
                        numChoice++;
                        StringBuilder tmp = new StringBuilder("\nChoose your Wizard: ");
                        int counter=1;
                        for (Wizard w: wizardChoiceTmp){
                                tmp.append("\n" + counter + ") " + w.toString());
                                counter++;
                        }
                        System.out.println(tmp);
                    }
                    else
                        System.out.println("Error try again with another number");
                }
                return true;
            case 1:
                if(isItAnInt(input)){
                    if(Integer.parseInt(input)>0 && Integer.parseInt(input) < wizardChoiceTmp.size() + 1) {
                        wizard = wizardChoiceTmp.get(Integer.parseInt(input)-1).ordinal();
                        wizardChoiceTmp.remove(Integer.parseInt(input)-1);
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
    public void manageUpdate(Match match) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoIslandException, SameInfluenceException, WrongDataplayerException {
        setTowerChoice(towersChoiceTmp);
        setWizardChoice(wizardChoiceTmp);
        match.addPlayer(nickname,TowerColor.values()[towerColor].toString(), Wizard.values()[wizard].toString());
    }



    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("Choose your tower color:");
        int counter = 1;
        if (numPlayers == 2 || numPlayers == 4) {
            towersChoiceTmp.remove(TowerColor.GREY);
            for (TowerColor t : towersChoiceTmp) {
                    tmp.append("\n" + counter + ") " + t.toString());
                    counter++;
            }

        } else if (numPlayers == 3) {
            for (TowerColor t : towersChoiceTmp) {
                    tmp.append("\n" + counter + ") " + t.toString());
                    counter++;
            }
        }
        return tmp.toString();

    }
}
