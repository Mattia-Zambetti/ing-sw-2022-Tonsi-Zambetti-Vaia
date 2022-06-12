import client.ClientCLI;
import client.ClientJavaFX;
import javafx.application.Application;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {

    private static int clientType;
    private static Scanner readUser= new Scanner(System.in);

    public static void main(String[] args){


        System.out.println("Choose between CLI (1) and GUI (2):");
        clientType = readUser.nextInt();

        switch ( clientType ) {
            case 1:
                ClientCLI clientCLI =new ClientCLI("127.0.0.1", 12345);
                clientCLI.run();
                break;
            case 2:
                ClientJavaFX clientGUI = new ClientJavaFX();
                Application.launch(ClientJavaFX.class);
                break;
            default:
                System.out.println("Error: you chose: "+clientType);
        }
    }

}
