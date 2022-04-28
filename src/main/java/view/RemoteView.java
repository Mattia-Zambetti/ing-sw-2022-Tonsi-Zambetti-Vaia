package view;

import model.MatchView;
import model.Player;
import model.figureCards.FigureCard;
import server.Connection;
import view.choice.Choice;

import java.util.Observable;
import java.util.Observer;

public class RemoteView extends Observable implements Observer {
    private Player player;
    private final Connection connection;

    public RemoteView(Player player, Connection connection) {
        this.player = player;
        this.connection = connection;
        connection.addObserver(this);
    }

    public synchronized void choiceUser(Choice choice) {
        choice=connection.sendAndReceive(choice);
        setChanged();
        notifyObservers((Choice)choice);
    }

    public Player getPlayer() {
        return player;
    }

    public void sendError(String message){
        connection.send(message);
    }



    @Override
    public synchronized void update(Observable o, Object arg) {
        if(o instanceof MatchView){
            connection.send(o.toString());
        }else if (arg instanceof FigureCard) {
            setChanged();
            notifyObservers(arg);
        }
        else if(o instanceof Connection && o==connection){
            setChanged();
            notifyObservers(arg);
        }


    }


}
    /*
        public void chooseCard(){
            Set<Card> cardsTmp = match.showCards();
            connection.send(cardsTmp.toString());
        }

        public void moveStudent(){
            Set<Student> studentsTmp = match.showCurrentPlayerDashboard().showEntrance();
            connection.send(studentsTmp.toString());
        }

        public void chooseFigureCard(){
            List<FigureCard> figureCards = ((ExpertMatch) match).showFigureCardsInGame();
            connection.send(figureCards.toString());
        }

        public void moveMotherNature(){
            int maxMovement = match.showCurrentPlayerDashboard().getCurrentCard().getMovementValue() + ((ExpertMatch) match).getPostManValue();
            connection.send(""+ maxMovement);
        }

        public void chooseCloud(){
            connection.send(match.toStringStudentsOnCloud());
        }




        if (arg instanceof Card) {
            Card cardChosenTmp = (Card) arg;
            notifyObservers(cardChosenTmp);
        }
        if (arg instanceof Student) {
            Student studentChosen = (Student) arg;
            notifyObservers(studentChosen);
        }

        if (arg instanceof Integer) {
            notifyObservers((Integer) arg);
        }
        if (arg instanceof Cloud) {
            Cloud cloudTmp = (Cloud) arg;
            notifyObservers(cloudTmp);
        }
    */