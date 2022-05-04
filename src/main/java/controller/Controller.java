package controller;

import model.Match;
import model.Player;
import model.exception.*;
import view.RemoteView;
import view.choice.*;

import java.util.*;

//TODO ERRORI DA RIMUOVERE:
/*
    -Il numero di studenti da muovere dipende dal numero di giocatori (NUMSTUDENTSMOVE errato)
    -Non viene segnalato errore se si gioca la stessa carta
    -Le isole non vengono unite (il metodo checkNearbyIsland non viene mai utilizzato)
*/


public class Controller implements Observer {
    private final Match match;
    private final HashMap<Player,RemoteView> remoteViewMap;
    private static final int NUMSTUDENTSMOVE=3;
    private final boolean isExpertMatch;

    public Controller(Match match, boolean isExpertMatch){
        this.match=match;
        this.remoteViewMap=new HashMap<>();
        /*for (RemoteView view: remoteViewList) {
            remoteViewMap.put(view.getPlayer(),view);
        }*/
        this.isExpertMatch=isExpertMatch;
    }

    private void choiceManager(Choice choice, Player player){
        remoteViewMap.get(player).choiceUser(choice);
    }

    /*public void startMatch(){
        Choice actualChoice;

        try {
            for (Player p : remoteViewMap.keySet()) {
                choiceManager(new DataPlayerChoice(match.getTotalPlayersNum(), p.getNickname()), p);
            }
            while (true) {
                match.refillClouds();
                do {
                    choiceManager(new CardChoice(match.showCards()), match.showCurrentPlayer());
                } while (match.setNextCurrDashboard());
                match.setDashboardOrder();
                do {
                    for (int i = 0; i < match.chooseStudentsNumOnCLoud(); i++) {
                        choiceManager(new MoveStudentChoice(match.showCurrentPlayerDashboard().showEntrance()), match.showCurrentPlayer());
                    }
                    //SPOSTATO NEI METODI moveStudent.. del match
                    //match.checkAndMoveMasters();
                    if (isExpertMatch) {
                        choiceManager(new FigureCardPlayedChoice(((ExpertMatch) match).showFigureCardsInGame()), match.showCurrentPlayer());
                    }
                    choiceManager(new MoveMotherNatureChoice(), match.showCurrentPlayer());

                    choiceManager(new CloudChoice(), match.showCurrentPlayer());

                } while (match.setNextCurrDashboard());
            }
        } catch (NoMoreStudentsException e) {
            remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(e.getMessage())));
            //e.printStackTrace();TODO CONDIZIONE END MATCH
        }

    }*/

    public void startMatch() {
        try {

            System.out.println("StartMatch() done 1");
            do {
                wait(100);
            }while(match.getCurrentPlayersNum()!=match.getTotalPlayersNum());
            System.out.println("StartMatch() done 2");
            /*
            while (true) {
                match.refillClouds();
                do {
                    choiceManager(new CardChoice(match.showCards()), match.showCurrentPlayer());
                } while (match.setNextCurrDashboard());
                match.setDashboardOrder();
                do {
                    for (int i = 0; i < match.chooseStudentsNumOnCLoud(); i++) {
                        choiceManager(new MoveStudentChoice(match.showCurrentPlayerDashboard().showEntrance()), match.showCurrentPlayer());
                    }
                    //SPOSTATO NEI METODI moveStudent.. del match
                    //match.checkAndMoveMasters();
                    if (isExpertMatch) {
                        choiceManager(new FigureCardPlayedChoice(((ExpertMatch) match).showFigureCardsInGame()), match.showCurrentPlayer());
                    }
                    choiceManager(new MoveMotherNatureChoice(), match.showCurrentPlayer());

                    choiceManager(new CloudChoice(), match.showCurrentPlayer());

                } while (match.setNextCurrDashboard());
            }*/
        } /*catch (NoMoreStudentsException e) {
            remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(e.getMessage())));
            //e.printStackTrace();TODO CONDIZIONE END MATCH
        } */catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Match thread blocked by ");
        }

    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        if (o instanceof RemoteView) {
            try {
                ((Choice) arg).manageUpdate(match);
            } catch (Exceptions e) {
                e.manageException(match);
            } catch (FinishedGameExceptions e) { //Finished game exceptions
                //e.manageException(remoteViewMap);
            }
        }
    }

}