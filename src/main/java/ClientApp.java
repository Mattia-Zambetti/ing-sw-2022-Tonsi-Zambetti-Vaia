import client.Client;

public class ClientApp {
    public static void main(String[] args){
        Client client=new Client("127.0.0.1", 11111);
        client.run();
    }

}
