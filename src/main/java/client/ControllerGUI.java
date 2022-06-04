package client;

import controller.choice.Choice;
import controller.choice.DataPlayerChoice;
import controller.choice.StartingMatchChoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.MatchDataInterface;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControllerGUI implements ControllerGUIInterface {

    @FXML
    private ImageView island1;
    @FXML
    private ChoiceBox<String> totalPlayerNumberChoiceBox;


    private Stage stage;
    private Scene scene;
    private Parent root;

    private static ClientJavaFX client;
    public MatchDataInterface match;

    private final String[] totalPlayerNumChoices = {"2 players", "3 players", "4 players"};

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
    public void enterGameMethod(ActionEvent e) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        client.setParam("127.0.0.1", 12345);
        executor.submit(client);
    }


    public void switchScene(Choice choice) throws IOException {
        if(choice instanceof StartingMatchChoice) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("StartMatch.fxml"));
            root = fxmlLoader.load();

            scene = new Scene(root);
            stage.setMaximized(true);
            stage.setFullScreen(true);
            stage.setScene(scene);
            stage.show();
        }else if ( choice instanceof DataPlayerChoice ) {

            client.getFxmlLoader().setLocation(getClass().getResource("PlayerDataScene.fxml"));
            root = client.getFxmlLoader().load();

            scene = new Scene(root);
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }
    }

}
