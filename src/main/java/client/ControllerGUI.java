package client;

import controller.choice.Choice;
import controller.choice.DataPlayerChoice;
import controller.choice.StartingMatchChoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControllerGUI extends ControllerGUIInterface implements Initializable{

    @FXML
    private Label textBox;

    @FXML
    private Button enterButton;

    @FXML
    public void enterGameMethod(ActionEvent e) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        if ( !client.isConnected() ) {
            client.setParam();
            executor.submit(client);
        }
    }


    public void switchScene(Choice choice) throws IOException {
        if(choice instanceof StartingMatchChoice) {

            client.getFxmlLoader().setLocation(getClass().getResource("StartMatch.fxml"));
            root = client.getFxmlLoader().load();

            scene = new Scene(root);
            //stage.setFullScreen(false);
            stage.setMaximized(false);

            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        }else if ( choice instanceof DataPlayerChoice ) {
            if(((DataPlayerChoice)choice).getPlayerNum()==3)
                ControllerGUIPlayerData.isGrey=true;
            client.getFxmlLoader().setLocation(getClass().getResource("PlayerDataScene.fxml"));
            root = client.getFxmlLoader().load();


            scene = new Scene(root);


            stage.setMaximized(false);
            stage.setScene(scene);

            stage.setMaximized(true);

            stage.show();
        }
    }

    @Override
    public void printMessageText(String s) {
        textBox.setText(s);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font font=Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),12);
        textBox.setFont(font);
        enterButton.setFont(font);
    }


}
