package view.choice;

import model.Match;
import model.exception.CardNotFoundException;
import model.exception.MaxNumberException;
import model.exception.NoMoreCardException;
import model.exception.WrongCloudNumberException;

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
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)) {
            switch (getNumChoice()) {
                case 0:
                    if (Integer.parseInt(input) >= Match.getMINPLAYERSNUM() &&
                            Integer.parseInt(input) <= Match.getMAXPLAYERSNUM()) {
                        setTotalPlayersNumMatch(Integer.parseInt(input));
                        choicePlusPlus();
                    } else
                        System.out.println("Error in the player number insertion, please give a number from 2 to 4");
                    return true;
                case 1:
                    if (Integer.parseInt(input) <= TOTAL_NUM_MATCH_TYPE && Integer.parseInt(input) > 0) {
                        setMatchType(Integer.parseInt(input));
                        System.out.println("Waiting other players... ");
                        return false;
                    }else
                        return true;

            }
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException {
        //QUESTO IN ReALTA' NON DEVE ESSERE MAI CHIAMATO, almeno per ora
    }

    @Override
    public String toString() {
        String OutputString = "";
        switch (getNumChoice()) {
            case 0:
                OutputString = "You are the first player, choose the number of players(from 2 to 4): ";
                break;
            case 1:
                OutputString = "Insert the match type:\n" +
                        "1. Normal match\n" +
                        "2. Expert match";
                break;
        }
        return OutputString;
    }


    public int getTotalNumMatchType(){
        return TOTAL_NUM_MATCH_TYPE;
    }

}
