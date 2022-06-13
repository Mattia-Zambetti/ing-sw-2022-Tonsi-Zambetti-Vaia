package client;

import controller.choice.Choice;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ControllerGUIInterface {
    protected static ClientJavaFX client;
    protected static Stage stage;
    protected static Scene scene;
    protected static Parent root;

    protected static Map<Integer, Image> fromIntToWizard=new HashMap<>(){{
        put(1, new Image(getClass().getResourceAsStream("/client/Images/wizards/wizard1circle.png")));
        put(2,new Image(getClass().getResourceAsStream("/client/Images/wizards/wizard2circle.png" )));
        put(3, new Image(getClass().getResourceAsStream("/client/Images/wizards/wizard3circle.png" )));
        put(4, new Image(getClass().getResourceAsStream("/client/Images/wizards/wizard4circle.png" )));
    }};

    protected static Map<Integer, Image> fromIntToTower=new HashMap<>(){{
        put(1, new Image(getClass().getResourceAsStream("/client/Images/WhiteTower.png")));
        put(2,new Image(getClass().getResourceAsStream("/client/Images/BlackTower.png" )));
        put(3, new Image(getClass().getResourceAsStream("/client/Images/GreyTower.png" )));
    }};

    public static void setClient(ClientJavaFX client) {
        ControllerGUIInterface.client = client;
    }

    public abstract void switchScene(Choice choice) throws IOException;
    public abstract void printMessageText(String s);

    public static void setStage(Stage stage) {
        ControllerGUIInterface.stage = stage;
    }
}
