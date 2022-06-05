package client;

import controller.choice.Choice;
import controller.choice.DataPlayerChoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGUIPlayerData implements Initializable,ControllerGUIInterface {
    private static ClientJavaFX client;
    private static Stage stage;
    private Scene scene;
    private Parent root;

    private static Choice choice;

    @FXML
    private TextField nickname;

    @FXML
    private ChoiceBox<String> towerColorChoiceBox;

    @FXML
    private ChoiceBox<String> wizardTypeChoiceBox;

    private List<String> wizardTypeChoices = new ArrayList<>(){{add("WIZARD1"); add("WIZARD2"); add("WIZARD3");add("WIZARD4");}};

    private List<String> towerColorChoices =new ArrayList<>() {{add("WHITE"); add("BLACK");add("GREY");}};

    public static void setChoice(Choice choice) {
        ControllerGUIPlayerData.choice = choice;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        wizardTypeChoiceBox.getItems().addAll(wizardTypeChoices);
        towerColorChoiceBox.getItems().addAll(towerColorChoices);
        wizardTypeChoiceBox.setValue(wizardTypeChoices.get(0));
        towerColorChoiceBox.setValue(towerColorChoices.get(0));
    }

    @Override
    public void setClient(ClientJavaFX c) {
        client = c;
    }

    @Override
    public void switchScene(Choice choice) throws IOException {
        if(choice instanceof DataPlayerChoice) {

            client.getFxmlLoader().setLocation(getClass().getResource("GameScene.fxml"));
            root = client.getFxmlLoader().load();

            scene = new Scene(root);
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void submitValues(ActionEvent e){
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        String s=nickname.getText();
        choice.setChoiceParam(s);
        s =new String(""+(wizardTypeChoices.indexOf(wizardTypeChoiceBox.getValue())+1));
        choice.setChoiceParam(s);
        s = new String(""+ (towerColorChoices.indexOf(towerColorChoiceBox.getValue())+1));
        choice.setChoiceParam(s);
        synchronized ( client.getOutputStreamLock() ) {
            client.getOutputStreamLock().notifyAll();
        }
    }
}
