package model;

import controller.Controller;
import controller.choice.*;
import graphicAssets.CLIgraphicsResources;
import model.exception.*;
import model.figureCards.*;

import java.io.Serializable;
import java.util.*;

public class ExpertMatch extends Match implements ExpertMatchInterface, Serializable {
    private final List<FigureCard> figureCards;

    private boolean centaurEffect;
    private int postManValue = 0;
    private int colorBlocked = -1;

    private int blockCards;
    private static final int FIGURECARDSTOTALNUM=12;
    private static final int FIGURECARDSINGAME=3;

    public ExpertMatch(int totalPlayersNum)  {
        super(totalPlayersNum);
        centaurEffect =false;


        figureCards=new ArrayList<>();


        try {
            figureCards.add(new Minstrel());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            while(figureCards.size()!=FIGURECARDSINGAME) {
                int randomInt = new Random().nextInt(FIGURECARDSTOTALNUM + 1);
                //figureCards.add(figureCardsAvaiable.remove(randomInt-1));
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
                } else if (randomInt == 9 && !figureCards.contains(new Farmer())) {
                    figureCards.add(new Farmer());
                }else if (randomInt == 10 && !figureCards.contains(new Minstrel())) {
                    figureCards.add(new Minstrel());
                }else if (randomInt == 11 && !figureCards.contains(new Herald())) {
                    figureCards.add(new Herald());
                }else if (randomInt == 12 && !figureCards.contains(new Thief())) {
                    figureCards.add(new Thief());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        if(figureCards.contains(new GrannyGrass())){
            blockCards=GrannyGrass.getBLOCKCARDS();
        }
    }

    //OVERRIDED FROM MATCH


    /**it contains the additional part to the normal cli in an expert match(figure cards view)*/
    @Override
    public String toString() {
        int i=1;
        StringBuilder res= new StringBuilder(super.toString());

        StringBuilder res2=new StringBuilder();

        res2.append("\nCharacter cards in this game:\n");
        for(FigureCard f: figureCards) {
            res2.append("\n").append(i++).append(".").append(f).append("\n");
            if (f instanceof FigureCardWithStudents) {
                res2.append("Students on this card:\n");
                for (int j = 0; j < ((FigureCardWithStudents) f).getStudentsOnCard().size(); j++) {
                    res2.append((j + 1)).append(".").append(((FigureCardWithStudents) f).getStudentsOnCard().get(j)).append("\n");
                }
            } else if (f instanceof GrannyGrass) {
                res2.append("Number of block cards: ").append(blockCards).append("\n");
            }
        }


        res2.append(CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_YELLOW);
        if(!(choicePhase instanceof FigureCardActionChoice)
                && !(choicePhase instanceof CardChoice)
                && !(choicePhase instanceof  DataPlayerChoice))
        res2.append("\nPRESS THE COMMAND \"f\" TO PLAY A CHARACTER CARD(you can do this only in your turn)\n");
        res2.append(CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR);
        res.append(res2);

        return res.toString();
    }

    /**It adds to the overrided match method the possibility to reset the figure cards effect every turn*/
    @Override
    public boolean setNextCurrDashboard(){
        centaurEffect=false;
        Island.setCentaurEffect(centaurEffect);
        if(currentPlayerDashboard.isFarmerEffect())
            currentPlayerDashboard.setFarmerEffect(false);
        if(colorBlocked != -1){
            Color.values()[colorBlocked].unlockColor();
            colorBlocked = -1;
        }
        return super.setNextCurrDashboard();
    }

    /**It's an override of a match method that allows to use in the calculation the postman effect*/
    @Override
    public void moveMotherNature(int posizioni) throws FinishedGameIslandException, NoMoreTowerException, NoIslandException, TowerIDAlreadyExistingException, SameInfluenceException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, CardNotFoundException, MaxNumberOfTowerPassedException, NoMoreBlockCardsException, MaxNumberException {
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
                    blockCards++;
                    islands.get(currentIsland).setForbidden(false);
                }

                choicePhase = new CloudChoice();
                notifyMatchObservers();
            } else throw new MaxNumberException("Cannot move Mother nature that far");

            postManValue = 0;
    }




    //ONLY EXPERT METHODS:

    /**it's the getter to know if the current player has played the centaur figure card*/
    public boolean isCentaurEffect() {
        return centaurEffect;
    }

