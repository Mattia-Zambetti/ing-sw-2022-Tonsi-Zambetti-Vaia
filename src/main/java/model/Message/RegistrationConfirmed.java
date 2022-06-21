package model.Message;

import client.Client;
import client.ClientJavaFX;
import graphicAssets.CLIgraphicsResources;

public class RegistrationConfirmed extends ConfirmationMessage {
    public RegistrationConfirmed(int id) {
        super(id);
    }

    @Override
    public void manageMessage(Client client) {
        if(client.getIdThis()==0){
            client.setIdThis(getId());
            client.printToScreen(CLIgraphicsResources.getStringColor("Successfully connected to the lobby",CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_GREEN));
        }
    }

    //TODO DA CAMBIARE
    @Override
    public void manageMessageGUI(ClientJavaFX client) {
        if(client.getIdThis()==0){
            client.setIdThis(getId());
            client.printToScreen(CLIgraphicsResources.getStringColor("Successfully connected to the lobby",CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_GREEN));
        }
    }
}
