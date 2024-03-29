package model.Message;

import client.Client;
import client.ClientJavaFX;

import java.io.Serial;
import java.io.Serializable;

public abstract class Message implements Serializable{

    @Serial
    private static final long serialVersionUID =445345453;

    public abstract void manageMessage(Client client);


    public abstract void manageMessageGUI(ClientJavaFX client);

}
