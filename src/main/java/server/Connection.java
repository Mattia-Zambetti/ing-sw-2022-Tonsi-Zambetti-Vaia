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

    public Choice sendAndReceive(Object obj){
        try {

            writeOut.writeObject(obj);
            writeOut.flush();
            return (Choice) scannerIn.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createConnection (){
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

            /*while(isActive) {//TODO
                choice = (Choice) scannerIn.readObject();
                setChanged();
                notifyObservers(choice);
            }*/

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }












 /*

    private String name;
    private boolean active = true;



    private synchronized boolean isActive(){
        return active;
    }



    public void asyncSend(final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }



    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void run() {
        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            send("Welcome! What's your name?");
            name = in.nextLine();
            server.lobby(this, name);
            while(isActive()){
                String read = in.nextLine();
                notify(read);
            }
        } catch(IOException e){
            System.err.println(e.getMessage());
        } finally {
            close();
        }
    }*/
}
