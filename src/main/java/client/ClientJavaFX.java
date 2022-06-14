package client;

import controller.choice.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MatchDataInterface;
import model.Message.Message;
import model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;


public class ClientJavaFX extends Application implements Runnable,Client {


    private final Object outputStreamLock = new Object();
    private int idThis;
    private int port;
    private String ip;
    private Choice actualToDoChoice;

    private MatchDataInterface matchView;

    private Socket clientSocket;

    private Player player = new Player("none");

    private boolean isActive=true;
    private volatile boolean isChoiceTime;

    private ObjectOutputStream outputStream;


    private boolean figureCardNotPlayed = true;

    private boolean matchCompletelyCreated = false;


    private boolean isChanged=false;

    private static ControllerGUIInterface controllerGUI;

    private Parent root;

    private FXMLLoader fxmlLoader;

    private Choice actualToDoChoiceQueue;

    public static int wizardThisPlayer;

    public static int towerColorThisPlayer;

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setActualToDoChoiceQueue(Choice actualToDoChoiceQueue) {
        this.actualToDoChoiceQueue = actualToDoChoiceQueue;
    }

    public Choice getActualToDoChoiceQueue() {
        return actualToDoChoiceQueue;
    }

    public void setActualToDoChoice(Choice actualToDoChoice) {
        this.actualToDoChoice = actualToDoChoice;
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }


    public boolean isFigureCardNotPlayed() {
        return figureCardNotPlayed;
    }

    public void setFigureCardNotPlayed(boolean figureCardNotPlayed) {
        this.figureCardNotPlayed = figureCardNotPlayed;
    }

    public boolean isChoiceTime() {
        return isChoiceTime;
    }

    public Choice getActualToDoChoice() {
        return actualToDoChoice;
    }

    public MatchDataInterface getMatchView() {
        return matchView;
    }

    public boolean isMatchCompletelyCreated() {
        return matchCompletelyCreated;
    }

    @Override
    public void start(Stage primaryStage) {
        try {

            ControllerGUIInterface.setStage(primaryStage);
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("StartingTitle.fxml"));
            root = fxmlLoader.load();
            Scene scene = new Scene(root);

            controllerGUI = fxmlLoader.getController();
            controllerGUI.setClient(this);


            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        if ( clientSocket == null )
            return false;
        return clientSocket.isConnected();
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(ip, port);

            Thread threadSocket= this.readingFromSocket();
            Thread outputThread= this.sendChoiceThread();
            threadSocket.join();
            outputThread.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void setParam(String ip, int port){
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

    public void printToScreen(String s ) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controllerGUI.printMessageText(s);
            }
        }
        );
    }

    public Object getOutputStreamLock() {
        return outputStreamLock;
    }

    public Thread sendChoiceThread() throws IOException {
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while( isActive() ) {

                        synchronized ( outputStreamLock ) {
                            outputStreamLock.wait();
                        }

                        if ( isChoiceTime ) {
                            actualToDoChoice.setSendingPlayer(player);
                            isChoiceTime=false;
                            outputStream.writeObject(actualToDoChoice);
                            outputStream.flush();
                            outputStream.reset();
                        }


                    }
                } catch (IOException | NoSuchElementException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
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
        //ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {

                controllerGUI.setClient(ClientJavaFX.this);


                try {
                    while(isActive()) {

                        Object obj = readSocket.readObject();


                        if(obj instanceof Message){
                            ((Message)obj).manageMessage(ClientJavaFX.this);
                        }

                        if ( obj instanceof StartingMatchChoice s) {
                            isChoiceTime = true;
                            actualToDoChoice = s;

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        fxmlLoader = new FXMLLoader();
                                        controllerGUI.switchScene(s);

                                        controllerGUI = fxmlLoader.getController();


                                    }catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }
                        else if(obj instanceof MatchDataInterface){

                            matchView=(MatchDataInterface) obj;



                            if(!(matchView.getChoice() instanceof DataPlayerChoice) ||(matchView.getChoice() instanceof DataPlayerChoice && ((DataPlayerChoice) matchView.getChoice()).getPossessor()==idThis)) {
                                actualToDoChoice = matchView.getChoice();
                                isChanged=true; //Used to check if the player who has to set his data is te correct one
                            }


                            if (actualToDoChoice instanceof CardChoice && !matchCompletelyCreated) {
                                matchCompletelyCreated = true;
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            fxmlLoader = new FXMLLoader();
                                            controllerGUI.switchScene(actualToDoChoice);

                                            controllerGUI = fxmlLoader.getController();

                                            ((ControllerGUIGame)controllerGUI).inizializeAllDasboards(player, matchView.showAllPlayers());


                                        }catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            if ( actualToDoChoice instanceof DataPlayerChoice && isChanged ) {
                                isChanged=false;
                                isChoiceTime = true;
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            fxmlLoader = new FXMLLoader();
                                            controllerGUI.switchScene(actualToDoChoice);

                                            controllerGUI = fxmlLoader.getController();

                                        }catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }else if ( !(actualToDoChoice instanceof DataPlayerChoice) && matchView.showCurrentPlayer().equals(player) ) {
                                isChoiceTime=true;
                                if(actualToDoChoice instanceof CardChoice) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((ControllerGUIGame) controllerGUI).updateCardsPlayerView();
                                        }
                                    });
                                }

                            }
                            if ( matchCompletelyCreated ) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((ControllerGUIGame) controllerGUI).updateGameView();
                                    }
                                });
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