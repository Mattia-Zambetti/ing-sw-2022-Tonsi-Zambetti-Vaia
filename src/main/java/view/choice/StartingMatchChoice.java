package view.choice;

import model.Match;
import model.exception.CardNotFoundException;
import model.exception.MaxNumberException;
import model.exception.NoMoreCardException;
import model.exception.WrongCloudNumberException;
import view.RemoteView;

public class StartingMatchChoice extends Choice{
    private int totalPlayersNumMatch;
    private int matchType;
    private int numChoice=0;
    private final static int TOTAL_NUM_MATCH_TYPE=2;

    public int getMatchType() {
        return matchType;
    }

    public int getNumChoice() {
        return numChoice;
    }

    public void choicePlusPlus(){
        numChoice++;
    }

    public void setMatchType(int matchType) {
        this.matchType = matchType;
    }

    public int getTotalPlayersNumMatch() {
        return totalPlayersNumMatch;
    }

    public void setTotalPlayersNumMatch(int totalPlayersNumMatch) {
        this.totalPlayersNumMatch = totalPlayersNumMatch;
    }

    @Override
    public String toString() {
        return "You are the first player, choose the number of players(from 2 to 4): ";
    }

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)) {
            switch (getNumChoice()) {
                case 0:
                    if (Integer.parseInt(input) >= Match.getMINPLAYERSNUM() &&
                            Integer.parseInt(input) <= Match.getMAXPLAYERSNUM()) {
                        setTotalPlayersNumMatch(Integer.parseInt(input));
                        choicePlusPlus();
                        System.out.println("Insert the match type:\n" +
                                "1. Normal match\n" +
                                "2. Expert match");
                    } else
                        System.out.println("Error in the player number insertion, please give a number from 2 to 4");
                    return true;
                case 1:
                    if (Integer.parseInt(input) <= TOTAL_NUM_MATCH_TYPE && Integer.parseInt(input) > 0) {
                        setMatchType(Integer.parseInt(input));
                        System.out.println("Waiting other players... ");
                        return false;
                    } else
                        System.out.println("This match type doesn't exist, please try again :\n" +
                                "1. Normal match\n" +
                                "2. Expert match");


            }
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match, RemoteView remoteView) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException {
        //QUESTO IN ReALTA' NON DEVE ESSERE MAI CHIAMATO, almeno per ora
    }


}
