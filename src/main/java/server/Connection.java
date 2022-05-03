package server;


import view.choice.Choice;
import view.choice.NamePlayerChoice;
import view.choice.StartingMatchChoice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

public class Connection extends Observable{

    private Socket clientSocket;
    private ObjectInputStream scannerIn;
    private ObjectOutputStream writeOut;
    private final Server server;

    private boolean isActive=true;


    public Connection(Socket socket, Server server) throws IOException {
        this.clientSocket=socket;
        this.server=server;
    }

    public void send(Object obj){
        try {
            writeOut.writeObject(obj);
            writeOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run (){
        try {
            writeOut=new ObjectOutputStream(clientSocket.getOutputStream());
            scannerIn=new ObjectInputStream(clientSocket.getInputStream());
            Choice choice = new StartingMatchChoice();

            if (server.getConnectionsSize() == 1) {
                choice = new StartingMatchChoice();
                send(choice);
                Choice startChoice = (Choice) scannerIn.readObject();
                server.setMatchParams(((StartingMatchChoice) startChoice).getTotalNumMatchType(), ((StartingMatchChoice) startChoice).getMatchType());
            }



            choice = new NamePlayerChoice(server.getNicknameSet());
            send(choice);
            choice = (Choice) scannerIn.readObject();
            server.addNickname(((NamePlayerChoice) choice).getPlayer().getNickname());
            server.lobby(this, (NamePlayerChoice) choice);


            while(isActive) {//TODO
                choice = (Choice) scannerIn.readObject();
                setChanged();
                notifyObservers(choice);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
