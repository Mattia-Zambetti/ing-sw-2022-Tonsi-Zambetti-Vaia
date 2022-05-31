import client.Client;
import client.ClientJavaFX;
import client.ControllerGUI;
import javafx.application.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.application.Application.launch;

public class ClientApp {
    private static int clientType=2;

    public static void main(String[] args){

        switch ( clientType ) {
            case 1:
                Client client=new Client("127.0.0.1", 12345);
                client.run();
                break;
            case 2:
                ClientJavaFX clientGUI = new ClientJavaFX();
                ControllerGUI.setClient(clientGUI);
                Application.launch(ClientJavaFX.class);
                break;
        }
    }

}
