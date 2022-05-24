package model.Message;

import client.Client;

public class PlayerDisconnectedMessage implements Message{
    @Override
    public void manageMessage(Client client) {
        client.printToCLI("Connection closed by the server, another player has quit the game");
    }
}
