package controller;

import model.Match;
import view.RemoteView;

import java.util.List;

public class Controller {
    Match match;
    List<RemoteView> remoteViewList;

    public Controller(Match match, List<RemoteView> remoteViewList){
        this.match=match;
        this.remoteViewList=remoteViewList; //TODO: copia o no?
    }
}
