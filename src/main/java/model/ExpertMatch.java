package model;

import controller.choice.CloudChoice;
import graphicAssets.CLIgraphicsResources;
import model.figureCards.*;
import model.exception.*;

import java.io.Serializable;
import java.util.*;

public class ExpertMatch extends Match implements ExpertMatchInterface, Serializable {
    private final List<FigureCard> figureCards;
    private static boolean centaurEffect;
    private int postManValue = 0;
    private int colorBlocked = -1;

    private static final int FIGURECARDSTOTALNUM=8;
    private static final int FIGURECARDSINGAME=3;

    public ExpertMatch(int totalPlayersNum) {
        super(totalPlayersNum);
        centaurEffect =false;

        figureCards=new ArrayList<>();


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
                } else if (randomInt == 7 && !figureCards.contains(new GrannyGrass())) {
                    figureCards.add(new GrannyGrass());
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
    public String toString() {
        int i=1;
        StringBuilder res= new StringBuilder(super.toString());

        StringBuilder res2=new StringBuilder();

        res2.append("\nCharacter cards in this game:\n");
        for(FigureCard f: figureCards)
            res2.append(i++).append(".").append(f).append("\n");

        res2.append(CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_YELLOW);
        res2.append("\nPRESS THE COMMAND \"F\" TO PLAY A CHARACTER CARD(you can do this only in your turn)\n");
        res2.append(CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR);
        res.append(res2);

        return res.toString();
    }

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
    public void moveMotherNature(int posizioni) throws NegativeNumberOfTowerException, FinishedGameIslandException {
        try {
            if (posizioni <= currentPlayerDashboard.getCurrentCard().getMovementValue() + postManValue && posizioni > 0) {
                int positionTmp = currentIsland;
                islands.get(positionTmp).setMotherNature(false);
                for (int i = 0; i < posizioni; i++) {
                    positionTmp = nextIsland(positionTmp);
                }
                currentIsland = positionTmp;
                islands.get(currentIsland).setMotherNature(true);
                if (!islands.get(currentIsland).checkForbidden())
                    changeTowerColorOnIsland();
                else {
                    GrannyGrass.addBlockCard();
                    islands.get(currentIsland).setForbidden(false);
                }

                if (getISLANDSNUM() <= 3)
                    throw new FinishedGameIslandException("Island num <= 3, game over");

                if (super.totalNumIslands <= 3)
                    throw new FinishedGameIslandException("Island Num <= 3, game is over");

                if (matchFinishedAtEndOfRound)
                    throw new FinishedGameEndTurnException("Game over");

                choicePhase = new CloudChoice();
                notifyMatchObservers();
            } else throw new MaxNumberException("Cannot move Mother nature that far");

            postManValue = 0;
        }catch (Exceptions e){
            e.manageException(this);
        } catch (FinishedGameEndTurnException e) {
            e.printStackTrace();
        }
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
            figureCards.stream().toList().get(figureCards.stream().toList().indexOf(card)).playCard(this);
            card.pricePlusPlus();
        }else throw new CardNotFoundException("This figure card isn't playable in this match...");
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
        if(dashboardListTmp.get(0).hasKnightPrivilege())
            knightEffect = 2;
        int influenceTmp = islands.get(currentIsland).getInfluenceByDashboard(dashboardsCollection.get(dasboardInfluencer)) + knightEffect, influenceTmp1;
        knightEffect = 0;
        for (int i = 1; i < dashboardsCollection.size(); i++){
            if(dashboardListTmp.get(i).hasKnightPrivilege())
                knightEffect = 2;
            influenceTmp1 = islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(i)) + knightEffect;
            if (influenceTmp < influenceTmp1){
                dasboardInfluencer = i;
                influenceTmp = influenceTmp1;
                exception = false;
            }
            else if(influenceTmp == islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(i)))
                exception = true;
            knightEffect = 0;
        }
        if(currentPlayerDashboard.hasKnightPrivilege())
            currentPlayerDashboard.setKnight(false);
        if (exception)
            throw new SameInfluenceException("No change needed in current island -- Same influence");
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
        notifyObservers(figureCard); //TODO METTERE APPOSTO
    }


    //There's three overloading methods to manage figure with students different operations:

    public void takeStudentsOnFigureCard(Set<Student> chosenStudents, int islandPosition) throws MaxNumberException, InexistentStudentException, StudentIDAlreadyExistingException, WrongColorException, NoMoreStudentsException, NoIslandException {
        for (FigureCard f : figureCards) {
            if (f instanceof Merchant) {
                if (((Merchant) f).takeStudents(chosenStudents)) {
                    islands.get(islandPosition).addStudent(chosenStudents.stream().toList().get(0));
                }
            }
        }
    }

    public void takeStudentsOnFigureCard(Set<Student> chosenStudents, Set<Student> studentsFromEntrance)
            throws MaxNumberException, InexistentStudentException,
            StudentIDAlreadyExistingException, NoMoreStudentsException {

        if(chosenStudents.size()==studentsFromEntrance.size() &&
                currentPlayerDashboard.showEntrance().containsAll(studentsFromEntrance)) {
            for (FigureCard f : figureCards){
                if(f instanceof Jester){
                    if (((Jester)f).takeStudents(chosenStudents)) {
                        for (Student student : studentsFromEntrance)
                            currentPlayerDashboard.removeStudentFromEntrance(student);
                        currentPlayerDashboard.moveToEntrance(chosenStudents);
                        ((Jester)f).refillStudents(studentsFromEntrance);
                    }
                }
            }

        }else throw new MaxNumberException("Wrong parameters for this method(taking students from the jester)...");
    }




    public void takeStudentsOnFigureCard(Set<Student> chosenStudents) throws MaxNumberException, InexistentStudentException, StudentIDAlreadyExistingException, WrongColorException, NoMoreStudentsException {
       for (FigureCard f : figureCards) {
           if (f instanceof Princess){
               if (((Princess)f).takeStudents(chosenStudents)) {
                   currentPlayerDashboard.moveToDR(chosenStudents);
               }
           }
       }

    }

    public void placeForbiddenCards(int islandToSetForbidden) throws NoIslandException, NoMoreBlockCardsException {
        if(islandPositions.contains((Integer) islandToSetForbidden)){
            islands.get(islandToSetForbidden).setForbidden(true);
            GrannyGrass.removeBlockCard();
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
