package model.Message;

import client.Client;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class MatchEndedMessage implements Message{

    List<Player> winners;

    public MatchEndedMessage ( List<Player> winners ) {
        this.winners = new ArrayList<Player>();
        this.winners.addAll(winners);
    }

    @Override
    public void manageMessage(Client client) {

    }
}
