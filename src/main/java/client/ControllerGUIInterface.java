package client;

import controller.choice.Choice;

import java.io.IOException;

public interface ControllerGUIInterface {
    void setClient(ClientJavaFX c);
    void switchScene(Choice choice) throws IOException;
}
