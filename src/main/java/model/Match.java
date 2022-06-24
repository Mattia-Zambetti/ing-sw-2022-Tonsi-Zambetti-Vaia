//Tonsi, Zambo, Vaia
package model;

import graphicAssets.CLIgraphicsResources;
import model.Message.MatchEndedMessage;
import controller.choice.*;
import model.Message.PlayerSuccessfullyCreated;
import model.exception.*;
import model.figureCards.FigureCard;
import model.figureCards.NoMoreBlockCardsException;

import java.io.Serializable;
import java.util.*;
public class Match extends Observable implements MatchDataInterface, Serializable {
    protected List<Island> islands;
    private List<Cloud> clouds;
    protected List<Dashboard> dashboardsCollection; //The order of the player during the actual round is the same of the dashboard in this List
    protected Dashboard currentPlayerDashboard;
    protected HashMap<Color, Master> mastersMap;

    private String errorMessage = "";

    private int totalPlayersNum;
    protected int currentIsland;

    protected int totalNumIslands;
    protected final List<Integer> islandPositions = new ArrayList<>();
    private int towersNum;

    protected int counterMoveStudents=0;

    //utile definire tanti attributi così per avere codice facilmente modificabile
    private final int ISLANDSNUM=12;

    private static final int STUDENTSONCLOUD2PLAYERS= 3;
    private static final int STUDENTSONCLOUD3PLAYERS= 4;
    private static final int STUDENTSONCLOUD4PLAYERS= 3;

    private static final int MAXPLAYERSNUM=4;
    private static final int MINPLAYERSNUM=2;

    protected Choice choicePhase;

    protected boolean matchFinishedAtEndOfRound;
    private List<Player> winnerPlayers;

    //TESTED
    public Match(int totalPlayersNum) {
        try {
            this.totalPlayersNum = totalPlayersNum;
            if (this.totalPlayersNum <= MAXPLAYERSNUM && this.totalPlayersNum >= MINPLAYERSNUM) {

                for (TowerColor c: TowerColor.values()) {
                    c.resetCounter();
                }

                this.totalNumIslands=ISLANDSNUM; //TODO

                Bag.restoreBag();

                //per essere più precisi, a noi non serve sapere l'ordine totale ma solo la prossima/precedente, dovrebbe essere più
                // efficiente la linked
                islands = new ArrayList<>();
                initializeIslands();
                this.totalNumIslands=ISLANDSNUM;

                Bag.createAllStudents();
                Cloud.setStudentsNumOnCloud(chooseStudentsNumOnCLoud());



                clouds = new ArrayList<>();
                initializeClouds();

                setTowersNum();

                dashboardsCollection = new ArrayList<>();

                initializeMasters();



                matchFinishedAtEndOfRound = false;
                winnerPlayers = new ArrayList<>();

            } else throw new MaxNumberException("A match can have only from 2 to 4 players");
        }catch (MaxNumberException | NoMoreStudentsException e){
            System.out.println(e.getMessage());
        }
    }

    //TONSI

    /**it allows to set the actual phase in the turn by setting the choice(different choice= different phase)*/
    public void setChoicePhase(Choice choicePhase) {
        this.choicePhase = choicePhase;
    }

    /**it returns a copy of the player in this turn (current player)*/
    public Player showCurrentPlayer() {
        try{
            return showCurrentPlayerDashboard().getPlayer();
        }catch (NullPointerException e){
            return new Player("error");
        }
    }

    /** it returns the current players number in the match*/
    public int getCurrentPlayersNum() {
        return dashboardsCollection.size();
    }

    /**it returns the max number of players in a match*/
    public static int getMAXPLAYERSNUM() {
        return MAXPLAYERSNUM;
    }

    /**it returns the minimum number of players in a match*/
    public static int getMINPLAYERSNUM() {
        return MINPLAYERSNUM;
    }

    /**It returns the final players' number in this match(a match of totalPlayersNum players)*/
    public int getTotalPlayersNum() {
        return totalPlayersNum;
    }

    /**It returns the students number on the clouds, used in the constructor*/
    public int chooseStudentsNumOnCLoud() {
        if(totalPlayersNum ==2){
            return STUDENTSONCLOUD2PLAYERS;
        }else if(totalPlayersNum ==3)
            return STUDENTSONCLOUD3PLAYERS;
        else return STUDENTSONCLOUD4PLAYERS;
    }

