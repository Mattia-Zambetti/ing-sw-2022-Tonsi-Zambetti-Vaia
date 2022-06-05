package controller.choice;

import graphicAssets.CLIgraphicsResources;
import model.Match;
import model.MatchDataInterface;
import model.Player;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;

import java.io.Serial;
import java.io.Serializable;

public abstract class Choice implements Serializable {

    @Serial
    private static final long serialVersionUID =445345454;

    private Player sendingPlayer;

    public boolean completed = false;

    public void setSendingPlayer(Player sendingPlayer) {
        this.sendingPlayer = new Player(sendingPlayer.getNickname());
    }

    public Player getSendingPlayer() {
        return sendingPlayer;
    }

    public abstract boolean setChoiceParam(String input);

    public abstract void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMoreBlockCardsException, NoIslandException, SameInfluenceException, WrongDataplayerException, NoMasterException, NoMoreTowerException, TowerIDAlreadyExistingException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException, FinishedGameIslandException, CardAlreadyPlayedException, FinishedGameEndTurnException;

    public abstract String toString(MatchDataInterface match);

    public boolean getChoiceCompleted() {
        return completed;
    }


    public boolean isItAnInt(String input){
        try{
            Integer.parseInt(input);
            return true;
        }catch (NumberFormatException e){
            System.out.println(getRedString("That isn't a number, please try again: "));
            return false;
        }
    }

    public String retryMessage(){
        return  getRedString("please try again");
    }

    public String getRedString(String normalString){
        StringBuilder res=new StringBuilder(CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_RED);
        res.append(normalString);
        res.append(CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR);
        return res.toString();
    }

}
