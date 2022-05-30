package client;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import controller.choice.*;

import java.awt.event.ActionEvent;

public class ControllerGUI {

    private ClientJavaFX client;

    public void setClient(ClientJavaFX client) {
        this.client=client;
    }

    @FXML
    public void manageIsland1Clicked(MouseEvent e){

        System.out.println("Premuta isola 1");
    }

}
