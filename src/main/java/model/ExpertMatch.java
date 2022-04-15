package model;

import model.FigureCards.*;
import model.exception.*;

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
        return super.setNextCurrDashboard();

    }




    //ONLY EXPERT METHODS:

    public boolean isCentaurEffect() {
        return centaurEffect;
    }

    public void setCentaurEffect(boolean centaurEffect) {
        this.centaurEffect = centaurEffect;
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

    public List<FigureCard> showFigureCardsInGame(){
        return new ArrayList<>(figureCards);
    }

    public void notifyStudentsOnFigureCard(Set<Student> studentsOnFigureCard, FigureCardWithStudents figureCardWithStudents){
        notifyObservers(new HashSet<>(studentsOnFigureCard)); //TODO pensare a che tipo, bisogna sapere che carta l'ha
        // chiamato
    }

    //islandPosition==-1  if isn't the merchant
    public void takeStudentsOnFigureCard(Set<Student> chosenStudents, FigureCardWithStudents figureCard, int islandPosition) throws MaxNumberException, InexistentStudentException, StudentIDAlreadyExistingException, WrongColorException, NoMoreStudentsException {
        if(figureCard.takeStudents(chosenStudents)){
            if(figureCard instanceof Jester)
                currentPlayerDashboard.moveToEntrance(chosenStudents);
            if(figureCard instanceof Princess)
                currentPlayerDashboard.moveToDR(chosenStudents);
            if(figureCard instanceof Merchant)
                islands.get(islandPosition).addStudent(chosenStudents.stream().findAny().get());
        }
    }



    //ONLY FOR TESTING
    public void addCoinToCurrPlayer(){
        currentPlayerDashboard.addCoin();
    }



}
