package client;

import controller.choice.Choice;
import controller.choice.DataPlayerChoice;
import controller.choice.StartingMatchChoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.MatchDataInterface;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControllerGUIstartMatch extends ControllerGUI implements Initializable  {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ChoiceBox<String> totalPlayerNumberChoiceBox;

    @FXML
    private ChoiceBox<String> matchTypeChoiceBox;

    private List<String> totalPlayerNumChoices = new ArrayList<>(){{add("2 players"); add("3 players"); add("4 players");}};

    private List<String> matchTypeChoices =new ArrayList<>() {{add("Normal match"); add("Expert match");}};

    private static ClientJavaFX client;

    public Choice choice;

    public void setClient(ClientJavaFX c){
        client = c;
    }

    public void setChoice(Choice choice){
        this.choice = choice;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        totalPlayerNumberChoiceBox.getItems().addAll(totalPlayerNumChoices);
        matchTypeChoiceBox.getItems().addAll(matchTypeChoices);
    }

    @FXML
    public void submitValues(ActionEvent e){
        String s = new String(""+totalPlayerNumChoices.indexOf(totalPlayerNumberChoiceBox.getValue())+2);
        choice.setChoiceParam(s);
        s = new String(""+matchTypeChoices.indexOf(matchTypeChoiceBox.getValue())+1);
        choice.setChoiceParam(s);
    }

    public void switchScene(Choice choice) throws IOException {
        if(choice instanceof DataPlayerChoice) {
            root = FXMLLoader.load(getClass().getResource("StartMatch.fxml"));
            scene = new Scene(root);
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setScene(scene);
        }
    }

}
