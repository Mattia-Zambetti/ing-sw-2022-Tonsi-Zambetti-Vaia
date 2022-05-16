package model.Message;

import client.Client;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private int id;

    public Message(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public abstract void manageMessage(Client client);
}
