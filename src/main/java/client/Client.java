package client;


import view.choice.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private final int port;
    private final String ip;
    private Choice actualToDoChoice;



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
        }else if(actualToDoChoice instanceof MoveStudentChoice){
            switch (((MoveStudentChoice) actualToDoChoice).getNumChoice())
            {
                case 0:
                    ((MoveStudentChoice) actualToDoChoice).setWhereToMove(choice);
                    ((MoveStudentChoice) actualToDoChoice).choiceplus();
                case 1:
                    ((MoveStudentChoice) actualToDoChoice).setChosenStudent(Integer.parseInt(choice));
                    ((MoveStudentChoice) actualToDoChoice).choiceplus();
                    if(((MoveStudentChoice) actualToDoChoice).getWhereToMove().equals("dining room")){
                        isChoiceTime=false;
                    }
                case 2:
                    ((MoveStudentChoice) actualToDoChoice).setIslandID(Integer.parseInt(choice));
                    isChoiceTime=false;
            }

        }
    }



    @Override
    public void run() {

        try {
            Socket clientSocket = new Socket(ip, port);
            ObjectInputStream readObject=new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("You are in the game");
            Thread threadUser= this.readingFromUser();
            Thread threadSocket= this.readingFromSocket(readObject);
            threadUser.join();
            threadSocket.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    public Thread readingFromUser() {
        Scanner readUser= new Scanner(System.in);
        PrintWriter writeUser=new PrintWriter(System.out);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isActive) {
                    try {
                        if (!isChoiceTime) {
                            readUser.nextLine();
                            writeUser.println("It's not your turn, please wait...");
                            writeUser.flush();
                        } else
                            while (isChoiceTime) {
                                handleChoice(readUser.nextLine());
                                readUser.nextLine();
                            }
                    }catch (IllegalStateException e){
                        isActive=false;
                    }
                }
            }
        });
        t.start();
        return t;
    }

    public Thread readingFromSocket(ObjectInputStream readSocket){
        PrintWriter writeUser=new PrintWriter(System.out);

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