    /**It allows to set the centaur played from the current player in this turn*/
    public void setCentaurEffect(boolean centaurEffect) {
        this.centaurEffect = centaurEffect;
        Island.setCentaurEffect(centaurEffect);
    }

    /**It allows to play the figure card(param) in this turn if it is in the game. If this figure card is the granny grass, it check
     * there are others block cards(it sends to the current player an error in this case). In the end it calls the correct play card method
     * within the figure cards(strategy pattern) and it removes coins from the players and add 1 coin to the price of the chosen figure card.
     * If the card hasn't been found, it throws CardNotFoundException*/
    public void playFigureCard(FigureCard card) throws CardNotFoundException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException {
        if(figureCards.contains(card)){
            card = figureCards.get(figureCards.indexOf(card));
            if(card instanceof GrannyGrass && blockCards==0){
                setErrorMessage("You can't play this figure card now, please try later");
                setChoicePhase(Controller.getTmpChoice());
                setChanged();
                notifyObservers(card);
            }
            else {
                currentPlayerDashboard.removeCoin(card.getPrice());
                figureCards.stream().toList().get(figureCards.stream().toList().indexOf(card)).playCard(this);
                figureCards.stream().toList().get(figureCards.stream().toList().indexOf(card)).pricePlusPlus();
                setChanged();
                notifyObservers(card);
            }
        }else throw new CardNotFoundException("This figure card isn't playable in this match...");
    }

    /**It returns the numbre of remaining block cards on the granny grass(0 if there's no granny grass in the match)*/
    public int getRemainingBlockCards() {
        return blockCards;
    }

    public void checkAndMoveMasters() throws WrongColorException, NoMasterException {
        Dashboard maxStudentDashboard = null;
        Dashboard dashboardWithMaster = null;
        Dashboard dashboardWithSameNum = null;
        int maxStudents;
        for ( Color c: Color.values() ) {
            maxStudents = 0;
            for ( Dashboard d: dashboardsCollection ) {
                if ( d.haveMaster(c) ) {
                    if(maxStudentDashboard == null)
                        maxStudentDashboard = d;
                    dashboardWithMaster = d;
                    if(maxStudents < d.getStudentsNumInDR(c)){
                        maxStudents = d.getStudentsNumInDR(c);
                        maxStudentDashboard = d;
                    }
                    else if(maxStudents == d.getStudentsNumInDR(c) && !maxStudentDashboard.isFarmerEffect() && maxStudentDashboard != dashboardWithMaster){
                        maxStudents = d.getStudentsNumInDR(c);
                        maxStudentDashboard = d;
                    }
                }
                else if ( d.getStudentsNumInDR(c)>maxStudents ) {
                    maxStudents = d.getStudentsNumInDR(c);
                    maxStudentDashboard = d;
                }
                else if(d.getStudentsNumInDR(c)==maxStudents && d.isFarmerEffect())
                    maxStudentDashboard = d;
            }
            if ( maxStudentDashboard!=null && dashboardWithMaster==null ) {
                maxStudentDashboard.insertMaster(mastersMap.remove(c));
            }
            else if ( maxStudentDashboard!=null && dashboardWithMaster!=maxStudentDashboard ) {
                maxStudentDashboard.insertMaster(dashboardWithMaster.removeMaster(c));
            }
            else if (maxStudents == 0 && dashboardWithMaster!= null)
                mastersMap.put(c,dashboardWithMaster.removeMaster(c));
            dashboardWithMaster=null;
            maxStudentDashboard = null;
        }

    }

    /**It sets the value of the postman for the current player*/
    @Override
    public void setPostManValue() {
        postManValue = 2;
    }

    /**It returns true if the post man value is >0*/
    public boolean isPostManValue(){
        if(postManValue!=0)
            return true;
        return false;
    }

    /**It sets true the knight effect in the current player dashboard, then it sets the old choice phase, and it notifies the players */
    @Override
    public void setIsKnight() {
        currentPlayerDashboard.setKnight(true);
        setChoicePhase(Controller.getTmpChoice());
        notifyMatchObservers();
    }

    /**It sets true the farmer effect in the current player dashboard, then it sets the old choice phase, and it notifies the players*/
    public void setIsFarmer() throws NoMasterException, WrongColorException {
        currentPlayerDashboard.setFarmerEffect(true);
        checkAndMoveMasters();
        setChoicePhase(Controller.getTmpChoice());
        notifyMatchObservers();
    }

