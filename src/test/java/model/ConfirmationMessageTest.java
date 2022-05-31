package model;

import client.ClientCLI;
import model.Message.ConfirmationMessage;
import model.Message.PlayerSuccessfullyCreated;
import model.Message.RegistrationConfirmed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfirmationMessageTest {
    ConfirmationMessage confirmationMessage;
    ClientCLI clientCLI;

    @BeforeEach
    void init(){
        clientCLI =new ClientCLI("127.0.0.1", 12345);

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
