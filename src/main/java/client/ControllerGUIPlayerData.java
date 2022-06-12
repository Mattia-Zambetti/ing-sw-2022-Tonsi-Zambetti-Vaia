package client;

import controller.choice.CardChoice;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGUIPlayerData extends ControllerGUIInterface implements Initializable { private static Stage stage;

    private static boolean alreadyInsert=false;

    @FXML
    private TextField nickname;

    @FXML
    protected ChoiceBox<String> towerColorChoiceBox;

    @FXML
    private ChoiceBox<String> wizardTypeChoiceBox;

    @FXML
    private Button submit;

    @FXML
    private Label textNickname;

    @FXML
    private Label textWizard;

    @FXML
    private Label textTowerColor;

    private List<String> wizardTypeChoices = new ArrayList<>(){{add("WIZARD1"); add("WIZARD2"); add("WIZARD3");add("WIZARD4");}};

    private List<String> towerColorChoices =new ArrayList<>() {{add("WHITE"); add("BLACK");}};

    protected static boolean isGrey=false;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        wizardTypeChoiceBox.getItems().addAll(wizardTypeChoices);

        if(isGrey)
            towerColorChoices.add("GREY");
        towerColorChoiceBox.getItems().addAll(towerColorChoices);

        wizardTypeChoiceBox.setValue(wizardTypeChoices.get(0));
        towerColorChoiceBox.setValue(towerColorChoices.get(0));

        Font font=Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),14);

        textNickname.setFont(font);
        textTowerColor.setFont(font);
        textWizard.setFont(font);
        submit.setFont(Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),16));
    }

    @Override
    public void switchScene(Choice choice) throws IOException {
        if(choice instanceof DataPlayerChoice && alreadyInsert) {
            client.getFxmlLoader().setLocation(getClass().getResource("PlayerDataScene.fxml"));
            root = client.getFxmlLoader().load();
            scene = new Scene(root);
            stage.setFullScreen(false);
            stage.setMaximized(false);
            stage.setScene(scene);

            //stage.setFullScreen(true);
            stage.setMaximized(true);

            stage.show();

            submit.setDisable(false);

        }else if (choice instanceof CardChoice){
            client.getFxmlLoader().setLocation(getClass().getResource("GameScene.fxml"));
            root = client.getFxmlLoader().load();

            ((ControllerGUIGame)client.getFxmlLoader().getController()).updateInitialMatchView();
            ((ControllerGUIGame)client.getFxmlLoader().getController()).updateCardsView();

            scene = new Scene(root);
            stage.setScene(scene);

            stage.setFullScreen(false);
            stage.setMaximized(true);

            stage.show();

        }
    }

    @Override
    public void printMessageText(String s) {
        System.out.println(s);
    }

    @FXML
    public void submitValues(ActionEvent e){
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        String s=nickname.getText();
        client.getActualToDoChoice().setChoiceParam(s);
        s = new String(""+ (towerColorChoices.indexOf(towerColorChoiceBox.getValue())+1));
        client.getActualToDoChoice().setChoiceParam(s);
        ClientJavaFX.towerColorThisPlayer=towerColorChoices.indexOf(towerColorChoiceBox.getValue())+1;
        s =new String(""+(wizardTypeChoices.indexOf(wizardTypeChoiceBox.getValue())+1));
        client.getActualToDoChoice().setChoiceParam(s);
        ClientJavaFX.wizardThisPlayer=wizardTypeChoices.indexOf(wizardTypeChoiceBox.getValue())+1;
        submit.setDisable(true);
        alreadyInsert=true;
        synchronized ( client.getOutputStreamLock() ) {
            client.getOutputStreamLock().notifyAll();
        }

    }
}
