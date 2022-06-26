package model.Message;

import client.Client;
import client.ClientJavaFX;
import graphicAssets.CLIgraphicsResources;
import model.Player;

public class PlayerSuccessfullyCreated extends ConfirmationMessage {
    private Player player;


    public PlayerSuccessfullyCreated(Player player, int id) {
        super(id);
        this.player=player;
    }

    /** it allows to print the message, and it
     * sets the player in the client cli*/
    public void manageMessage(Client client){
        if(client.getIdThis()== getId()) {
            client.setPlayer(player);
            System.out.println(CLIgraphicsResources.getStringColor("Player confirmed, get ready for the match",CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_GREEN));
        }

    }

    /**Not tested(GUI component), it allows to print the message in the message box, and it
     * sets the player in the client javaFX*/
    public void manageMessageGUI(ClientJavaFX client){
        if(client.getIdThis()== getId()) {
            client.setPlayer(player);
            client.printToScreen("Player confirmed, get ready for the match");
        }

    }

    /**It returns the player data object*/
    public Player getPlayer() {
        return player;
    }
}
