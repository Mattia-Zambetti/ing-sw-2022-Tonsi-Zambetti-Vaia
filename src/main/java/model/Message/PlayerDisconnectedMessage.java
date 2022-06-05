package model.Message;

import client.Client;

public class PlayerDisconnectedMessage extends Message{
    @Override
    public void manageMessage(Client client) {
        client.printToScreen("Connection closed by the server, another player has quit the game.\nPress any key to quit.");
        client.closeConnection();
    }
}
