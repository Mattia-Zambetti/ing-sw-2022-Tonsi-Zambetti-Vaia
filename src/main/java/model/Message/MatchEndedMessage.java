package model.Message;

import client.Client;
import client.ClientJavaFX;
import client.ControllerGUIGame;
import javafx.application.Platform;
import model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MatchEndedMessage extends Message implements Serializable {

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

        client.printToScreen(outputString);
        client.closeConnection();
    }

    @Override
    public void manageMessageGUI(ClientJavaFX client) {

        String outputString = "";
        if ( winners.contains(client.getPlayer()) )
            outputString = "YOU WON!";
        else {
            for ( Player p : winners )
                outputString = outputString.concat(p.getNickname() + " ");
            outputString = outputString.concat(" WON THE GAME!");
        }

        String finalOutputString = outputString;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((ControllerGUIGame)client.getControllerGUI()).showMessage(finalOutputString);
            }
        });

        client.closeConnection();
    }
}
