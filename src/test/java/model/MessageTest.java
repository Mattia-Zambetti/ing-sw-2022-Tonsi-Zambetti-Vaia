package model;

import client.Client;
import model.Message.Message;
import model.Message.PlayerSuccessfullyCreated;
import model.Message.RegistrationConfirmed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {
    Message message;
    Client client;

    @BeforeEach
    void init(){
        client=new Client("127.0.0.1", 12345);

    }

    @Test
    void PlayerSuccessfullyCreatedTest(){
        client.setIdThis(1);
        message=new PlayerSuccessfullyCreated(new Player("Zambo"),1);

        message.manageMessage(client);
        assertEquals(new Player("Zambo"),((PlayerSuccessfullyCreated)message).getPlayer());

    }

    @Test
    void RegistrationConfirmedTest(){
        message=new RegistrationConfirmed(1);

        message.manageMessage(client);
        assertEquals(1,((RegistrationConfirmed)message).getId());

    }

}