    /**It's used in the constructor, it creates the clouds by using the player's number*/
    private void initializeClouds() {
        for (int i = 0; i < totalPlayersNum; i++) {
            clouds.add(new Cloud(i));
        }
    }

    /**TESTED
    *It's used in the constructor, it creates the islands*/
    private void initializeIslands() throws NoMoreStudentsException {
        boolean motherNature=true;
        currentIsland = 0;
        for (int i=0; i< ISLANDSNUM; i++){
            islands.add(new Island(motherNature, i));
            islandPositions.add(i);
            //islands.add(new Island(motherNature, i+1));
            if( i!=0 && i!=(getISLANDSNUM()/2) )
                islands.get(i).addStudent(Bag.removeStudent());
            motherNature=false;
        }
    }

    /** TESTED
    It's used to take the students from the bag and
    *put them into the cloud number "cloudNum"*/
    private Set<Student> pullStudentsFromCloud(int cloudNum) throws WrongCloudNumberException {
        if (cloudNum < totalPlayersNum && cloudNum >= 0 ) {
            return getCloudByID(cloudNum).takeStudents();
        }
        throw new WrongCloudNumberException("wrong cloud's number");
    }

    /**It returns the IDth cloud
     * It throws WrongCloudNumberException if the id doesn't exist
     */
    private Cloud getCloudByID(int ID) throws WrongCloudNumberException {
        for ( Cloud c: clouds ) {
            if ( c.getID() == ID )
                return c;
        }
        throw new WrongCloudNumberException("Wrong cloud's number");
    }

    /** It returns a copy of the clouds list*/
    public List<Cloud> getClouds() {
        List<Cloud> cloudsCopy=new ArrayList<>();
        for (Cloud c: clouds){
            cloudsCopy.add(new Cloud(c));
        }
        return cloudsCopy;
    }

    /**TESTED
    *It's used at the start of a round to refill every cloud
    *with new students from the bag*/
    public void refillClouds(){
        for (Cloud c : clouds) {
            c.setCloudNotChosen();
            try {
                c.refillCloud(Bag.removeStudents(Cloud.getStudentsNumOnCloud()));
            } catch (AlreadyFilledCloudException | MaxNumberException e) {
                System.out.println(e.getMessage());
            } catch ( NoMoreStudentsException e ) {
                e.manageException(this);
            }
        }

    }


