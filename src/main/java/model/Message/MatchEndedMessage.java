package model.Message;

import client.Client;
import model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MatchEndedMessage implements Message, Serializable {

    List<Player> winners;

    public MatchEndedMessage ( List<Player> winners ) {
        this.winners = new ArrayList<Player>();
        this.winners.addAll(winners);
    }

    @Override
    public void manageMessage(Client client) {

        String outputString = "";

        if ( winners.contains(client.getPlayer()) )
            outputString = "YOU WON!";
        else {
            for ( Player p : winners )
                outputString = outputString.concat(p.getNickname() + " ");
            outputString = outputString.concat(" WON THE GAME!");
        }

        client.printToCLI(outputString);
    }
}
