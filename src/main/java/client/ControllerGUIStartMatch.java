package client;

import controller.choice.Choice;
import controller.choice.DataPlayerChoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGUIStartMatch extends ControllerGUIInterface implements Initializable  {
    @FXML
    private ChoiceBox<String> totalPlayerNumberChoiceBox;
    @FXML
    private Button sendStartingMatchButton;
    @FXML
    private ChoiceBox<String> matchTypeChoiceBox;
    @FXML
    private Label textBox;
    @FXML
    private Text text1;

    @FXML
    private Text text2;

    private List<String> totalPlayerNumChoices = new ArrayList<>(){{add("2 PLAYERS"); add("3 PLAYERS"); add("4 PLAYERS");}};

    private List<String> matchTypeChoices =new ArrayList<>() {{add("NORMAL MATCH"); add("EXPERT MATCH");}};




    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        totalPlayerNumberChoiceBox.getItems().addAll(totalPlayerNumChoices);
        matchTypeChoiceBox.getItems().addAll(matchTypeChoices);
        totalPlayerNumberChoiceBox.setValue(totalPlayerNumChoices.get(0));
        matchTypeChoiceBox.setValue(matchTypeChoices.get(0));

        Font font=Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),16);
        sendStartingMatchButton.setFont(font);
        textBox.setFont(Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),14));

        text1.setFont(font);
        text2.setFont(font);


    }

    @FXML
    public void submitValues(ActionEvent e){
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        String s =""+(totalPlayerNumChoices.indexOf(totalPlayerNumberChoiceBox.getValue())+2);
        client.getActualToDoChoice().setChoiceParam(s);
        s = ""+(matchTypeChoices.indexOf(matchTypeChoiceBox.getValue())+1);
        client.getActualToDoChoice().setChoiceParam(s);
        textBox.setText("Match created. " +
                "Waiting for other players...");
        sendStartingMatchButton.setDisable(true);
        sendStartingMatchButton.setStyle("-fx-background-color: #B0B0B0;");
        synchronized ( client.getOutputStreamLock() ) {
            client.getOutputStreamLock().notifyAll();
        }
    }

    @Override
    public void switchScene(Choice choice) throws IOException {
        if(choice instanceof DataPlayerChoice) {

            if(((DataPlayerChoice)choice).getPlayerNum()==3)
                ControllerGUIPlayerData.isGrey=true;
            client.getFxmlLoader().setLocation(getClass().getResource("PlayerDataScene.fxml"));
            root = client.getFxmlLoader().load();

            scene = new Scene(root);

            stage.setFullScreen(false);
            stage.setMaximized(false);

            stage.setScene(scene);
            //stage.setFullScreen(true);
            stage.setMaximized(true);

            stage.show();
        }
    }

    @Override
    public void printMessageText(String s) {
        System.out.println(s);
    }

}
