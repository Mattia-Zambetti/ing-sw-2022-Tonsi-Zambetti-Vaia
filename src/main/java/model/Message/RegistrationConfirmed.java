package model.Message;

import client.Client;
import client.ClientJavaFX;
import graphicAssets.CLIgraphicsResources;

public class RegistrationConfirmed extends ConfirmationMessage {

    public RegistrationConfirmed(int id) {
        super(id);
    }

    /**It sets the unique id of the client and it prints the message in the cli version*/
    @Override
    public void manageMessage(Client client) {
        if(client.getIdThis()==0){
            client.setIdThis(getId());
            client.printToScreen(CLIgraphicsResources.getStringColor("Successfully connected to the lobby",CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_GREEN));
        }
    }

    /**Not tested, it sets the unique id of the client and it prints in the message box the message*/
    @Override
    public void manageMessageGUI(ClientJavaFX client) {
        if(client.getIdThis()==0){
            client.setIdThis(getId());
            client.printToScreen("Successfully connected to the lobby");
        }
    }
}
