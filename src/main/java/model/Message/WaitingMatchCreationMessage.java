package model.Message;

import client.Client;

public class WaitingMatchCreationMessage extends Message {
    @Override
    public void manageMessage(Client client) {
        client.printToScreen("Connected to the Server. Waiting for a match creation...");
    }
}
