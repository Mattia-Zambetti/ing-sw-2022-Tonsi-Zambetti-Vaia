import client.Client;
import client.ClientJavaFX;
import javafx.application.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.application.Application.launch;

public class ClientApp {
    private static int clientType=2;

    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Client client=new Client("127.0.0.1", 12345);
        switch ( clientType ) {
            case 1:
                client.run();
                break;
            case 2:
                ClientJavaFX clientGUI = new ClientJavaFX();
                clientGUI.setParam("127.0.0.1", 12345);
                executor.submit(clientGUI);
                Application.launch(ClientJavaFX.class);
                break;
        }
    }

}
