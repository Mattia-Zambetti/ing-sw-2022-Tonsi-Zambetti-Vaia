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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGUIStartMatch extends ControllerGUI implements Initializable, ControllerGUIInterface  {

    private static Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ChoiceBox<String> totalPlayerNumberChoiceBox;

    @FXML
    private ChoiceBox<String> matchTypeChoiceBox;

    private List<String> totalPlayerNumChoices = new ArrayList<>(){{add("2 players"); add("3 players"); add("4 players");}};

    private List<String> matchTypeChoices =new ArrayList<>() {{add("Normal match"); add("Expert match");}};

    private static ClientJavaFX client;

    private static Choice choice;

    public void setClient(ClientJavaFX c){
        client = c;
    }

    public static void setChoice(Choice choice){
        ControllerGUIStartMatch.choice = choice;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        totalPlayerNumberChoiceBox.getItems().addAll(totalPlayerNumChoices);
        matchTypeChoiceBox.getItems().addAll(matchTypeChoices);
        totalPlayerNumberChoiceBox.setValue(totalPlayerNumChoices.get(0));
        matchTypeChoiceBox.setValue(matchTypeChoices.get(0));
    }

    @FXML
    public void submitValues(ActionEvent e){
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        String s =""+totalPlayerNumChoices.indexOf(totalPlayerNumberChoiceBox.getValue())+2;
        choice.setChoiceParam(s);
        s = ""+matchTypeChoices.indexOf(matchTypeChoiceBox.getValue())+1;
        choice.setChoiceParam(s);
        synchronized ( client.getOutputStreamLock() ) {
            client.getOutputStreamLock().notifyAll();
        }
    }

    @Override
    public void switchScene(Choice choice) throws IOException {
        if(choice instanceof DataPlayerChoice) {

            client.getFxmlLoader().setLocation(getClass().getResource("PlayerDataScene.fxml"));
            root = client.getFxmlLoader().load();
            /*FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("PlayerDataScene.fxml"));
            root = fxmlLoader.load();*/

            scene = new Scene(root);
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }
    }

}
