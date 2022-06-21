package model.Message;

import client.Client;
import client.ClientJavaFX;

public class WaitingMatchCreationMessage extends Message {
    @Override
    public void manageMessage(Client client) {
        client.printToScreen("Connected to the Server. Waiting for a match creation...");
    }

    //TODO DA CAMBIARE
    @Override
    public void manageMessageGUI(ClientJavaFX client) {
        client.printToScreen("Connected to the Server. Waiting for a match creation...");
    }
}
