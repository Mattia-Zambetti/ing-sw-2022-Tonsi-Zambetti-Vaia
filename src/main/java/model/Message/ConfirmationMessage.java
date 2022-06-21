package model.Message;

import client.Client;
import client.ClientJavaFX;

import java.io.Serializable;

public abstract class ConfirmationMessage extends Message implements Serializable {
    private int id;

    public ConfirmationMessage(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public abstract void manageMessage(Client client);

    public abstract void manageMessageGUI(ClientJavaFX client);
}
