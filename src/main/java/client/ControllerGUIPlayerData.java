package client;

import controller.choice.Choice;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGUIPlayerData implements ControllerGUIInterface {
    private static ClientJavaFX client;

    @Override
    public void setClient(ClientJavaFX c) {
        client = c;
    }

    @Override
    public void switchScene(Choice choice) throws IOException {

    }

}
