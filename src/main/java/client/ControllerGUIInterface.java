package client;

import controller.choice.Choice;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ControllerGUIInterface {
    protected static ClientJavaFX client;
    protected static Stage stage;
    protected static Scene scene;
    protected static Parent root;


    public static void setClient(ClientJavaFX client) {
        ControllerGUIInterface.client = client;
    }

    public abstract void switchScene(Choice choice) throws IOException;
    public abstract void printMessageText(String s);

    public static void setStage(Stage stage) {
        ControllerGUIInterface.stage = stage;
    }
}
