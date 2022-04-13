package model;

import model.FigureCards.FigureCard;
import model.exception.CardNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class ExpertMatch extends Match implements ExpertMatchInterface{
    private Set<FigureCard> figureCards;

    public ExpertMatch(int totalPlayersNum){
        super(totalPlayersNum, true);
        figureCards=new HashSet<>();
        //TODO capire come randomizzare la creazione
    }




    //....

    public void playFigureCard(FigureCard card) throws CardNotFoundException {
        if(figureCards.contains(card)){
            card.playCard(this);
        }else throw new CardNotFoundException("This figure card isn't playable in this match...");
    }


    //TODO
    @Override
    public boolean getIsColorBlocked(Color colorBlocked) {
        return false;
    }

    @Override
    public Set<Student> moveFromBagToFigureCard(int studentsNum) {
        return null;
    }

    @Override
    public void setIslandBlocked(int islandPosition) {

    }
}
