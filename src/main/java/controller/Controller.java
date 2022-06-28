package controller;

import controller.choice.Choice;
import controller.choice.DataPlayerChoice;
import controller.choice.FigureCardActionChoice;
import controller.choice.FigureCardPlayedChoice;
import model.Match;
import model.exception.Exceptions;
import model.exception.FinishedGameExceptions;
import view.RemoteView;

import java.util.Observable;
import java.util.Observer;

//TODO ERRORI DA RIMUOVERE:
/*
    -Il metodo checkNearByIsland lancia eccezione se non ci sono torri
    -Se si collegano altri due giocatori dopo che si sono collegati i primi due viene creato un nuovo match e iniziato, quello prima termina
    -La choice per inserire i Dati del giocatore puó essere inviata due volte dallo stesso ClientCLI (problema a riga 139 ClientCLI)
*/


public class Controller implements Observer {
    private final Match match;
    private static Choice tmpChoice;

    public Controller(Match match){
        this.match=match;
    }

    public static Choice getTmpChoice() {
        return tmpChoice;
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        if (o instanceof RemoteView) {
            if ( (arg instanceof Choice) && (((Choice) arg).whichChoicePhase().equals(match.getChoice().whichChoicePhase())
                    || (arg instanceof FigureCardPlayedChoice) || (arg instanceof FigureCardActionChoice))) {
                if((arg instanceof DataPlayerChoice) || (((Choice) arg).getSendingPlayer().equals(match.showCurrentPlayer())))
                {
                    try {
                        if (((Choice) arg).completed) {
                            ((Choice) arg).manageUpdate(match);
                        } else
                            tmpChoice = (Choice) arg;
                    } catch (Exceptions e) {
                        e.manageException(match);
                    } catch (FinishedGameExceptions e) {
                        e.manageException(match);
                    }
                }
            }
        }
    }

}