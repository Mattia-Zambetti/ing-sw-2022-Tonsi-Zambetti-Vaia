package model.Message;

import client.Client;
import graphicAssets.CLIgraphicsResources;
import model.Player;

public class PlayerSuccessfullyCreated extends ConfirmationMessage {
    private Player player;
    public PlayerSuccessfullyCreated(Player player, int id) {
        super(id);
        this.player=player;
    }

    public void manageMessage(Client client){
        if(client.getIdThis()== getId()) {
            client.setPlayer(player);
            System.out.println(CLIgraphicsResources.getStringColor("Player confirmed, get ready for the match",CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_GREEN));
        }

    }

    public Player getPlayer() {
        return player;
    }
}
