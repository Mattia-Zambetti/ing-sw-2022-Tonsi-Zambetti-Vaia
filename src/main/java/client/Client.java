package client;

//non va bene, il match viene mostrato due volte spesso, e la princess non modifica effettivamente il match

import controller.choice.*;
import model.ExpertMatch;
import model.MatchDataInterface;
import model.Message.ConfirmationMessage;
import model.Message.Message;
import model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client implements Runnable{

    private int idThis;
    private final int port;
    private final String ip;
    private Choice actualToDoChoice;

    private MatchDataInterface matchView;

    private Socket clientSocket;

    private Player player = new Player("none");

    private boolean isActive=true;
    private volatile boolean isChoiceTime;
    private boolean figureCardNotPlayed = true;

    private boolean matchCompletelyCreated = false;

    Choice actualToDoChoiceQueue;

    private boolean isChanged=false;

    private List<String> allowedCommands = new ArrayList<>(){{add("f");add("x");}};


    public Client(String ip, int port){
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

    public Player getPlayer() {
        return player;
    }

    public void setIdThis(int idThis) {
        this.idThis = idThis;
    }

    public void printToCLI( String s ) {
        PrintWriter writeUser=new PrintWriter(System.out);
        writeUser.println(s);
        writeUser.flush();
        writeUser.close();
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
                input=readUser.nextLine();
                try {
                    while (isActive()) {
                        if (!isChoiceTime) {
                            writeUser.println("Please wait your turn...");
                            writeUser.flush();
                        } else {

                            if (allowedCommands.contains(input)
                                    && matchView instanceof ExpertMatch
                                    && !(actualToDoChoice instanceof FigureCardActionChoice)
                                    && !(actualToDoChoice instanceof CardChoice)
                                    && !(actualToDoChoice instanceof DataPlayerChoice) && figureCardNotPlayed) {
                                Choice figureCardChoice = new FigureCardPlayedChoice(matchView.showFigureCardsInGame());
                                actualToDoChoiceQueue = actualToDoChoice;
                                actualToDoChoice = figureCardChoice;
                                figureCardNotPlayed = false;
                                actualToDoChoiceQueue.setSendingPlayer(player);
                                outputStream.writeObject(actualToDoChoiceQueue);
                                outputStream.flush();
                            } else { //Attenti a luiiiii

                                isChoiceTime = actualToDoChoice.setChoiceParam(input);
                            }

                            if (isChoiceTime) {
                                writeUser.println(actualToDoChoice.toString(matchView));
                                writeUser.flush();
                            } else {
                                if (actualToDoChoice instanceof CloudChoice)
                                    figureCardNotPlayed = true;
                                actualToDoChoice.setSendingPlayer(player);
                                outputStream.writeObject(actualToDoChoice);
                                outputStream.flush();
                            }
                        }
                        input = readUser.nextLine();
                    }
                }catch (IllegalStateException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                } finally {
                    writeUser.close();
                    readUser.close();
                    try {
                        outputStream.close();
                    } catch (IOException e) {
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



                try {
                    while(isActive()) {

                        Object obj = readSocket.readObject();

                        if(obj instanceof Message){
                            ((Message)obj).manageMessage(Client.this);
                        }

                        if ( obj instanceof StartingMatchChoice s) {
                            isChoiceTime = true;
                            actualToDoChoice = s;
                            writeUser.println(actualToDoChoice.toString(matchView));
                            writeUser.flush();
                        }
                        else if(obj instanceof MatchDataInterface){

                            matchView=(MatchDataInterface) obj;

                            if(!(matchView.getChoice() instanceof DataPlayerChoice) ||(matchView.getChoice() instanceof DataPlayerChoice && ((DataPlayerChoice) matchView.getChoice()).getPossessor()==idThis)) {
                                actualToDoChoice = matchView.getChoice();
                                isChanged=true;
                            }


                            if (actualToDoChoice instanceof CardChoice)
                                matchCompletelyCreated = true;
                            if(matchCompletelyCreated) {
                                writeUser.println(matchView);
                                writeUser.flush();
                            }


                            if((actualToDoChoice instanceof DataPlayerChoice && isChanged)
                                    ||(!(actualToDoChoice instanceof DataPlayerChoice) && matchView.showCurrentPlayer().equals(player)) ) {
                                writeUser.println(matchView.getErrorMessage());
                                writeUser.println(matchView.getChoice().toString(matchView));
                                writeUser.flush();
                                isChanged=false;
                                isChoiceTime = true;
                            }

                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException | NoSuchElementException e) {
                    e.printStackTrace();
                } finally {
                    writeUser.close();
                    try {
                        readSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        return t;
    }

    private synchronized boolean isActive() {
        return isActive;
    }

    private synchronized void setActive( boolean active) {
        isActive=active;
    }

    public void closeConnection() {
        setActive(false);
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
