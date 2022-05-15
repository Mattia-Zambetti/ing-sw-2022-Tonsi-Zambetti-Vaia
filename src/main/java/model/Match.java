//Tonsi, Zambo,Vaia
package model;

import model.figureCards.NoMoreBlockCardsException;
import model.exception.*;
import view.choice.*;

import java.io.Serializable;
import java.util.*;
public class Match extends Observable implements MatchDataInterface, Serializable {
    protected List<Island> islands;
    private List<Cloud> clouds;
    protected List<Dashboard> dashboardsCollection; //The order of the player during the actual round is the same of the dashboard in this List
    protected Dashboard currentPlayerDashboard;
    private HashMap<Color, Master> mastersMap;

    private String errorMessage = "";

    private int totalPlayersNum;
    protected int currentIsland;

    private int totalNumIslands; //TODO questo attributo non modifica nulla, penso debba essere
                                //TODO usato per capire se la partita è finita
    protected final List<Integer> islandPositions = new ArrayList<>();
    private int towersNum;

    private int counterMoveStudents=0;

    //utile definire tanti attributi così per avere codice facilmente modificabile
    private final int ISLANDSNUM=12;

    private static final int STUDENTSONCLOUD2PLAYERS= 3;
    private static final int STUDENTSONCLOUD3PLAYERS= 4;
    private static final int STUDENTSONCLOUD4PLAYERS= 3;

    private static final int MAXPLAYERSNUM=4;
    private static final int MINPLAYERSNUM=2;

    private Choice choicePhase;

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

