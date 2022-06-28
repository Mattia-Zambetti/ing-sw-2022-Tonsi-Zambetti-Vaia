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