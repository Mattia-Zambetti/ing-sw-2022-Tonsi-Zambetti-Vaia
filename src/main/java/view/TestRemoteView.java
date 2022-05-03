package view;

import model.MatchDataInterface;
import model.Player;
import model.figureCards.FigureCard;
import server.Connection;
import view.choice.Choice;

import java.util.*;

public class TestRemoteView extends RemoteView {
    private Player player;
    private List<String> inputString = new ArrayList<>();
    private boolean isChoiceTime = false;

    public TestRemoteView(Player player, Connection connection) {
        super(player, connection);
    }

    public TestRemoteView(Player player) {
        super(player,null);
    }

    public void setInputString(int index, String s) {
        inputString.add(index,s);
    }

    public synchronized void choiceUser(Choice choice) {
        isChoiceTime = true;
        int j = 0;
        while ( isChoiceTime ) {
            System.out.println(choice);
            isChoiceTime=choice.setChoiceParam(inputString.get(j));
            j++;
        }

        setChanged();
        notifyObservers((Choice)choice);

    }


    @Override
    public void sendError(String message){
        System.out.println(message);
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        /*if(o instanceof MatchView){
            if ( arg instanceof String)
                connection.send(arg);*/
        if(o instanceof MatchDataInterface){
            if ( arg instanceof String)
                System.out.println(arg);
        }else if (arg instanceof FigureCard) {
            setChanged();
            notifyObservers(arg);
        }
    }


}