    /**TESTED
    *The param chosenCLoud require to contain the choice starting from 0, the method
    *takes the students from the cloud "chosenCloud" and put them into the current player entrance*/
    public void moveStudentsFromCloudToEntrance(int chosenCloud) throws WrongCloudNumberException, MaxNumberException, FinishedGameEndTurnException, NoMoreStudentsException {
        try {

            if (chosenCloud < totalPlayersNum && chosenCloud >= 0 )
                currentPlayerDashboard.moveToEntrance(pullStudentsFromCloud(chosenCloud));
            else
                throw new WrongCloudNumberException("This cloud doesn't exist");


            if(!setNextCurrDashboard()){
                if ( matchFinishedAtEndOfRound )
                    throw new FinishedGameEndTurnException("Game ended");
                choicePhase=new CardChoice(showCurrentPlayerDashboard().showCards());
                refillClouds();
            }else
                choicePhase=new MoveStudentChoice(showCurrentPlayerDashboard().showEntrance());

            notifyMatchObservers();



        }catch (StudentIDAlreadyExistingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /** It returns the current cards number, the current cards are initialized with every attribute to 0, so it count the card only
     * if its id is 0*/
    public int getSizeCurrentCards(){
        int counter=0;
        for(Card c: showAllCurrentCards()){
            if(c.getId()!=0){
                counter++;
            }
        }
        return counter;
    }

    /** TESTED
    *it returns the string version of the clouds content*/
    public String toStringStudentsOnCloud() {
        StringBuilder res = new StringBuilder();
        for (Cloud c : clouds) {
            res.append(c.toString());
        }
        return res.toString();
    }

   /*
    public List<Cloud> showClouds(){
        List<Cloud> res=new ArrayList<>();
        for (Cloud c:clouds) {
            res.add(new Cloud(c));
        }
        return res;
    }*/

    /** it returns a copy of the cards in the current player's deck*/
    public Set<Card> showCards(){
        return currentPlayerDashboard.showCards();
    }


    /**it returns 0 if it's a match without figure cars(without granny grass)*/
    @Override
    public int getBlockCards() {
        return 0;
    }

    /**it returns false if it's a match without figure cars(without postman)*/
    @Override
    public boolean isPostManValue() {
        return false;
    }


    /**It sets the chosen card param as a current card if no one has played this card in this or if it has no possibility to do
     * another choose. it throws CardAlreadyPlayedException if it can't be setted as the current card*/
    public void chooseCard(Card chosenCard) throws CardNotFoundException, CardAlreadyPlayedException {
        boolean isAlreadyPlayed=false;

        /*It checks if the card has already played from one of the player before the current one*/
        for (Dashboard d: dashboardsCollection) {
            if ( dashboardsCollection.indexOf(d) < dashboardsCollection.indexOf(currentPlayerDashboard) ) {
                if ( chosenCard.equals(d.getCurrentCard())) {
                    isAlreadyPlayed=true;
                    break;
                }
            }
        }

        if(isAlreadyPlayed && isThereAnotherCard()){
            throw new CardAlreadyPlayedException("This card has already been chosen by another player");
        }


        currentPlayerDashboard.playChosenCard(chosenCard);

        //if the deck is empty it set that is the last round
        if(currentPlayerDashboard.showCards().size()==0 && currentPlayerDashboard.equals(dashboardsCollection.get(0))){
            setMatchFinishedAtEndOfRound();
        }


        //it chooses the next phase in the match, it can be cardChoice for the next player or the moveStudentChoice for the first
        //player in the order
        if(!setNextCurrDashboard()){
            setDashboardOrder();
            choicePhase=new MoveStudentChoice(showCurrentPlayerDashboard().showEntrance());
        }else
            choicePhase=new CardChoice(showCurrentPlayerDashboard().showCards());

        notifyMatchObservers();

    }

    /**it return true if the player could choose a different card from the other player's current cards. It check only the current
    *card of the players before the current player*/
    public boolean isThereAnotherCard(){
        boolean isFree=true;
        for (Card c: currentPlayerDashboard.showCards()) {
            for (int i=0; i<dashboardsCollection.indexOf(currentPlayerDashboard); i++) {
                if(c.equals(dashboardsCollection.get(i).getCurrentCard()))
                    isFree=false;
            }
            if(isFree)
                return true;
            isFree=true;
        }
        return false;
    }

    /**this method takes the players data and set them into the match. It checks if they are correct and if the player could
    *choose these data. If data are wrong, it throws WrongDataplayerException/WrongColorException. it finally create the player dashboard
     *in the match and notify the change to the players in the lobby */
    public void addPlayer(String nickname, String towerColor, String wizard, int id) throws WrongColorException, WrongDataplayerException, NoMoreStudentsException, MaxNumberException {

        if(choicePhase instanceof DataPlayerChoice)
            ((DataPlayerChoice)choicePhase).setPossessor(id);


        if (dashboardsCollection.size() < totalPlayersNum) {

            //It checks if the players choose the grey tower color in a different match than the match with 3 players
            if ((totalPlayersNum == 2 || totalPlayersNum == 4) && towerColor.toString().equals("GREY")) {
                throw new WrongColorException("You can't choose this color...");
            }

            //it checks for each player in the lobby if it has the same nickname or wizard(params of the method)
            for (Dashboard player : dashboardsCollection) {
                if (player.getPlayer().getNickname().equals(nickname))
                    throw new WrongDataplayerException("This nickname has been already chosen");
                if (player.getWizard().toString().equals(wizard))
                    throw new WrongDataplayerException("This wizard has already chosen");
            }


            //it checks for each player in the lobby if it has the same tower color; in the match with 4 players, it checks
            //if there's already two players with this tower color param
            if (totalPlayersNum < MAXPLAYERSNUM) {
                for (Dashboard player : dashboardsCollection) {
                    if (player.getTowerColor().toString().equals(towerColor))
                        throw new WrongDataplayerException("This tower color has been already chosen");
                }
            } else {
                for (Dashboard player : dashboardsCollection) {
                    if (player.getTowerColor().toString().equals(towerColor) && TowerColor.valueOf(towerColor).getCounter() == 2)
                        throw new WrongDataplayerException("This tower color has been already chosen");
                }
            }

            //it creates the new dashboard with che correct number of tower by watching the number of players
            if (totalPlayersNum < MAXPLAYERSNUM) {
                dashboardsCollection.add(new Dashboard(towersNum, TowerColor.valueOf(towerColor), Wizard.valueOf(wizard), nickname, dashboardsCollection.size()));
                setChanged();
                notifyObservers(new PlayerSuccessfullyCreated(new Player(nickname), id));
            } else if (TowerColor.valueOf(towerColor).getCounter() == 0) {
                dashboardsCollection.add(new Dashboard(towersNum, TowerColor.valueOf(towerColor), Wizard.valueOf(wizard), nickname, dashboardsCollection.size()));
                TowerColor.valueOf(towerColor).counterplus();
                setChanged();
                notifyObservers(new PlayerSuccessfullyCreated(new Player(nickname), id));
            } else {
                Dashboard tmp = new Dashboard(0, TowerColor.valueOf(towerColor), Wizard.valueOf(wizard), nickname, dashboardsCollection.size());
                Dashboard buddy = null;
                for (Dashboard player : dashboardsCollection) {
                    if (player.getTowerColor().toString().equals(towerColor)){
                        player.setBuddy(tmp);
                        buddy = player;
                    }
                }
                tmp.setBuddy(buddy);
                dashboardsCollection.add(tmp);

                TowerColor.valueOf(towerColor).counterplus();
                setChanged();
                notifyObservers(new PlayerSuccessfullyCreated(new Player(nickname), id));
            }

            //it starts the match when every player in the match has already chosen his data
            if (totalPlayersNum == dashboardsCollection.size()) {
                initializeAllEntrance();
                choicePhase = new CardChoice(currentPlayerDashboard.showCards());
                refillClouds();
                notifyMatchObservers();
            }

            //it sets the current player
            if (dashboardsCollection.size() == 1) {
                currentPlayerDashboard = dashboardsCollection.get(0);
            }
        } else throw new MaxNumberException("Max players number reached...");

    }

    /**it sets the towers number in the match by choosing using the players number for this match*/
    private void setTowersNum() {
        if (totalPlayersNum == 2) {
            towersNum = 8;
        } else if (totalPlayersNum == 3) {
            towersNum = 6;
        } else if (totalPlayersNum == 4)
            towersNum = 8;

    }

    /**it returns a copy of the current player dashboard*/
    public Dashboard showCurrentPlayerDashboard() throws NullPointerException {
        return new Dashboard(currentPlayerDashboard);
    }

    //END TONSI

    //ZAMBO

    /** Returns a copy of all dashboards linked with the player nickname
     */
    public Map<String, Dashboard> showAllDashboards() {
        Map<String, Dashboard> dashboardsMap = new HashMap<>();
        for ( Dashboard d: dashboardsCollection ) {
            dashboardsMap.put(d.getPlayer().getNickname(), new Dashboard(d));
        }
        return dashboardsMap;
    }

    public void notifyMatchObservers() {
        setChanged();
        notifyObservers(this);
    }

    public int getISLANDSNUM() {
        return ISLANDSNUM;
    }

    private void initializeMasters() {
        mastersMap=new HashMap<>();
        for (Color c : Color.values()) {
            mastersMap.put(c, new Master(c));
        }
    }

    //il metodo muove gli studenti scelti dall'ingresso alla dining room, non serve passare dashboard perché si basa su CurrentDashboard
    public void moveStudentFromEntranceToDR( Student studentToBeMoved ) throws NoMasterException, WrongColorException {
        Student tmpStudent;


        try {
            tmpStudent = this.currentPlayerDashboard.removeStudentFromEntrance( studentToBeMoved );
            this.currentPlayerDashboard.moveToDR(tmpStudent);
            checkAndMoveMasters();


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

    //Useless, we use only indexes to chose Island
    /*public void moveStudentFromEntranceToIsland( Student chosenStudent, Island chosenIsland ) throws NoIslandException {
        try {
            Student tmpStudent = this.currentPlayerDashboard.removeStudentFromEntrance(chosenStudent);
            for ( Island isl : islands) {
                if (isl.equals(chosenIsland)) {
                    isl.addStudent(tmpStudent);
                    return;
                }
            }
            throw new NoIslandException("Island not found, moveStudentFromEntranceToIsland failed");
        }
        catch ( InexistentStudentException | NullPointerException e ) {
            System.out.println(e.getMessage());
        }
    }*/

    public void moveStudentFromEntranceToIsland( Student chosenStudent, int chosenIslandPosition ) throws NoIslandException {
        try {
            if ( !islandPositions.contains(chosenIslandPosition) )
                throw new NoIslandException("Chosen island doesn't exist, operation failed, please try again");
            if ( this.islands.get(chosenIslandPosition) == null )
                throw new NoIslandException("Chosen island is null, please try again");
            else {
                this.islands.get(chosenIslandPosition).addStudent(currentPlayerDashboard.removeStudentFromEntrance(chosenStudent));

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
        catch (InexistentStudentException | NullPointerException e ) {
            System.out.println(e.getMessage());
        }

    }

    //This method set the next dashboard (and so the player) that has to play, if there is a next player it notifies the player and after return true, if there are no more player
    //it returns false without notifying any player, in the planning phase if it's returned false the controller has to call the setDashboardOrder method
    public boolean setNextCurrDashboard() {
        if ( ! (dashboardsCollection instanceof ArrayList<Dashboard>) )
            throw new IllegalArgumentException("DashboardCollection is not an ArrayList");
        int currentPlayerPosition = dashboardsCollection.indexOf(this.currentPlayerDashboard);
        if ( currentPlayerPosition < (this.dashboardsCollection.size()-1) ) {
            currentPlayerPosition++;
            this.currentPlayerDashboard = dashboardsCollection.get(currentPlayerPosition);

            //setChanged();
            //notifyObservers(this.toString());
            return true;
            //Views are notified only if another Player has to play the turn, the first player is notified in the setDashboardOrder() method
        }
        else {
            currentPlayerPosition = 0;
            this.currentPlayerDashboard = dashboardsCollection.get(currentPlayerPosition);
            return false;
        }
    }

    public void setDashboardOrder() {
        Dashboard tmp;
        for (int i = 0; i< totalPlayersNum -1; i++ ) {
            for (int j = 0; j< totalPlayersNum -i-1; j++ ) {
                if ( dashboardsCollection.get(j).getCurrentCard().getValue() > dashboardsCollection.get(j+1).getCurrentCard().getValue() ) {
                    tmp = dashboardsCollection.get(j);
                    dashboardsCollection.set(j, dashboardsCollection.get(j+1));
                    dashboardsCollection.set(j+1, tmp);
                }
            }
        }
        this.currentPlayerDashboard = dashboardsCollection.get(0);

        //setChanged();
        //notifyObservers(this.toString());
    }

    public void initializeAllEntrance(){
        try {
            for (Dashboard d : dashboardsCollection) {
                if (totalPlayersNum == 3)
                    d.moveToEntrance(Bag.removeStudents(9));
                else
                    d.moveToEntrance(Bag.removeStudents(7));
            }
        }
        catch (MaxNumberException | StudentIDAlreadyExistingException | NoMoreStudentsException e ) {
            System.out.println(e.getMessage());
        }
    }

    public void checkAndMoveMasters() throws WrongColorException, NoMasterException {
        Dashboard maxStudentDashboard = null; //TODO NULL NONONONONONO
        Dashboard dashboardWithMaster = null;
        int maxStudents;
        for ( Color c: Color.values() ) {
            maxStudents = 0;
            maxStudentDashboard = null;
            dashboardWithMaster = null;
            for ( Dashboard d: dashboardsCollection ) {
                if ( d.haveMaster(c) ) {
                    dashboardWithMaster = d;
                    maxStudents = d.getStudentsNumInDR(c);
                }
            }
            for ( Dashboard d: dashboardsCollection ) {
                if ( d.getStudentsNumInDR(c)>maxStudents ) {
                    maxStudents = d.getStudentsNumInDR(c);
                    maxStudentDashboard = d;
                }
            }
            if ( maxStudentDashboard!=null && dashboardWithMaster==null ) {
                maxStudentDashboard.insertMaster(mastersMap.remove(c));
            }
            else if ( maxStudentDashboard!=null && dashboardWithMaster!=maxStudentDashboard ) {
                maxStudentDashboard.insertMaster(dashboardWithMaster.removeMaster(c));
            }
        }
    }

    @Override
    public String toString() {
        String outputString = "";

        for ( Dashboard d : dashboardsCollection ) {
            outputString = outputString.concat(d.toString()+"Card played by " + d.getPlayer().getNickname() + ": " + d.getCurrentCard().toString() + "\n");
            if(this instanceof ExpertMatch){
                outputString=outputString.concat("Player coins number: "+ d.getCoinsNumber()+"\n");
            }
        }

        for ( int i : islandPositions ) {
            outputString = outputString.concat(islands.get(i).toString());
        }

        for ( Cloud c : clouds ) {
            outputString = outputString.concat(c.toString());
        }

        outputString = outputString.concat("\n-> " + currentPlayerDashboard.getPlayer().getNickname() + " it's your turn! <-\n");

        return outputString;
    }

    public void setErrorMessage(String errorMessage) {
        StringBuilder res=new StringBuilder(CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_RED);
        res.append(errorMessage).append(CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR);
        this.errorMessage=res.toString();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public Choice getChoice() {
        return choicePhase;
    }

    //Only for test
    public HashMap<Color, Master> getMasters () {
        return new HashMap<Color, Master>(mastersMap);
    }

    public List<Player> showAllPlayers() {
        ArrayList<Player> NicknamesList = new ArrayList<>(0);

        for ( Dashboard d : dashboardsCollection ) {
            NicknamesList.add(d.getPlayer());
        }

        return NicknamesList;
    }

    @Override
    public List<FigureCard> showFigureCardsInGame() {
        return null;
    }

    @Override
    public List<Card> showAllCurrentCards() {
        List<Card> currentCards=new ArrayList<>();
        for (Dashboard d: dashboardsCollection) {
            currentCards.add(new Card(d.getCurrentCard()));
        }
        return currentCards;
    }

    @Override
    public Player getPlayerByCurrentCard(Card card){
        for (Dashboard d: dashboardsCollection) {
            if(d.getCurrentCard().equals(card))
                return new Player(d.getPlayer().getNickname());
        }
        return null;
    }

    @Override
    public boolean isCentaurEffect() {
        return false;
    }

    public void setWinnerPlayerByTowerColor(TowerColor t) {
        for ( Dashboard d : dashboardsCollection ) {
            if ( d.getTowerColor().equals(t))
                winnerPlayers.add(d.getPlayer());
        }
    }

    public List<Player> getWinnerPlayers() {
        return winnerPlayers;
    }

    public void notifyEndMatch() {
        setChanged();
        notifyObservers( new MatchEndedMessage(getWinnerPlayers()));
    }

    public void setMatchFinishedAtEndOfRound() {
        matchFinishedAtEndOfRound = true;
    }


    /**
     * This method calculates which {@code Player} is the winner (based on the number of towers on islands)
     * and save it in {@code winnerPlayer} list.
     */
    public void calculateWinner(){
        int maxTowerOnIslands = 0;
        int maxNumOfMaster = 0;
        ArrayList<TowerColor> maxTowerColorList = new ArrayList<>(0);
        HashMap<TowerColor, Integer> towerNumByColor = new HashMap<>();

        //Initialize num of tower on island to 0 for each color
        for ( TowerColor t: TowerColor.values() )
            towerNumByColor.put(t,0);


        //Add tower for each color analyzing all islands
        try {
            for( Integer i: islandPositions) {

                if ( islands.get(i).getTowerNum() != 0 )
                    towerNumByColor.put( islands.get(i).getTowerColor(), ( towerNumByColor.get(islands.get(i).getTowerColor()) )+(islands.get(i).getTowerNum()) );
            }
        } catch( NoTowerException e ) {
            e.printStackTrace();
        }

        //All towerColor which have the major number of towers on islands are saved in maxTowerColorList
        //This is necessary because more than one towerColor could be the one that determine the winner
        for ( TowerColor t: towerNumByColor.keySet()  ) {
            if ( towerNumByColor.get(t) == maxTowerOnIslands ) {
                maxTowerColorList.add(t);
            }
            else if ( towerNumByColor.get(t) > maxTowerOnIslands ) {
                maxTowerOnIslands=towerNumByColor.get(t);
                maxTowerColorList.clear();
                maxTowerColorList.add(t);
            }
        }

        //Used when the number of towers on islands is the same for more than one towerColor
        if ( maxTowerColorList.size()==1 ) {
            for ( Dashboard d: dashboardsCollection ) {
                if ( d.getTowerColor() == maxTowerColorList.get(0) )
                    winnerPlayers.add(d.getPlayer());
            }
        } else {
            for ( Dashboard d: dashboardsCollection) {
                if ( maxTowerColorList.contains(d.getTowerColor()) && d.getMastersList().size()>maxNumOfMaster )
                    maxNumOfMaster=d.getMastersList().size();
            }
            for ( Dashboard d: dashboardsCollection) {
                if ( maxTowerColorList.contains(d.getTowerColor()) && d.getMastersList().size()==maxNumOfMaster )
                    winnerPlayers.add(d.getPlayer());
            }
        }

    }


    //END ZAMBO

    //Start Vaia


    public int getCurrentIsland() {
        return currentIsland;
    }

    public void moveMotherNature(int posizioni) throws NoIslandException, SameInfluenceException, NoMoreBlockCardsException, MaxNumberException, NoMoreTowerException, TowerIDAlreadyExistingException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, CardNotFoundException, MaxNumberOfTowerPassedException, FinishedGameIslandException {
        //try {
            if (posizioni <= currentPlayerDashboard.getCurrentCard().getMovementValue() && posizioni > 0) {
                int positionTmp = currentIsland;
                islands.get(positionTmp).setMotherNature(false);
                for (int i = 0; i < posizioni; i++) {
                    positionTmp = nextIsland(positionTmp);
                }
                currentIsland = positionTmp;
                islands.get(currentIsland).setMotherNature(true);
                changeTowerColorOnIsland();


                choicePhase = new CloudChoice();
                notifyMatchObservers();
            } else throw new MaxNumberException("Cannot move mother nature that far");
        /*}catch (Exceptions e){
            e.manageException(this);
        }*/
    }

    public void mergeIsland(int islandToBeMerged) throws NoMoreTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers {
        ArrayList<Student>[] studentsToBeMergedTmp;
        ArrayList<Student>[] studentsTmp;
        studentsToBeMergedTmp = islands.get(islandToBeMerged).getStudents();
        studentsTmp = islands.get(currentIsland).getStudents();
        for (int i = 0; i< 5;i++)
        {
            studentsTmp[i].addAll(studentsToBeMergedTmp[i]);
        }
        islands.get(currentIsland).setStudents(studentsTmp);
        islandPositions.remove((Integer) islandToBeMerged);
        totalNumIslands--;
        islands.get(currentIsland).addTowers(islands.get(islandToBeMerged).removeTowers());
    }
    //solo per testare
    public void addIslandsTowers(int islandToSet, ArrayList<Tower> towers) throws InvalidNumberOfTowers, NoListOfSameColoredTowers {
        islands.get(islandToSet).addTowers(towers);
    }

    public void setIslandsStudents(int islandToSet, ArrayList<Student>[] students) {
        islands.get(islandToSet).setStudents(students);
    }

    public ArrayList<Student>[] getStudentsOnIsland(int island){
        return islands.get(island).getStudents();
    }

    public int getTowersNumOnIsland(int island){
        return islands.get(island).getTowerNum();
    }

    public boolean checkMotherNatureOnIsland(int island){
        return islands.get(island).checkIsMotherNature();
    }

    public List<Integer> getIslandPositions() {
        List<Integer> tmp = new ArrayList<>(islandPositions);
        return tmp;
    }


    public List<Island> getIslands() {
        return islands;
    }

    public void checkNearbyIslands() throws NoTowerException, NoIslandException, NoMoreTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers {
        int nextIslandTmp, previousIslandTmp;
        nextIslandTmp = nextIsland(currentIsland);
        previousIslandTmp = previousIsland(currentIsland);
        try{if(islands.get(nextIslandTmp).getTowerColor() == islands.get(currentIsland).getTowerColor())
            mergeIsland(nextIslandTmp);}
        catch (NoTowerException e){
            //((NoTowerException) e).printStackTrace();
        }
        try{
            if(islands.get(previousIslandTmp).getTowerColor() == islands.get(currentIsland).getTowerColor())
                mergeIsland(previousIslandTmp);
        }
        catch (NoTowerException e){
            //((NoTowerException) e).printStackTrace();
        }
    }
//
    public int nextIsland(int position) throws NoIslandException { //metodo che ritorna l'indice della posizione della prosiima isola
        int tmp = islandPositions.indexOf(position);
        if(tmp > -1){
        if(tmp + 1 == islandPositions.size())
            return islandPositions.get(0);
        return islandPositions.get(tmp + 1);}
        else throw new NoIslandException("Island not found");
    }

    public int previousIsland(int position) throws NoIslandException{ //metodo che ritorna l'indice della posizione della isola precedente
        int tmp = islandPositions.indexOf(position);
        if(tmp > -1){
        if(tmp - 1 == - 1)
            return islandPositions.get(islandPositions.size() - 1);
        return islandPositions.get(tmp - 1);}
        else throw new NoIslandException("Island not found");
    }

    public void setDashboardMaster(int dashboardToSet, Master master)
    {
        dashboardsCollection.get(dashboardToSet).insertMaster(master);
    }

    public TowerColor getTowerColorFromIsland(int island) throws NoTowerException {
        return islands.get(island).getTowerColor();
    }

    public void initializeDashboardsForTesting(Dashboard dashboard){
        dashboardsCollection.add(dashboard);
    }

    public Dashboard checkDashboardWithMoreInfluence() throws SameInfluenceException, CardNotFoundException {
        ArrayList<Dashboard> dashboardListTmp = new ArrayList<Dashboard> (dashboardsCollection);
        int dasboardInfluencer = 0;
        Boolean exception = false;
        int influenceTmp , influenceTmp1;
        if(totalPlayersNum != 4){
            influenceTmp = islands.get(currentIsland).getInfluenceByDashboard(dashboardsCollection.get(dasboardInfluencer));
            for (int i = 1; i < dashboardsCollection.size(); i++){
                if (influenceTmp < islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(i))){
                    dasboardInfluencer = i;
                    influenceTmp = islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(i));
                    exception = false;
                }
                else if(influenceTmp == islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(i)))
                    exception = true;
            }
            if (exception)
                throw new SameInfluenceException("No change needed in current island");
        }
        else{
            influenceTmp = islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(0));
            influenceTmp += islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(0).getBuddy());
            dashboardListTmp.remove(0);
            dashboardListTmp.remove(dashboardsCollection.get(0).getBuddy());
            influenceTmp1 = islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(0));
            influenceTmp1 += islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(1));

            if (influenceTmp < influenceTmp1){
                    dasboardInfluencer = dashboardListTmp.get(0).getPlayerNumber();
                    //influenceTmp = influenceTmp1;
            }

            if (influenceTmp == influenceTmp1)
                throw new SameInfluenceException("No change needed in current island");
        }
        return dashboardsCollection.get(dasboardInfluencer);
    }

