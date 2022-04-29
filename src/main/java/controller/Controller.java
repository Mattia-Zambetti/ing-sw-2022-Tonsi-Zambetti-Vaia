package controller;

import model.ExpertMatch;
import model.Match;
import model.Player;
import model.exception.*;
import model.figureCards.*;
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
            for (Player p: remoteViewMap.keySet()) {
                actualChoice = new DataPlayerChoice(match.getTotalPlayersNum(), p.getNickname());
                remoteViewMap.get(p).choiceUser(actualChoice);

            }
            while(true){
                match.refillClouds();
                do{
                    actualChoice=new CardChoice(match.showCards());
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);
                }while(match.setNextCurrDashboard());
                match.setDashboardOrder();

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

                    actualChoice=new CloudChoice();
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);
                }while(match.setNextCurrDashboard());
            }
        } catch (NoMasterException e) {
            e.printStackTrace();//TODO Penso funzioni così...?
            System.out.println(e.getMessage());
        } catch (WrongColorException e) {
            e.printStackTrace();
        } catch (NoMoreStudentsException e) {
            remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(e.getMessage())));
            //e.printStackTrace();TODO CONDIZIONE END MATCH
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RemoteView) {
            try {
                ((Choice) arg).manageUpdate(match, remoteViewMap.get(match.showCurrentPlayer()));

            } catch (CardNotFoundException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                remoteViewMap.get(match.showCurrentPlayer()).choiceUser((Choice) arg);
            } catch (NoMoreCardException e) {
                remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(e.getMessage())));
            } catch (WrongCloudNumberException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                remoteViewMap.get(match.showCurrentPlayer()).choiceUser((Choice) arg);
            } catch (MaxNumberException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                remoteViewMap.get(match.showCurrentPlayer()).choiceUser((Choice)arg);

            } catch (InsufficientCoinException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError("You don't have enough coins");
            } catch (InexistentStudentException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                remoteViewMap.get(match.showCurrentPlayer()).choiceUser((Choice) arg);//TODO FUNZIONERA'?
            } catch (StudentIDAlreadyExistingException e) {
                e.printStackTrace();
                //remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                //Choice choice=new JesterChoice();
                //remoteViewMap.get(match.showCurrentPlayer()).choiceUser(choice);
            } catch (WrongColorException e) {//TODO
                e.printStackTrace();
            } catch (NoMoreStudentsException e) {//TODO
                remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(e.getMessage())));
                //e.printStackTrace();
            } catch (NoIslandException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                remoteViewMap.get(match.showCurrentPlayer()).choiceUser((Choice) arg);
                //e.printStackTrace();//TODO
            } catch (SameInfluenceException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
            } catch (WrongDataplayerException e) {
                e.printStackTrace();
            } catch (NoMoreBlockCardsException e) {
                e.printStackTrace();
            } catch (FigureCardAlreadyPlayedInThisTurnException e) {
                e.printStackTrace();
            }
        }
    }
}