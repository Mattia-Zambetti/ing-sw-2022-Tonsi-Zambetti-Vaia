package server;


import utils.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection extends Observable<Object> {

    private final ObjectInputStream scannerIn;
    private final ObjectOutputStream writeOut;
    private final Socket socket;
    private final Server server;


    public Connection(Socket socket, Server server) throws IOException {
        this.socket=socket;

        scannerIn=new ObjectInputStream(socket.getInputStream());
        writeOut=new ObjectOutputStream(socket.getOutputStream());

        this.server=server;
    }

    public synchronized void send(Object obj){
        try {
            writeOut.writeObject(obj);
            writeOut.flush();
        } catch (IOException e) {
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
