package view;

import model.MatchView;
import model.Player;
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
    }

    public void choiceUser(Choice choice) {
        connection.send(choice.toString());
    }



    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof MatchView){
            connection.send(o.toString());
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
        if (arg instanceof FigureCard) {
            FigureCard figureCardtmp = (FigureCard) arg;
            notifyObservers(figureCardtmp);
        }
        if (arg instanceof Integer) {
            notifyObservers((Integer) arg);
        }
        if (arg instanceof Cloud) {
            Cloud cloudTmp = (Cloud) arg;
            notifyObservers(cloudTmp);
        }
    */