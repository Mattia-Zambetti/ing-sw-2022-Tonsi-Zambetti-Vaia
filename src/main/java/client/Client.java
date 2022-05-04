package client;


import model.MatchDataInterface;
import model.Player;
import view.choice.CardChoice;
import view.choice.Choice;
import view.choice.DataPlayerChoice;
import view.choice.StartingMatchChoice;

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

    private Player player = new Player("none");

    private boolean isActive=true;
    private volatile boolean isChoiceTime;

    private boolean matchCompletelyCreated = false;

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
                                player=((DataPlayerChoice) actualToDoChoice).getPlayer();

                            if(isChoiceTime) {
                                writeUser.println(actualToDoChoice);
                                writeUser.flush();
                            }

                            if(!isChoiceTime) {
                                outputStream.writeObject(actualToDoChoice);
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

                        if ( obj instanceof StartingMatchChoice s) {
                            isChoiceTime = true;
                            actualToDoChoice = s;
                            writeUser.println(actualToDoChoice);
                            writeUser.flush();
                            while (isChoiceTime) {
                            }
                        }
                        else if(obj instanceof MatchDataInterface){

                            matchView=(MatchDataInterface) obj;
                            actualToDoChoice = matchView.getChoice();
                            if (actualToDoChoice instanceof CardChoice)
                                matchCompletelyCreated = true;
                            if(matchCompletelyCreated)
                                writeUser.println(matchView);

                            writeUser.println(matchView.getErrorMessage());
                            //writeUser.println(matchView.getChoice().toString());
                            writeUser.flush();
                            if(actualToDoChoice instanceof DataPlayerChoice || matchView.showCurrentPlayer().equals(player)){
                                isChoiceTime = true;
                                writeUser.println(matchView.getChoice().toString());
                                writeUser.flush();
                            }



                        }


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
