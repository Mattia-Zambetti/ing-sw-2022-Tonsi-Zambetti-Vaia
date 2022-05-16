package client;


import controller.choice.CardChoice;
import controller.choice.Choice;
import controller.choice.DataPlayerChoice;
import controller.choice.StartingMatchChoice;
import model.MatchDataInterface;
import model.Message.Message;
import model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{

    private int idThis;
    private final int port;
    private final String ip;
    private Choice actualToDoChoice;

    private MatchDataInterface matchView;

    private Socket clientSocket;

    private Player player;

    private boolean isActive=true;
    private volatile boolean isChoiceTime;

    private boolean matchCompletelyCreated = false;

    private boolean isChanged=false;


    public Client(String ip, int port){

        player = new Player("none");
        this.port=port;
        this.ip=ip;
        isChoiceTime=false;
    }

    public int getIdThis() {
        return idThis;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setIdThis(int idThis) {
        this.idThis = idThis;
    }

    @Override
    public void run() {

        try {
            clientSocket = new Socket(ip, port);

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


                            if(isChoiceTime) {
                                writeUser.println(actualToDoChoice);
                                writeUser.flush();
                            }

                            if(!isChoiceTime) {
                                outputStream.writeObject(actualToDoChoice);
                                outputStream.flush();
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

                        if(obj instanceof Message){
                            ((Message)obj).manageMessage(Client.this);
                        }

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

                            if(!(matchView.getChoice() instanceof DataPlayerChoice) ||(matchView.getChoice() instanceof DataPlayerChoice && ((DataPlayerChoice) matchView.getChoice()).getPossessor()==idThis)) {
                                actualToDoChoice = matchView.getChoice();
                                isChanged=true;
                            }


                            if (actualToDoChoice instanceof CardChoice)
                                matchCompletelyCreated = true;
                            if(matchCompletelyCreated)
                                writeUser.println(matchView);


                            if((actualToDoChoice instanceof DataPlayerChoice && isChanged)
                                    ||(!(actualToDoChoice instanceof DataPlayerChoice) && matchView.showCurrentPlayer().equals(player)) ) {
                                writeUser.println(matchView.getErrorMessage());
                                writeUser.println(matchView.getChoice().toString());
                                writeUser.flush();
                                isChanged=false;
                                isChoiceTime = true;
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
