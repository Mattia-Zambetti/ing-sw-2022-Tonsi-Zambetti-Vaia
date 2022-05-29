import client.Client;
import client.ClientJavaFX;
import javafx.application.Application;

public class ClientApp {
    private static int clientType=1;

    public static void main(String[] args){
        Client client=new Client("127.0.0.1", 12345);
        switch ( clientType ) {
            case 1:
                client.run();
                break;
            case 2:
                Application.launch(ClientJavaFX.class);
                break;
        }
    }

}
