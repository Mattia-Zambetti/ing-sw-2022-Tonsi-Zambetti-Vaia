package model;

import model.FigureCards.FigureCard;
import model.exception.CardNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ExpertMatch implements MatchInterface{
    Match match;
    private Set<FigureCard> figureCards;

    public ExpertMatch(int totalPlayersNum){
        match= new Match(totalPlayersNum, true);
        figureCards=new HashSet<>();
        //TODO capire come randomizzare la creazione
    }

    @Override
    public Dashboard showCurrentPlayerDashboard() {
        return match.showCurrentPlayerDashboard();
    }

    @Override
    public void setIslandsStudents(int islandToSet, ArrayList<Student>[] students) {
        match.setIslandsStudents(islandToSet,students);
    }

    //....

    public void playFigureCard(FigureCard card) throws CardNotFoundException {
        if(figureCards.contains(card)){
            card.playCard(this);
        }else throw new CardNotFoundException("This figure card isn't playable in this match...");
    }
}
