package model.Message;

import client.Client;
import client.ClientJavaFX;

public class WaitingMatchCreationMessage extends Message {

    /**it allows to print the message on the cli version*/
    @Override
    public void manageMessage(Client client) {
        client.printToScreen("Connected to the Server. Waiting for a match creation...");
    }

    /**Not tested(GUI component), it allows to print the message in the message box*/
    //TODO DA CAMBIARE
    @Override
    public void manageMessageGUI(ClientJavaFX client) {
        client.printToScreen("Connected to the Server. Waiting for a match creation...");
    }
}
