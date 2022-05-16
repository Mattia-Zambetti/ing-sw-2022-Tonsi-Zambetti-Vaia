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

        String outputString = "";

        if ( winners.contains(client.getPlayer()) )
            outputString = "YOU WON!";
        else {
            for ( Player p : winners )
                outputString = outputString.concat(p + " ");
            outputString = outputString.concat(" WON THE GAME!");
        }

        client.printToCLI(outputString);
    }
}
