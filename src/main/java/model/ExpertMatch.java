package model;

import model.FigureCards.*;
import model.exception.*;

import java.util.*;

public class ExpertMatch extends Match implements ExpertMatchInterface{
    private final Set<FigureCard> figureCards;
    private boolean centaurEffect;
    private int postManValue = 0;
    private int colorBlocked = -1;

    private static final int FIGURECARDSTOTALNUM=8;
    private static final int FIGURECARDSINGAME=3;

    public ExpertMatch(int totalPlayersNum) {
        super(totalPlayersNum, true);
        centaurEffect =false;

        figureCards=new HashSet<>();


        try {
            while(figureCards.size()!=FIGURECARDSINGAME) {
                int randomInt = new Random().nextInt(FIGURECARDSTOTALNUM +1);
                if (randomInt == 1 && !figureCards.contains(new Knight())) {
                    figureCards.add(new Knight());
                } else if (randomInt == 2 && !figureCards.contains(new Jester())) {
                    figureCards.add(new Jester());
                } else if (randomInt == 3 && !figureCards.contains(new Merchant())) {
                    figureCards.add(new Merchant());
                } else if (randomInt == 4 && !figureCards.contains(new Postman())) {
                    figureCards.add(new Postman());
                } else if (randomInt == 5 && !figureCards.contains(new Princess())) {
                    figureCards.add(new Princess());
                } else if (randomInt == 6 && !figureCards.contains(new MushroomCollector())) {
                    figureCards.add(new MushroomCollector());
                } else if (randomInt == 7 && !figureCards.contains(new Witch())) {
                    figureCards.add(new Witch());
                } else if (randomInt == 8 && !figureCards.contains(new Centaur())) {
                    figureCards.add(new Centaur());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //OVERRIDED FROM MATCH

    @Override
    public boolean setNextCurrDashboard(){
        centaurEffect=false;
        Island.setCentaurEffect(centaurEffect);
        if(colorBlocked != -1){
            Color.values()[colorBlocked].unlockColor();
            colorBlocked = -1;
        }
        return super.setNextCurrDashboard();
    }

    @Override
    public void moveMotherNature(int posizioni) throws NoIslandException, SameInfluenceException, NoMoreBlockCardsException, MaxNumberException {
        if(posizioni < currentPlayerDashboard.getCurrentCard().getMovementValue() + postManValue){
            int positionTmp = currentIsland;
            islands.get(positionTmp).setMotherNature(false);
            for (int i = 0; i < posizioni; i++){
                positionTmp = nextIsland(positionTmp);
            }
            currentIsland = positionTmp;
            islands.get(currentIsland).setMotherNature(true);
            if(!islands.get(currentIsland).checkForbidden())
                changeTowerColorOnIsland();
            else
                Witch.addBlockCard();
        }
        else throw new MaxNumberException("Cannot move Mother nature that far");
        postManValue = 0;
    }




    //ONLY EXPERT METHODS:

    public boolean isCentaurEffect() {
        return centaurEffect;
    }

    public void setCentaurEffect(boolean centaurEffect) {
        this.centaurEffect = centaurEffect;
        Island.setCentaurEffect(centaurEffect);
    }

    public void playFigureCard(FigureCard card) throws CardNotFoundException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException {
        if(figureCards.contains(card)){
            currentPlayerDashboard.removeCoin(card.getPrice());
            card.playCard(this);
            card.pricePlusPlus();
        }else throw new CardNotFoundException("This figure card isn't playable in this match...");
    }

    @Override
    public void setIslandBlocked(int islandPosition) {
        islands.get(islandPosition).setForbidden(true);
    }

    @Override
    public void setPostManValue() {
        postManValue = 2;
    }

    @Override
    public void setIsKnight() {
        currentPlayerDashboard.setKnight(true);
    }

    @Override
    public Dashboard checkDashboardWithMoreInfluence() throws SameInfluenceException, CardNotFoundException {
        ArrayList<Dashboard> dashboardListTmp = (ArrayList<Dashboard>)dashboardsCollection;
        int dasboardInfluencer, knightEffect = 0;
        dasboardInfluencer = 0;
        Boolean exception = false;
        int influenceTmp = islands.get(currentIsland).getInfluenceByDashboard(dashboardsCollection.get(dasboardInfluencer));
        for (int i = 1; i < dashboardsCollection.size(); i++){
            if(dashboardListTmp.get(i).hasKnightPrivilege())
                knightEffect = 2;
            if (influenceTmp < islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(i)) + knightEffect){
                dasboardInfluencer = i;
                influenceTmp = islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(i));
                exception = false;
            }
            else if(influenceTmp == islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(i)))
                exception = true;
            knightEffect = 0;
        }
        if (exception)
            throw new SameInfluenceException("No change needed in current island");
        return dashboardsCollection.get(dasboardInfluencer);
    }

    public List<FigureCard> showFigureCardsInGame(){
        return new ArrayList<>(figureCards);
    }

    public void notifyStudentsOnFigureCard(FigureCardWithStudents figureCard){
        this.setChanged();
        notifyObservers(figureCard); //TODO vedremo se basta così
    }

    public void notifyIslandFigureCard(FigureCard figureCard){
        this.setChanged();
        notifyObservers(figureCard); //TODO vedremo se basta così
    }

    //Island position value doesn't matter if isn't used
    public void takeStudentsOnFigureCard(Set<Student> chosenStudents, FigureCardWithStudents figureCard, int islandPosition) throws MaxNumberException, InexistentStudentException, StudentIDAlreadyExistingException, WrongColorException, NoMoreStudentsException {
        if(figureCard.takeStudents(chosenStudents)){
            if(figureCard instanceof Jester)
                currentPlayerDashboard.moveToEntrance(chosenStudents);
            if(figureCard instanceof Princess)
                currentPlayerDashboard.moveToDR(chosenStudents);
            if(figureCard instanceof Merchant)
                islands.get(islandPosition).addStudent(chosenStudents.stream().toList().get(0));
        }
    }

    public void placeForbiddenCards(int islandToSetForbidden) throws NoIslandException, NoMoreBlockCardsException {
        if(islandPositions.contains((Integer) islandToSetForbidden)){
            islands.get(islandToSetForbidden).setForbidden(true);
            Witch.removeBlockCard();
        }
        else throw new NoIslandException("Insert an island that exists");
    }

    public void blockColorForInfluence(Color color){
        colorBlocked = color.ordinal();
        color.lockColor();
    }





    //ONLY FOR TESTING
    public void addCoinToCurrPlayer(){
        currentPlayerDashboard.addCoin();
    }



}