                choicePhase = new DataPlayerChoice(totalPlayersNum);

            } else throw new MaxNumberException("A match can have only from 2 to 4 players");
        }catch (MaxNumberException | NoMoreStudentsException e){
            System.out.println(e.getMessage());
        }
    }


    public Match(Match match){

        this.islands = new ArrayList<>();
        for (Island island: match.islands) {
            this.islands.add(new Island(island));
        }

        this.clouds = new ArrayList<>();
        for (Cloud cloud: match.clouds) {
            this.clouds.add(new Cloud(cloud));
        }

        this.dashboardsCollection=new ArrayList<>();
        for (Dashboard dashboard: match.dashboardsCollection) {
            this.dashboardsCollection.add(new Dashboard(dashboard));
        }

        this.currentPlayerDashboard=new Dashboard(match.currentPlayerDashboard);

        this.mastersMap=new HashMap<>(match.mastersMap);

        this.totalPlayersNum=match.totalPlayersNum;

        this.currentIsland=match.currentIsland;

        this.islandPositions.addAll(match.islandPositions);

        this.towersNum=match.towersNum;
    }

    //TONSI


    public Player showCurrentPlayer() {
        try{
            return showCurrentPlayerDashboard().getPlayer();
        }catch (NullPointerException e){
            return new Player("error");
        }
    }

    public int getCurrentPlayersNum() {
        return dashboardsCollection.size();
    }

    //it returns the max number of players in a match
    public static int getMAXPLAYERSNUM() {
        return MAXPLAYERSNUM;
    }

    //it returns the minimum number of players in a match
    public static int getMINPLAYERSNUM() {
        return MINPLAYERSNUM;
    }

    //It's the player's number in this match
    public int getTotalPlayersNum() {
        return totalPlayersNum;
    }

    //It returns the students number on the clouds, used in the constructor
    public int chooseStudentsNumOnCLoud() {
        if(totalPlayersNum ==2){
            return STUDENTSONCLOUD2PLAYERS;
        }else if(totalPlayersNum ==3)
            return STUDENTSONCLOUD3PLAYERS;
        else return STUDENTSONCLOUD4PLAYERS;
    }

    //It's used in the constructor, it creates the clouds by using the player's number
    private void initializeClouds() {
        for (int i = 0; i < totalPlayersNum; i++) {
            clouds.add(new Cloud(i));
        }
    }

    //TESTED
    //It's used in the constructor, it creates the islands
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

    //TESTED
    //it's used to take the students from the bag and
    //put them into the cloud number "cloudNum"
    private Set<Student> pullStudentsFromCloud(int cloudNum) throws WrongCloudNumberException {
        if (cloudNum < totalPlayersNum && cloudNum >= 0 && !(getCloudByID(cloudNum).equals(new Cloud(cloudNum)))) {
            return getCloudByID(cloudNum).takeStudents();
        }
        throw new WrongCloudNumberException("wrong cloud's number");
    }

    private Cloud getCloudByID(int ID) throws WrongCloudNumberException {
        for ( Cloud c: clouds ) {
            if ( c.getID() == ID )
                return c;
        }
        throw new WrongCloudNumberException("wrong cloud's number");
    }

    //TESTED
    //it is used at the start of a round to refill every cloud
    //with new students from the bag
    public void refillClouds() throws NoMoreStudentsException {
        for (Cloud c : clouds) {
            try {
                c.refillCloud(Bag.removeStudents(Cloud.getStudentsNumOnCloud()));
            } catch (AlreadyFilledCloudException | MaxNumberException e) {
                System.out.println(e.getMessage());
            }
        }

        //setChanged();
        //notifyObservers(this.toString());

    }

    //ONLY FOR TEST
    public void refillCloudsNoNotify() throws NoMoreStudentsException {
        for (Cloud c : clouds) {
            try {
                c.refillCloud(Bag.removeStudents(Cloud.getStudentsNumOnCloud()));
            } catch (AlreadyFilledCloudException | MaxNumberException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    //TESTED
    //the param chosenCLoud require to contains the choice starting from 1(NOT 0), the method
    //takes the students from the cloud "chosenCloud"(STARTING FROM POSITION NUMBER 1) to
    //the current player's entrance
    public void moveStudentsFromCloudToEntrance(int chosenCloud) throws WrongCloudNumberException, MaxNumberException, NoMoreStudentsException {
        try {

            if (chosenCloud < totalPlayersNum && chosenCloud >= 0 && !(getCloudByID(chosenCloud).equals(new Cloud(chosenCloud))))
                currentPlayerDashboard.moveToEntrance(pullStudentsFromCloud(chosenCloud));
            else
                throw new WrongCloudNumberException("This cloud doesn't exist");
            //setChanged();
            //notifyObservers(this);


            if(!setNextCurrDashboard()){
                setDashboardOrder();
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

    //SEMI TESTED
    //it returns the string version of the clouds content
    public String toStringStudentsOnCloud() {
        StringBuilder res = new StringBuilder();
        for (Cloud c : clouds) {
            res.append(c.toString());
        }
        return res.toString();
    }

    public List<Cloud> showClouds(){
        List<Cloud> res=new ArrayList<>();
        for (Cloud c:clouds) {
            res.add(new Cloud(c));
        }
        return res;
    }

    public Set<Card> showCards(){
        return currentPlayerDashboard.showCards();
    }

    public void chooseCard(Card chosenCard) throws CardNotFoundException, NoMoreCardException, CardAlreadyPlayedException {

        for (Dashboard d: dashboardsCollection) {
            if ( dashboardsCollection.indexOf(d) < dashboardsCollection.indexOf(currentPlayerDashboard) ) {
                if ( chosenCard.equals(d.getCurrentCard()))
                   throw new CardAlreadyPlayedException("This card has already been chosen by another player");
            }
        }

        currentPlayerDashboard.playChosenCard(chosenCard);

        if(currentPlayerDashboard.showCards().size()==0 && currentPlayerDashboard.equals(dashboardsCollection.get(0))){
            throw new NoMoreCardException("It's the last turn");
        }


        if(!setNextCurrDashboard()){
            setDashboardOrder();
            choicePhase=new MoveStudentChoice(showCurrentPlayerDashboard().showEntrance());
        }else
            choicePhase=new CardChoice(showCurrentPlayerDashboard().showCards());

        notifyMatchObservers();

    }


    public void addPlayer(String nickname, String towerColor, String wizard) throws WrongColorException, WrongDataplayerException, NoMoreStudentsException, MaxNumberException {
        if (dashboardsCollection.size() < totalPlayersNum) {

            if ((totalPlayersNum == 2 || totalPlayersNum == 4) && towerColor.toString().equals("GREY")) {
                throw new WrongColorException("You can't choose this color...");
            }


            for (Dashboard player : dashboardsCollection) {
                if (player.getPlayer().getNickname().equals(nickname))
                    throw new WrongDataplayerException("This nickname has been already chosen");
                if (player.getWizard().toString().equals(wizard))
                    throw new WrongDataplayerException("This wizard has already chosen");
            }

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


            if (totalPlayersNum < MAXPLAYERSNUM) {
                dashboardsCollection.add(new Dashboard(towersNum, TowerColor.valueOf(towerColor), Wizard.valueOf(wizard), nickname, dashboardsCollection.size()));
            } else if (dashboardsCollection.size() == 0 || dashboardsCollection.size() == 2) {
                dashboardsCollection.add(new Dashboard(towersNum, TowerColor.valueOf(towerColor), Wizard.valueOf(wizard), nickname, dashboardsCollection.size()));
                TowerColor.valueOf(towerColor).counterplus();
            } else if (dashboardsCollection.size() == 1 || dashboardsCollection.size() == 3) {
                dashboardsCollection.add(new Dashboard(0, TowerColor.valueOf(towerColor), Wizard.valueOf(wizard), nickname, dashboardsCollection.size()));
                TowerColor.valueOf(towerColor).counterplus();
            }

            if (totalPlayersNum == dashboardsCollection.size()) {
                initializeAllEntrance();
                choicePhase = new CardChoice(currentPlayerDashboard.showCards());
                refillClouds();
                setChanged();
                notifyObservers((MatchDataInterface) this);
            }


            if (dashboardsCollection.size() == 1) {
                currentPlayerDashboard = dashboardsCollection.get(0);
            }
        } else throw new MaxNumberException("Max players number reached...");

    }

    private void setTowersNum() {
        if (totalPlayersNum == 2) {
            towersNum = 8;
        } else if (totalPlayersNum == 3) {
            towersNum = 6;
        } else if (totalPlayersNum == 4)
            towersNum = 8;

    }


    public Dashboard showCurrentPlayerDashboard() throws NullPointerException {
        return new Dashboard(currentPlayerDashboard);
    }

    //END TONSI

    //ZAMBO

    public void notifyMatchObservers() {
        setChanged();
        notifyObservers((MatchDataInterface)this);
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
                throw new NoIslandException("chosenIslandPosition out of bound, moveStudentFromEntranceToIsland failed");
            if ( this.islands.get(chosenIslandPosition) == null )
                throw new NoIslandException("Island at chosenIslandPosition is null, moveStudentFromEntranceToIsland failed");
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
        this.errorMessage = errorMessage;
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

    public List<String> showAllPlayersNickname() {
        ArrayList<String> NicknamesList = new ArrayList<>(0);

        for ( Dashboard d : dashboardsCollection ) {
            NicknamesList.add(d.getPlayer().getNickname());
        }

        return NicknamesList;
    }

    //END ZAMBO

    //Start Vaia
    public void moveMotherNature(int posizioni) throws NoIslandException, SameInfluenceException, NoMoreBlockCardsException, MaxNumberException, NegativeNumberOfTowerException, TowerIDAlreadyExistingException, InvalidNumberOfTowers, NoTowerException, NoListOfSameColoredTowers, CardNotFoundException, MaxNumberOfTowerPassedException, FinishedGameIslandException {
        try {
            if (posizioni <= currentPlayerDashboard.getCurrentCard().getMovementValue() && posizioni > 0) {
                int positionTmp = currentIsland;
                islands.get(positionTmp).setMotherNature(false);
                for (int i = 0; i < posizioni; i++) {
                    positionTmp = nextIsland(positionTmp);
                }
                currentIsland = positionTmp;
                islands.get(currentIsland).setMotherNature(true);
                changeTowerColorOnIsland();

                if (totalNumIslands <= 3)
                    throw new FinishedGameIslandException("Island Num <= 3, game is over");

                //setChanged();
                //notifyObservers(this.toString());

                choicePhase = new CloudChoice();
                notifyMatchObservers();
            } else throw new MaxNumberException("Cannot move mother nature that far");
        }catch (Exceptions e){
            e.manageException(this);
        }
    }

    public void mergeIsland(int islandToBeMerged) throws NegativeNumberOfTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers {
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

    public void checkNearbyIslands() throws NoTowerException, NoIslandException, NegativeNumberOfTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers {
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
        ArrayList<Dashboard> dashboardListTmp = (ArrayList<Dashboard>)dashboardsCollection;
        int dasboardInfluencer;
        dasboardInfluencer = 0;
        Boolean exception = false;
        int influenceTmp = islands.get(currentIsland).getInfluenceByDashboard(dashboardsCollection.get(dasboardInfluencer));
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
        return dashboardsCollection.get(dasboardInfluencer);
    }

    public ArrayList<Tower> removeTowersFromDashboard(int dashboard, int towersToRemove) throws NegativeNumberOfTowerException {
        return dashboardsCollection.get(dashboard).removeTowers(towersToRemove);
    }

    //TODO NegativeNumberOfTowerException non bisogna fare printStackTrace ma va ritornata al controller che segnala che la partita è finita e ha vinto il giocatore che ha finito le torri
    //TODO SameInfluenceException mai sollevata
    public void changeTowerColorOnIsland() throws SameInfluenceException, CardNotFoundException, NegativeNumberOfTowerException, NoTowerException, InvalidNumberOfTowers, TowerIDAlreadyExistingException, MaxNumberOfTowerPassedException, NoListOfSameColoredTowers {
        //try{
        try{Dashboard dashboardTmp = checkDashboardWithMoreInfluence();
        int towersNum = 0;
        if(islands.get(currentIsland).getTowerNum() == 0)
            islands.get(currentIsland).addTowers(dashboardTmp.removeTowers(1));
        else if(!dashboardTmp.getTowerColor().equals(islands.get(currentIsland).getTowerColor())){
            for (int i = 0; i< this.dashboardsCollection.size(); i++)
            {
                if(dashboardsCollection.get(i).getTowerColor().equals(islands.get(currentIsland).getTowerColor())) {
                    towersNum = islands.get(currentIsland).getTowerNum();
                    dashboardsCollection.get(i).addTowers(islands.get(currentIsland).removeTowers());
                    //towersNum = islands.get(currentIsland).getTowerNum();
                    islands.get(currentIsland).addTowers(dashboardTmp.removeTowers(towersNum));
                }
            }
        }
            checkNearbyIslands();
        }
        catch (SameInfluenceException e){System.out.println(e.getMessage());} catch (NoIslandException e) {
            e.printStackTrace();
        }

        // }
       // catch (NoTowerException | NegativeNumberOfTowerException | InvalidNumberOfTowers | NoListOfSameColoredTowers |
         //      MaxNumberOfTowerPassedException | TowerIDAlreadyExistingException e){System.out.println(e.getMessage());}
    }
    // END VAIA
}
