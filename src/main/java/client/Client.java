package client;


import model.MatchDataInterface;
import model.Player;
import view.choice.*;

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

    private Player player = null;

    private boolean isActive=true;
    private volatile boolean isChoiceTime;

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
                            if(actualToDoChoice instanceof DataPlayerChoice)
                                player=new Player(input);
                            if(actualToDoChoice instanceof StartingMatchChoice) {
                                if(isChoiceTime) {
                                    writeUser.println(actualToDoChoice);
                                    writeUser.flush();
                                }
                            }
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

                        Object obj;
                        boolean matchCompletelyCreated = false;

                        obj = readSocket.readObject();
                        if ( obj instanceof StartingMatchChoice s) {
                            isChoiceTime = true;
                            actualToDoChoice = s;
                            writeUser.println(actualToDoChoice);
                            writeUser.flush();
                            while (isChoiceTime) {
                            }
                        }

                        System.out.println("Before DataPlayerChoice");

                        do {

                            obj = readSocket.readObject();

                            if (obj instanceof MatchDataInterface) {
                                if ( player == null ) {
                                    matchView = (MatchDataInterface) obj;
                                    writeUser.println(matchView.getErrorMessage());
                                    writeUser.println(matchView.getChoice().toString());
                                    writeUser.flush();
                                    isChoiceTime = true;
                                    actualToDoChoice = matchView.getChoice();
                                }
                            }

                            if ( actualToDoChoice instanceof CardChoice )
                                matchCompletelyCreated=true;

                        } while(!matchCompletelyCreated);


                        writeUser.println(matchView.getChoice().toString());
                        writeUser.flush();

                        /*if(matchView.showCurrentPlayer().equals(player)) {
                            isChoiceTime = true;
                            actualToDoChoice = matchView.getChoice();
                            writeUser.println(actualToDoChoice);
                        }*/

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
