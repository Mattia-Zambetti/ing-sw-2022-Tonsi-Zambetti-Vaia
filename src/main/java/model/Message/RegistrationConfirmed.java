package model.Message;

import client.Client;
import graphicAssets.CLIgraphicsResources;

public class RegistrationConfirmed extends ConfirmationMessage {
    public RegistrationConfirmed(int id) {
        super(id);
    }

    @Override
    public void manageMessage(Client client) {
        if(client.getIdThis()==0){
            client.setIdThis(getId());
            System.out.println(CLIgraphicsResources.getStringColor("Successfully connected to the server",CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_GREEN));

        }
    }
}
