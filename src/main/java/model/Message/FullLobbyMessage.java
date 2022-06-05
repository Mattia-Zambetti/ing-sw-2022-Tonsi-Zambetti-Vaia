package model.Message;

import client.Client;

public class FullLobbyMessage extends Message {

    @Override
    public void manageMessage(Client client) {
        client.printToScreen("The lobby is full, try to enter again later.\nPress any key to quit.");
        client.closeConnection();
    }
}
