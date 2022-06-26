package model.Message;

import client.Client;
import client.ClientJavaFX;

public class FullLobbyMessage extends Message {

    @Override
    public void manageMessage(Client client) {
        client.printToScreen("The lobby is full, try to enter again later.\nPress any key to quit.");
        client.closeConnection();
    }

    //TODO DA CAMBIARE
    @Override
    public void manageMessageGUI(ClientJavaFX client) {
        client.printToScreen("The lobby is full, try to enter again later. Press any key to quit.");
        client.closeConnection();
    }
}
