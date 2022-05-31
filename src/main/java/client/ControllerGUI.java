package client;

import com.sun.javafx.scene.NodeEventDispatcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import controller.choice.*;
import javafx.stage.Stage;
import model.MatchDataInterface;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControllerGUI {

    @FXML
    private ImageView island1;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ChoiceBox<String> totalPlayerNumberChoiceBox;

    private String[] totalPlayerNumChoices = {"2 players", "3 players", "4 players"};

    private static ClientJavaFX client;

    public MatchDataInterface match;

    public void setClient(ClientJavaFX c){
        client = c;
    }

    public void setMatch(MatchDataInterface match1){
        match = match1;
    }

    @FXML
    public void manageIsland1Clicked(MouseEvent e){
        island1.setX(island1.getX()+1);
    }

    @FXML
    public void enterGameMethod(ActionEvent e) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        client.setParam("127.0.0.1", 12345);
        executor.submit(client);
    }


    public void switchScene(Choice choice) throws IOException {
        if(choice instanceof StartingMatchChoice) {
            root = FXMLLoader.load(getClass().getResource("StartMatch.fxml"));
            totalPlayerNumberChoiceBox.getItems().addAll(totalPlayerNumChoices);
            scene = new Scene(root);
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setScene(scene);
        }
    }

}
