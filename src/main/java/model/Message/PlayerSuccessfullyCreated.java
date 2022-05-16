package model.Message;

import client.Client;
import model.Player;

public class PlayerSuccessfullyCreated extends Message{
    private Player player;
    public PlayerSuccessfullyCreated(Player player, int id) {
        super(id);
        this.player=player;
    }

    public void manageMessage(Client client){
        if(client.getIdThis()== getId()) {
            client.setPlayer(player);
            System.out.println("Player confirmed, get ready for the match");
        }

    }

    public Player getPlayer() {
        return player;
    }
}
