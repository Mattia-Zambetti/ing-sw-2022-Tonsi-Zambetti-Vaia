package client;

import controller.choice.CardChoice;
import controller.choice.Choice;
import controller.choice.DataPlayerChoice;
import controller.choice.StartingMatchChoice;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class ClientJavaFX extends Application implements Runnable,Client {


    private int idThis;
    private int port;
    private String ip;
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

    private ControllerGUIInterface controllerGUI;

    private Parent root;

    private FXMLLoader fxmlLoader;

    private List<String> allowedCommands = new ArrayList<>(){{add("f");add("x");}};

    @Override
    public void start(Stage primaryStage) {
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("StartingTitle.fxml"));
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            controllerGUI = fxmlLoader.getController();
            ((ControllerGUI)controllerGUI).setClient(this);


            primaryStage.setMaximized(true);
            primaryStage.setFullScreen(true);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(ip, port);

            Thread threadSocket= this.readingFromSocket();
            threadSocket.join();

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

    public void printToCLI( String s ) {
        PrintWriter writeUser=new PrintWriter(System.out);
        writeUser.println(s);
        writeUser.flush();
        writeUser.close();
    }


    public Thread readingFromSocket() throws IOException {
        PrintWriter writeUser=new PrintWriter(System.out);
        ObjectInputStream readSocket=new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {



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
                                        ((ControllerGUI)controllerGUI).switchScene(s);
                                        fxmlLoader = new FXMLLoader();
                                        fxmlLoader.setLocation(getClass().getResource("StartMatch.fxml"));
                                        root = fxmlLoader.load();

                                        controllerGUI = fxmlLoader.getController();
                                        controllerGUI.setClient(ClientJavaFX.this);
                                        ControllerGUIStartMatch.setChoice(((StartingMatchChoice)obj));

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