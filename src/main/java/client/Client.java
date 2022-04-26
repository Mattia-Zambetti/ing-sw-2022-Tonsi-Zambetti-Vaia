package client;


import view.choice.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private final int port;
    private final String ip;
    private Choice actualToDoChoice;

    private Socket clientSocket;




    private boolean isActive=true;
    private boolean isChoiceTime;

    public Client(String ip, int port){
        this.port=port;
        this.ip=ip;
        isChoiceTime=false;
    }

    public void handleChoice(String choice){
        if(actualToDoChoice instanceof CardChoice){
            ((CardChoice)actualToDoChoice).setChosenCard(Integer.parseInt(choice));
            isChoiceTime=false;
        }else if(actualToDoChoice instanceof CloudChoice){
            ((CloudChoice) actualToDoChoice).setChosenCloudID(Integer.parseInt(choice));
            isChoiceTime=false;
        }else if(actualToDoChoice instanceof MoveMotherNatureChoice){
            ((MoveMotherNatureChoice) actualToDoChoice).setMovement(Integer.parseInt(choice));
            isChoiceTime=false;
        }else if(actualToDoChoice instanceof MoveStudentChoice) {
            switch (((MoveStudentChoice) actualToDoChoice).getNumChoice()) {
                case 0:
                    ((MoveStudentChoice) actualToDoChoice).setWhereToMove(choice);
                    ((MoveStudentChoice) actualToDoChoice).choiceplus();
                    break;
                case 1:
                    ((MoveStudentChoice) actualToDoChoice).setChosenStudent(Integer.parseInt(choice));
                    ((MoveStudentChoice) actualToDoChoice).choiceplus();
                    if (((MoveStudentChoice) actualToDoChoice).getWhereToMove().equals("dining room")) {
                        isChoiceTime = false;
                    }
                    break;
                case 2:
                    ((MoveStudentChoice) actualToDoChoice).setIslandID(Integer.parseInt(choice));
                    isChoiceTime = false;
                    break;

            }
        }else if(actualToDoChoice instanceof StartingMatchChoice) {
            switch (((StartingMatchChoice) actualToDoChoice).getNumChoice()) {
                case 0:
                    ((StartingMatchChoice) actualToDoChoice).setTotalPlayersNumMatch(Integer.parseInt(choice));
                    ((StartingMatchChoice) actualToDoChoice).choicePlusPlus();
                    break;
                case 1:
                    ((StartingMatchChoice) actualToDoChoice).setMatchType(Integer.parseInt(choice));
                    isChoiceTime = false;
                    break;

            }
        }
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
                String input=readUser.nextLine();
                while (isActive) {
                    try {

                        if (!isChoiceTime) {

                            writeUser.println("It's not your turn, please wait...");
                            writeUser.flush();
                            input=readUser.nextLine();
                        } else {
                            while (isChoiceTime) {
                                handleChoice(input);
                                input=readUser.nextLine();
                            }
                            outputStream.writeObject(actualToDoChoice);
                            outputStream.flush();
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
                try {
                    Object obj=readSocket.readObject();
                    writeUser.println(obj);
                    writeUser.flush();
                    if(obj instanceof Choice){
                        isChoiceTime=true;
                        actualToDoChoice=(Choice) obj;
                    }
                } catch ( ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    isActive=false;
                }
            }
        });
        t.start();
        return t;
    }
}