    @Override
    public Dashboard checkDashboardWithMoreInfluence() throws SameInfluenceException {
        ArrayList<Dashboard> dashboardListTmp = new ArrayList<>(dashboardsCollection);
        int dasboardInfluencer, knightEffect = 0;
        dasboardInfluencer = 0;
        Boolean exception = false;
        int influenceTmp,influenceTmp1;
        if(getTotalPlayersNum() != 4){
            if(dashboardListTmp.get(0).hasKnightPrivilege())
                knightEffect = 2;
            influenceTmp = islands.get(currentIsland).getInfluenceByDashboard(dashboardsCollection.get(dasboardInfluencer)) + knightEffect;
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
        }
        else{
            if(dashboardListTmp.get(0).hasKnightPrivilege() || dashboardListTmp.get(0).getBuddy().hasKnightPrivilege())
                knightEffect = 2;
            influenceTmp = islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(0)) + knightEffect;
            influenceTmp += islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(0).getBuddy());
            if(knightEffect == 2)
                knightEffect = 0;
            dashboardListTmp.remove(0);
            dashboardListTmp.remove(dashboardsCollection.get(0).getBuddy());
            if(dashboardListTmp.get(0).hasKnightPrivilege() || dashboardListTmp.get(0).getBuddy().hasKnightPrivilege())
                knightEffect = 2;
            influenceTmp1 = islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(0)) + knightEffect;
            influenceTmp1 += islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(1));

            if (influenceTmp < influenceTmp1){
                dasboardInfluencer = dashboardListTmp.get(0).getPlayerNumber();
                //influenceTmp = influenceTmp1;
            }

            if(currentPlayerDashboard.hasKnightPrivilege())
                currentPlayerDashboard.setKnight(false);

            if (influenceTmp == influenceTmp1)
                throw new SameInfluenceException("No change needed in current island");
        }

        return dashboardsCollection.get(dasboardInfluencer);
    }


    @Override
    public void moveStudentFromEntranceToDR( Student studentToBeMoved ) throws NoMasterException, WrongColorException {
        Student tmpStudent;
        try {
            tmpStudent = this.currentPlayerDashboard.removeStudentFromEntrance( studentToBeMoved );
            this.currentPlayerDashboard.moveToDR(tmpStudent);
            checkAndMoveMasters();
            if(currentPlayerDashboard.getStudentsNumInDR(studentToBeMoved.getColor()) % 3 == 0)
                currentPlayerDashboard.addCoin();
        }
        catch ( MaxNumberException | StudentIDAlreadyExistingException | InexistentStudentException | NullPointerException e ) {
            System.out.println(e.getMessage());
        }

        if(counterMoveStudents<chooseStudentsNumOnCLoud()-1){
            choicePhase=new MoveStudentChoice(showCurrentPlayerDashboard().showEntrance());
            counterMoveStudents++;
        }else {
            choicePhase = new MoveMotherNatureChoice();
            counterMoveStudents=0;
        }
        notifyMatchObservers();
    }

    /**It returns the figure cards in the game */
    public List<FigureCard> showFigureCardsInGame(){
        return new ArrayList<>(figureCards);
    }

    /**A method used to send to the players the new match with the request to do a choice for the chosen figure card*/
    public void notifyFigureCard(FigureCard figureCard){
        choicePhase = figureCard.getActualChoice();
        setChanged();
        notifyObservers(figureCard);
    }


    //There's three overloading methods to manage figure with students different operations:

    /**It's the first overload of this method (for the merchant figure card): it takes as param a set of chosen students(only one for the merchant)
     * and the chosen island; it takes the student from the merchant and add it to the chosen island;
     * in the end it sets the old choice and notifies the clients*/
    public void takeStudentsOnFigureCard(Set<Student> chosenStudents, int islandPosition) throws MaxNumberException, InexistentStudentException, StudentIDAlreadyExistingException, WrongColorException, NoMoreStudentsException, NoIslandException {
        for (FigureCard f : figureCards) {
            if (f instanceof Merchant) {
                if (((Merchant) f).takeStudents(chosenStudents)) {
                    islands.get(islandPosition).addStudent(chosenStudents.stream().toList().get(0));
                }
            }
        }
        setChoicePhase(Controller.getTmpChoice());
        notifyMatchObservers();
    }

    /**It's the second overload of this method (for the jester figure card): it takes as param a set of chosen students(from one to three for the jester)
     * and a set of students from the entrance; it checks if the params has the same size and if the studentsFromEntrance are in the entrance
     * (in case it throws MaxNumberException). Then it removes the studentsFromEntrance from the entrance and it puts the chosenStudents
     * in it. In the end it refills the jester figure card with the studentsFromEntrance and it sets the old choice notifying the clients */
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
            setChoicePhase(Controller.getTmpChoice());
            notifyMatchObservers();
        }else throw new MaxNumberException("Wrong parameters for this method(taking students from the jester)...");
    }


    public void takeStudentsOnFigureCard(Set<Student> chosenStudents) throws MaxNumberException, InexistentStudentException, StudentIDAlreadyExistingException, WrongColorException, NoMoreStudentsException, NoMasterException {
       for (FigureCard f : figureCards) {
           if (f instanceof Princess){
               if (((Princess)f).takeStudents(chosenStudents)) {
                   currentPlayerDashboard.moveToDR(chosenStudents);
                   checkAndMoveMasters();
               }
           }
       }
        setChoicePhase(Controller.getTmpChoice());
        notifyMatchObservers();
    }

    public void switchStudents(List<Student> fromEntrance, List<Student> fromDr) throws InexistentStudentException, MaxNumberException, StudentIDAlreadyExistingException, WrongColorException, NoMasterException {
        for(int i = 0; i < fromEntrance.size(); i++){
            currentPlayerDashboard.removeStudentFromEntrance(fromEntrance.get(i));
            currentPlayerDashboard.removeInDRbyStudentColor(fromDr.get(i));
            currentPlayerDashboard.moveToDR(fromEntrance.get(i));
            currentPlayerDashboard.addStudentToEntrance(fromDr.get(i));
            checkAndMoveMasters();
        }
        if(Controller.getTmpChoice() instanceof MoveStudentChoice)
            choicePhase=new MoveStudentChoice(showCurrentPlayerDashboard().showEntrance());
        else
            setChoicePhase(Controller.getTmpChoice());
        notifyMatchObservers();
    }

    /**It's used when a players choose the granny grass figure card; it takes as param the island position to block and sets it forbidden,
     * then it removes a block card(blockCard decrementing) and in the end it sets the old choice and notifies the clients.
     * It throws NoIslandException if the islandToSetForbidden doesn't exist*/
    public void placeForbiddenCards(int islandToSetForbidden) throws NoIslandException, NoMoreBlockCardsException {
        if(islandPositions.contains((Integer) islandToSetForbidden)){
            islands.get(islandToSetForbidden).setForbidden(true);
            GrannyGrass.removeBlockCard();
            blockCards--;
            setChoicePhase(Controller.getTmpChoice());
            notifyMatchObservers();
        }
        else throw new NoIslandException("Insert an island that exists");
    }


    public void calculateInfluenceOnChosenIsland(int chosenIsland) throws NoMoreTowerException, TowerIDAlreadyExistingException, SameInfluenceException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, CardNotFoundException, MaxNumberOfTowerPassedException, FinishedGameIslandException {
        int tmp = currentIsland;
        currentIsland = chosenIsland;
        changeTowerColorOnIsland();
        currentIsland = tmp;
        setChoicePhase(Controller.getTmpChoice());
        notifyMatchObservers();
    }

    /**It's used when a players choose the mushroom collector figure card; it locks the chosen
     * color by setting his boolean and in the end it sets the old choice and notifies the clients*/
    public void blockColorForInfluence(Color color){
        colorBlocked = color.ordinal();
        color.lockColor();
        setChoicePhase(Controller.getTmpChoice());
        notifyMatchObservers();
    }

    public void removeStudentsPerColor(Color color,int numStudentToRemove) throws WrongColorException, MaxNumberException, StudentIDAlreadyExistingException, NoMasterException {
        Set<Student> tmp = new HashSet<>();
        for(Dashboard d : dashboardsCollection){
            tmp.addAll(d.removeStudentFromDRbyColor(color,numStudentToRemove));
        }
        Bag.addStudents(tmp);
        checkAndMoveMasters();
        setChoicePhase(Controller.getTmpChoice());
        notifyMatchObservers();
    }


    /**It's used to add a coin to the current player*/
    public void addCoinToCurrPlayer(){
        currentPlayerDashboard.addCoin();
    }




}
