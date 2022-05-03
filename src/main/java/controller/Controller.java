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

    public Controller(Match match, List<RemoteView> remoteViewList, boolean isExpertMatch){
        this.match=match;
        this.remoteViewMap=new HashMap<>();
        for (RemoteView view: remoteViewList) {
            remoteViewMap.put(view.getPlayer(),view);
        }
        this.isExpertMatch=isExpertMatch;
    }

    private void choiceManager(Choice choice, Player player){
        remoteViewMap.get(player).choiceUser(choice);
    }

    public void startMatch(){
        Choice actualChoice;
        try{
            for (Player p: remoteViewMap.keySet()) {
                choiceManager(new DataPlayerChoice(match.getTotalPlayersNum(), p.getNickname()),p);
            }
            while(true){
                match.refillClouds();
                do{
                    choiceManager(new CardChoice(match.showCards()), match.showCurrentPlayer());
                }while(match.setNextCurrDashboard());
                match.setDashboardOrder();
                do{
                    for(int i=0; i<NUMSTUDENTSMOVE; i++) {
                        choiceManager(new MoveStudentChoice(match.showCurrentPlayerDashboard().showEntrance()), match.showCurrentPlayer());
                    }

                    //SPOSTATO NEI METODI moveStudent.. del match
                    //match.checkAndMoveMasters();

                    if(isExpertMatch) {
                        choiceManager(new FigureCardPlayedChoice(((ExpertMatch) match).showFigureCardsInGame()), match.showCurrentPlayer());
                    }

                    choiceManager(new MoveMotherNatureChoice(), match.showCurrentPlayer());

                    choiceManager(new CloudChoice(), match.showCurrentPlayer());

                }while(match.setNextCurrDashboard());
            }
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
            } catch (Exceptions e) {
                e.manageException(remoteViewMap.get(match.showCurrentPlayer()),(Choice) arg);
            } catch (FinishedGameExceptions e) { //Finished game exceptions
                e.manageException(remoteViewMap);
            }
        }
    }
}