package model;

import client.ClientCLI;
import client.ClientJavaFX;
import model.Message.Message;
import model.Message.PlayerSuccessfullyCreated;
import model.Message.RegistrationConfirmed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {
    Message confirmationMessage;
    ClientCLI clientCLI;
    ClientJavaFX clientJavaFX;

    @BeforeEach
    void init(){
        clientCLI =new ClientCLI("127.0.0.1", 12345);
        clientJavaFX=new ClientJavaFX();

    }

    @Test
    void PlayerSuccessfullyCreatedTest(){
        clientCLI.setIdThis(1);
        confirmationMessage =new PlayerSuccessfullyCreated(new Player("Zambo"),1);

        confirmationMessage.manageMessage(clientCLI);

        assertEquals(new Player("Zambo"),((PlayerSuccessfullyCreated) confirmationMessage).getPlayer());
    }

    @Test
    void RegistrationConfirmedTest(){

        confirmationMessage =new RegistrationConfirmed(1);

        confirmationMessage.manageMessage(clientCLI);
        assertEquals(1,((RegistrationConfirmed) confirmationMessage).getId());

    }

}
