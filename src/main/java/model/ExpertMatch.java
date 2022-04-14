package model;

import model.FigureCards.*;
import model.exception.CardNotFoundException;

import java.util.*;

public class ExpertMatch extends Match implements ExpertMatchInterface{
    private final Set<FigureCard> figureCards;
    private static final int FIGURECARDSTOTALNUM=8;
    private static final int FIGURECARDSINGAME=3;

    public ExpertMatch(int totalPlayersNum) {
        super(totalPlayersNum, true);
        figureCards=new HashSet<>();

        try {
            while(figureCards.size()!=FIGURECARDSINGAME) {
                int randomInt = new Random().nextInt(FIGURECARDSTOTALNUM + 1);
                if (randomInt == 1 && !Cavaliere.isAlreadyPlayed()) {
                    figureCards.add(new Cavaliere());
                } else if (randomInt == 2 && !Giullare.isAlreadyPlayed()) {
                    figureCards.add(new Giullare());
                } else if (randomInt == 3 && !Mercante.isAlreadyPlayed()) {
                    figureCards.add(new Mercante());
                } else if (randomInt == 4 && !Postino.isAlreadyPlayed()) {
                    figureCards.add(new Postino());
                } else if (randomInt == 5 && !Principessa.isAlreadyPlayed()) {
                    figureCards.add(new Principessa());
                } else if (randomInt == 6 && !Fungaiolo.isAlreadyPlayed()) {
                    figureCards.add(new Fungaiolo());
                } else if (randomInt == 7 && !Sciura.isAlreadyPlayed()) {
                    figureCards.add(new Sciura());
                } else if (randomInt == 8 && !Vaia.isAlreadyPlayed()) {
                    figureCards.add(new Vaia());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }




    //....

    public void playFigureCard(FigureCard card) throws CardNotFoundException {
        if(figureCards.contains(card)){
            card.playCard(this);
        }else throw new CardNotFoundException("This figure card isn't playable in this match...");
    }




    @Override
    public void setIslandBlocked(int islandPosition) {
        islands.get(islandPosition).setForbidden(true);
    }

    public List<FigureCard> showFigureCardsInGame(){
        return new ArrayList<>(figureCards);
    }
}
