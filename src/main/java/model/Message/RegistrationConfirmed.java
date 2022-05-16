package model.Message;

import client.Client;

public class RegistrationConfirmed extends ConfirmationMessage {
    public RegistrationConfirmed(int id) {
        super(id);
    }

    @Override
    public void manageMessage(Client client) {
        if(client.getIdThis()==0){
            client.setIdThis(getId());
            System.out.println("Successfully connected to the lobby!");
        }
    }
}
