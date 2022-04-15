package model;

import model.FigureCards.*;
import model.exception.CardNotFoundException;
import model.exception.InsufficientCoinException;

import java.util.*;

public class ExpertMatch extends Match implements ExpertMatchInterface{
    private final Set<FigureCard> figureCards;
    private boolean centaurEffect;


    private static final int FIGURECARDSTOTALNUM=8;
    private static final int FIGURECARDSINGAME=3;

    public ExpertMatch(int totalPlayersNum) {
        super(totalPlayersNum, true);
        centaurEffect =false;

        figureCards=new HashSet<>();

        try {
            while(figureCards.size()!=FIGURECARDSINGAME) {
                int randomInt = new Random().nextInt(FIGURECARDSTOTALNUM + 1);
                if (randomInt == 1 && !Knight.isAlreadyPlayed()) {
                    figureCards.add(new Knight());
                } else if (randomInt == 2 && !Jester.isAlreadyPlayed()) {
                    figureCards.add(new Jester());
                } else if (randomInt == 3 && !Merchant.isAlreadyPlayed()) {
                    figureCards.add(new Merchant());
                } else if (randomInt == 4 && !Postman.isAlreadyPlayed()) {
                    figureCards.add(new Postman());
                } else if (randomInt == 5 && !Princess.isAlreadyPlayed()) {
                    figureCards.add(new Princess());
                } else if (randomInt == 6 && !MushroomCollector.isAlreadyPlayed()) {
                    figureCards.add(new MushroomCollector());
                } else if (randomInt == 7 && !Witch.isAlreadyPlayed()) {
                    figureCards.add(new Witch());
                } else if (randomInt == 8 && !Centaur.isAlreadyPlayed()) {
                    figureCards.add(new Centaur());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public boolean isCentaurEffect() {
        return centaurEffect;
    }

    public void setCentaurEffect(boolean centaurEffect) {
        this.centaurEffect = centaurEffect;
    }


    //....

    public void playFigureCard(FigureCard card) throws CardNotFoundException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException {
        if(figureCards.contains(card)){
            showCurrentPlayerDashboard().removeCoin(card.getPrice());
            card.playCard(this);
            card.pricePlusPlus();
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