    public ArrayList<Tower> removeTowersFromDashboard(int dashboard, int towersToRemove) throws NoMoreTowerException {
        return dashboardsCollection.get(dashboard).removeTowers(towersToRemove);
    }

    public void changeTowerColorOnIsland() throws SameInfluenceException, CardNotFoundException, NoMoreTowerException, NoTowerException, InvalidNumberOfTowers, TowerIDAlreadyExistingException, MaxNumberOfTowerPassedException, NoListOfSameColoredTowers, FinishedGameIslandException {
        //try{
        try{Dashboard dashboardTmp = checkDashboardWithMoreInfluence();
        int towersNum = 0;
        if(islands.get(currentIsland).getTowerNum() == 0)
            islands.get(currentIsland).addTowers(dashboardTmp.getBuddyWithTowers().removeTowers(1));
        else if(!dashboardTmp.getBuddyWithTowers().getTowerColor().equals(islands.get(currentIsland).getTowerColor())){
            for (int i = 0; i< this.dashboardsCollection.size(); i++)
            {
                if(dashboardsCollection.get(i).getBuddyWithTowers().getTowerColor().equals(islands.get(currentIsland).getTowerColor())) {
                    towersNum = islands.get(currentIsland).getTowerNum();
                    dashboardsCollection.get(i).getBuddyWithTowers().addTowers(islands.get(currentIsland).removeTowers());
                    islands.get(currentIsland).addTowers(dashboardTmp.getBuddyWithTowers().removeTowers(towersNum));
                }
            }
        }
            checkNearbyIslands();
            if (totalNumIslands <= 3)
                throw new FinishedGameIslandException("Island Num <= 3, game is over");

        }
        catch (SameInfluenceException e){
            //no changes needed on this island
        }
        catch (NoIslandException e) {
            e.printStackTrace();
        }
    }
    // END VAIA
}
