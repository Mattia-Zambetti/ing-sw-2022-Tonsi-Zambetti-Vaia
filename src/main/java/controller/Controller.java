package controller;

import model.ExpertMatch;
import model.Match;
import model.Player;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.NoMoreBlockCardsException;
import view.RemoteView;
import view.choice.*;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
    private final Match match;
    private final HashMap<Player,RemoteView> remoteViewMap;
    private static final int NUMSTUDENTSMOVE=3;
    private final boolean isExpertMatch;

    public Controller(Match match, List<RemoteView> remoteViewList, boolean isExpertMatch){
        this.match=match;
        this.remoteViewMap=new HashMap<>();
        for (RemoteView view: remoteViewList) {
            remoteViewMap.put(view.getPlayer(),view);
        }
        this.isExpertMatch=isExpertMatch;
    }

    public void startMatch(){
        Choice actualChoice;
        try{
            while(true){
                match.refillClouds();
                do{
                    actualChoice=new CardChoice(match.showCards());
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);
                }while(match.setNextCurrDashboard());
                do{
                    for(int i=0; i<NUMSTUDENTSMOVE; i++) {
                        actualChoice = new MoveStudentChoice(match.showCurrentPlayerDashboard().showEntrance());
                        remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);
                    }
                    match.checkAndMoveMasters();
                    if(isExpertMatch) {
                        actualChoice = new FigureCardPlayedChoice(((ExpertMatch) match).showFigureCardsInGame());
                        remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);
                    }
                    actualChoice=new MoveMotherNatureChoice();
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);

                    actualChoice=new CloudChoice(match.showClouds());
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);
                }while(match.setNextCurrDashboard());
            }
        } catch (NoMasterException e) {
            e.printStackTrace();//TODO Penso funzioni così...?
            System.out.println(e.getMessage());
        } catch (WrongColorException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof RemoteView) {
            if(arg instanceof CardChoice) {
                match.chooseCard(((CardChoice) arg).getChosenCard());
            }else if(arg instanceof MoveStudentChoice){
                switch (((MoveStudentChoice) arg).getWhereToMove()) {
                    case "dining room":
                        match.moveStudentFromEntranceToDR(((MoveStudentChoice) arg).getChosenStudent());
                    case "island":
                        try {
                            match.moveStudentFromEntranceToIsland(((MoveStudentChoice) arg).getChosenStudent(), ((MoveStudentChoice) arg).getIslandID());
                        } catch (NoIslandException e) {
                            remoteViewMap.get(match.showCurrentPlayer()).choiceUser((MoveStudentChoice) arg);
                            //TODO VEDIAMO
                        }
                }
            }else if(arg instanceof FigureCardPlayedChoice){
                try {
                    ((ExpertMatch)match).playFigureCard(((FigureCardPlayedChoice) arg).getChosenFigureCard());
                } catch (CardNotFoundException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError("This card isn't in this match, try again");
                } catch (FigureCardAlreadyPlayedInThisTurnException e) {//TODO siamo sicuri che serva?
                    remoteViewMap.get(match.showCurrentPlayer()).sendError("You have already played this card in this turn");
                } catch (InsufficientCoinException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError("You don't have enough coins");
                }
            }//TODO manca parte di quando si chiamano i metodi delle carte specifiche
            else if(arg instanceof MoveMotherNatureChoice)
            {
                try {
                    match.moveMotherNature(((MoveMotherNatureChoice) arg).getMovement());
                } catch (NoIslandException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError("You insert a wrong island number, try again:");
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser((MoveMotherNatureChoice)arg);
                } catch (SameInfluenceException e) {
                    e.printStackTrace();//COME DOVREI GESTIRLA CUCCIOLO
                } catch (NoMoreBlockCardsException e) {
                    e.printStackTrace();//MA
                } catch (MaxNumberException e) {
                    e.printStackTrace();//MADONNA
                }
            }else if(arg instanceof CloudChoice){
                match.moveStudentsFromCloudToEntrance(((CloudChoice) arg).getChosenCloudID());
            }
        }
    }
}
