package view.choice;

import model.Match;
import model.TowerColor;
import model.Wizard;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;
import view.RemoteView;

public class DataPlayerChoice extends Choice{


    private int wizard, numChoice = 0;
    private int counterTowers = 0;
    private int towerColor;
    private int numPlayers;
    private String nickname;


    public DataPlayerChoice(int numPlayers, String nickname){
        this.numPlayers = numPlayers;
        if (numPlayers == 4)
            counterTowers = 1;
        this.nickname=nickname;

    }



    @Override
    public boolean setChoiceParam(String input) {

        switch (numChoice) {
            case 0:
                if(isItAnInt(input)){
                    if(Integer.parseInt(input)>0 && Integer.parseInt(input) < TowerColor.values().length && TowerColor.values()[Integer.parseInt(input)-1].getCounter()<=counterTowers) {
                        towerColor = Integer.parseInt(input);
                        TowerColor.values()[Integer.parseInt(input)-1].counterplus();
                        numChoice++;
                        StringBuilder tmp = new StringBuilder("\nChoose your Wizard: ");
                        int counter=1;
                        for (Wizard w: Wizard.values()){
                            if(w.getCounter() == 0){
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
                        wizard = Integer.parseInt(input);
                        Wizard.values()[Integer.parseInt(input)-1].counterplus();
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
        match.addPlayer(nickname,TowerColor.values()[towerColor-1].toString(), Wizard.values()[wizard-1].toString());
    }



    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("Choose your tower color:");
        int counter = 1;
        if (numPlayers == 2 || numPlayers == 4) {
            for (TowerColor t : TowerColor.values()) {
                if (!t.equals(TowerColor.GREY)) {
                    tmp.append("\n" + counter + ") " + t.toString());
                    counter++;
                }
            }
        } else if (numPlayers == 3) {
            for (TowerColor t : TowerColor.values()) {
                tmp.append("\n" + counter + ") " + t.toString());
                counter++;
            }
        }
        return tmp.toString();

    }
}
