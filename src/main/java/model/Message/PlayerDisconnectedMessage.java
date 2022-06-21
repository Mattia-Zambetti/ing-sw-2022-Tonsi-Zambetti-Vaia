package model.Message;

import client.Client;
import client.ClientJavaFX;

public class PlayerDisconnectedMessage extends Message{
    @Override
    public void manageMessage(Client client) {
        client.printToScreen("Connection closed by the server, another player has quit the game.\nPress any key to quit.");
        client.closeConnection();
    }

    //TODO DA CAMBIARE
    @Override
    public void manageMessageGUI(ClientJavaFX client) {
        client.printToScreen("Connection closed by the server, another player has quit the game.\nPress any key to quit.");
        client.closeConnection();
    }
}
