package client;


import model.MatchDataInterface;
import model.Player;
import view.choice.Choice;
import view.choice.NamePlayerChoice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
    private final int port;
    private final String ip;
    private Choice actualToDoChoice;

    private MatchDataInterface matchView;

    private Socket clientSocket;

    private Player player;

    private boolean isActive=true;
    private boolean isChoiceTime;

    public Client(String ip, int port){
        this.port=port;
        this.ip=ip;
        isChoiceTime=false;
    }


    @Override
    public void run() {

        try {
            clientSocket = new Socket(ip, port);
            System.out.println("You are in the lobby");

            Thread threadUser= this.readingFromUser();
            Thread threadSocket= this.readingFromSocket();
            threadUser.join();
            threadSocket.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }


    public Thread readingFromUser() throws IOException {
        Scanner readUser= new Scanner(System.in);
        PrintWriter writeUser=new PrintWriter(System.out);
        ObjectOutputStream outputStream=new ObjectOutputStream(clientSocket.getOutputStream());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String input;
                while (isActive) {
                    try {
                        input=readUser.nextLine();
                        if (!isChoiceTime) {
                            writeUser.println("please wait...");
                            writeUser.flush();
                        } else {
                            isChoiceTime=actualToDoChoice.setChoiceParam(input);
                            if(actualToDoChoice instanceof NamePlayerChoice)
                                player=new Player(input);
                            if(!isChoiceTime) {
                                outputStream.writeObject(actualToDoChoice);
                                outputStream.flush();
                                outputStream.reset();
                            }
                        }
                    }catch (IllegalStateException e){
                        isActive=false;
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        return t;
    }

    public Thread readingFromSocket() throws IOException {
        PrintWriter writeUser=new PrintWriter(System.out);
        ObjectInputStream readSocket=new ObjectInputStream(clientSocket.getInputStream());

        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                while(isActive) {
                    try {
                        Object obj = readSocket.readObject();

                        if(obj instanceof MatchDataInterface)
                            matchView=(MatchDataInterface) obj;


                        writeUser.println(matchView);
                        writeUser.flush();

                        if(matchView.showCurrentPlayer().equals(player)) {
                            isChoiceTime = true;
                            actualToDoChoice= matchView.getChoice();
                            writeUser.println(actualToDoChoice);
                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        isActive = false;
                    }
                }
            }
        });
        t.start();
        return t;
    }
}
