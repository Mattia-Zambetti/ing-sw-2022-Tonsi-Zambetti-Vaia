package model.Message;

import client.Client;

import java.io.Serializable;

public abstract class ConfirmationMessage implements Serializable, Message {
    private int id;

    public ConfirmationMessage(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public abstract void manageMessage(Client client);
}
