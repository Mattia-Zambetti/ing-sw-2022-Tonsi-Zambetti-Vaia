package controller;

import controller.choice.Choice;
import model.Match;
import model.exception.*;
import view.RemoteView;
import controller.*;

import java.util.*;

//TODO ERRORI DA RIMUOVERE:
/*
    -Il metodo checkNearByIsland lancia eccezione se non ci sono torri
    -Se si collegano altri due giocatori dopo che si sono collegati i primi due viene creato un nuovo match e iniziato, quello prima termina
    -La choice per inserire i Dati del giocatore puó essere inviata due volte dallo stesso Client (problema a riga 139 Client)
*/


public class Controller implements Observer {
    private final Match match;
    private static final int NUMSTUDENTSMOVE=3;

    public Controller(Match match){
        this.match=match;
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        if (o instanceof RemoteView) {
            if ( arg instanceof Choice) {
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

}