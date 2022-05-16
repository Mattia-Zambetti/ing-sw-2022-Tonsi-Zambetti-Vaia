package controller;

import model.Player;

import java.util.ArrayList;
import java.util.List;

public class MatchEndedMessage {

    List<Player> winners;

    public MatchEndedMessage ( List<Player> winners ) {
        this.winners = new ArrayList<Player>();
        this.winners.addAll(winners);
    }

}
