package model.Message;

import client.Client;

import java.io.Serializable;

public interface Message extends Serializable{

    void manageMessage(Client client);
}
