import client.ClientCLI;
import client.ClientJavaFX;
import javafx.application.Application;

public class ClientApp {
    private static int clientType=1;

    public static void main(String[] args){

        switch ( clientType ) {
            case 1:
                ClientCLI clientCLI =new ClientCLI("127.0.0.1", 12345);
                clientCLI.run();
                break;
            case 2:
                ClientJavaFX clientGUI = new ClientJavaFX();
                Application.launch(ClientJavaFX.class);
                break;
        }
    }

}
