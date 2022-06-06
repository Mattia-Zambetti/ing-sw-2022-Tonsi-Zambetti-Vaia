package controller.choice;

import model.Match;
import model.MatchDataInterface;
import model.exception.CardNotFoundException;
import model.exception.MaxNumberException;
import model.exception.WrongCloudNumberException;
import server.Server;

public class StartingMatchChoice extends Choice{
    private int totalPlayersNumMatch;
    private int matchType;
    private int numChoice=0;

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
                        System.out.println(getRedString("Error in the player number insertion, please give a number from 2 to 4"));
                    return true;
                case 1:
                    if (Integer.parseInt(input) <= Server.getTOTAL_NUM_MATCH_TYPE() && Integer.parseInt(input) > 0) {
                        setMatchType(Integer.parseInt(input));
                        System.out.println("Waiting other players... ");
                        completed = true;
                        return false;
                    }else
                        System.out.println(getRedString("There are only "+Server.getTOTAL_NUM_MATCH_TYPE()+" types of match, " )+ retryMessage());
                        return true;

            }
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException {
        //QUESTO IN REALTA' NON DEVE ESSERE MAI CHIAMATO, almeno per ora
    }

    @Override
    public String toString(MatchDataInterface match) {
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


}
