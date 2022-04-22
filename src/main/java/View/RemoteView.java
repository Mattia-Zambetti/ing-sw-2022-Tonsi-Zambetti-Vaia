package View;

import Server.Connection;
import model.*;
import model.FigureCards.FigureCard;
import model.exception.MaxNumberException;

import java.util.*;

public class RemoteView extends Observable implements Observer, utils.Observer<String> {
    private Player player;
    private Match match;
    private Server.Connection c;

    public RemoteView(Player player, Connection c){
        this.player = player;
        this.c = c;
    }



    public void chooseCard(){
        Set<Card> cardsTmp = match.showCards();
        c.send(cardsTmp.toString());
    }

    public void moveStudent(){
        Set<Student> studentsTmp = match.showCurrentPlayerDashboard().showEntrance();
        c.send(studentsTmp.toString());
    }

    public void chooseFigureCard(){
        List<FigureCard> figureCards = ((ExpertMatch) match).showFigureCardsInGame();
        c.send(figureCards.toString());
    }

    public void moveMotherNature(){
        int maxMovement = match.showCurrentPlayerDashboard().getCurrentCard().getMovementValue() + ((ExpertMatch) match).getPostManValue();
        c.send(""+ maxMovement);
    }

    public void chooseCloud(){
        c.send(match.toStringStudentsOnCloud());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Match){
            match = (Match) arg;
        }
        if(arg instanceof Card){
            Card cardChosenTmp = (Card) arg;
            notifyObservers(cardChosenTmp);
        }
        if(arg instanceof Student){
            Student studentChosen = (Student) arg;
            notifyObservers(studentChosen);
        }
        if(arg instanceof FigureCard){
            FigureCard figureCardtmp = (FigureCard) arg;
            notifyObservers(figureCardtmp);
        }
        if(arg instanceof Integer){
            notifyObservers((Integer) arg);
        }
        if(arg instanceof Cloud){
            Cloud cloudTmp = (Cloud) arg;
            notifyObservers(cloudTmp);
        }
    }

    @Override
    public void update(String message) {

    }
}
