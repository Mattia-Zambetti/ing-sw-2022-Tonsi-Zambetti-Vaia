//Tonsi, Zambo,Vaia
package model;

import model.FigureCards.FigureCard;
import model.exception.*;

import java.util.*;
public class Match extends Observable{
    private List<Island> islands;
    private List<Cloud> clouds;
    private Bag bag;
    private List<Dashboard> dashboardsCollection; //The order of the player during the actual round is the same of the dashboard in this List
    private Dashboard currentPlayerDashboard;
    private HashMap<Color, Master> mastersMap;
    private Collection<FigureCard> figureCards;
    private int playersNum;
    private boolean isExpertMode;
    private int currentIsland,totalNumIslands; //serve?
    private List<Integer> islandPositions = new ArrayList<>();
    private int towersNum;

    //utile definire tanti attributi così per avere codice facilmente modificabile
    private static final int ISLANDSNUM=12;

    private static final int STUDENTSONCLOUD2PLAYERS= 3;
    private static final int STUDENTSONCLOUD3PLAYERS= 4;
    private static final int STUDENTSONCLOUD4PLAYERS= 3;

    private static final int MAXPLAYERSNUM=4;
    private static final int MINPLAYERSNUM=2;

    //TESTED
    public Match(int playersNum, boolean isExpertMode) {
        try {
            this.playersNum = playersNum;
            if (this.playersNum <= MAXPLAYERSNUM && this.playersNum >= MINPLAYERSNUM) {

                this.isExpertMode = isExpertMode;

                Bag.restoreBag();

                //per essere più precisi, a noi non serve sapere l'ordine totale ma solo la prossima/precedente, dovrebbe essere più
                // efficiente la linked
                islands = new ArrayList<Island>();
                initializeIslands();
                this.totalNumIslands=ISLANDSNUM;

                Bag.createAllStudents();
                Cloud.setStudentsNumOnCloud(chooseStudentsNumOnCLoud());



                clouds = new ArrayList<Cloud>();
                initializeClouds();

                setTowersNum();

                dashboardsCollection = new ArrayList<Dashboard>();
                currentPlayerDashboard = null;

                initializeMasters();

            } else throw new MaxNumberException("A match can have only from 2 to 4 players");
        }catch (MaxNumberException | NoMoreStudentsException e){
            System.out.println(e.getMessage());
        }
    }

    //TONSI


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
    public int getPlayersNum() {
        return playersNum;
    }

    //It returns the students number on the clouds, used in the constructor
    private int chooseStudentsNumOnCLoud() {
        if(playersNum ==2){
            return STUDENTSONCLOUD2PLAYERS;
        }else if(playersNum ==3)
            return STUDENTSONCLOUD3PLAYERS;
        else return STUDENTSONCLOUD4PLAYERS;
    }

    //It's used in the constructor, it creates the clouds by using the player's number
    private void initializeClouds() {
        for (int i = 0; i < playersNum; i++) {
            clouds.add(new Cloud());
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
            if(i!=0 && i!=5)
                islands.get(i).addStudent(Bag.removeStudent());
            motherNature=false;
        }
    }

    //TESTED
    //it's used to take the students from the bag and
    //put them into the cloud number "cloudNum"
    private Set<Student> pullStudentsFromCloud(int cloudNum) throws WrongCloudNumberException {
        if (cloudNum <= playersNum && cloudNum > 0 && clouds.get(cloudNum - 1).toString() != "") {
            return clouds.get(cloudNum - 1).takeStudents();
        } else throw new WrongCloudNumberException("wrong cloud's number");
    }

    //TESTED
    //it is used at the start of a round to refill every cloud
    //with new students from the bag
    public void refillClouds() {
        try {
            for (Cloud c : clouds) {
                c.refillCloud(Bag.removeStudents(Cloud.getStudentsNumOnCloud()));
            }
        }catch (AlreadyFilledCloudException | MaxNumberException | NoMoreStudentsException e){
            System.out.println(e.getMessage());
        }
    }

    //TESTED
    //the param chosenCLoud require to contains the choice starting from 1(NOT 0), the method
    //takes the students from the cloud "chosenCloud"(STARTING FROM POSITION NUMBER 1) to
    //the current player's entrance
    public void moveStudentsFromCloudToEntrance(int chosenCloud) {
        try {
            if (chosenCloud <= playersNum && chosenCloud > 0 && !clouds.get(chosenCloud-1).toString().equals(""))
                currentPlayerDashboard.moveToEntrance(pullStudentsFromCloud(chosenCloud));
            else
                throw new WrongCloudNumberException("This cloud doesn't exist");
            notifyObservers();//non so cosa potrebbe notificare per ora, vedremo
        }catch (MaxNumberException | StudentIDAlreadyExistingException e){
            System.out.println(e.getMessage());
        }catch (WrongCloudNumberException e){
            System.out.println(e.getMessage());
            notifyObservers(currentPlayerDashboard); //TODO qui bisogna capire cosa notifichiamo,
                                                    // per dire di ripetere la scelta
        }
    }

    //SEMI TESTED
    //it returns the string version of the clouds content
    public String toStringStudentsOnCloud() {
        String res = "";
        for (Cloud c : clouds) {
            res += c.toString();
        }
        notifyObservers(res);
        return res;
    }


    public Set<Card> showCards(){
        notifyObservers(new HashSet<>(currentPlayerDashboard.showCards()));
        return new HashSet<Card>(currentPlayerDashboard.showCards());
    }

    public void chooseCard(Card chosenCard) {

        try {
            currentPlayerDashboard.playChosenCard(chosenCard);

            //qui si passa al prossimo giocatore, di modo che se la carta è corretta ci entra
            //e passa al prossimo, se non lo è viene direttamente catchato. Nel momento in
            //cui ci troviamo all'ultima carta, lancia l'exception e prosegue col normale corso
            //del programma

            if(currentPlayerDashboard.showCards().size()==0 && currentPlayerDashboard.equals(((ArrayList)dashboardsCollection).get(0))){
                throw new NoMoreCardException("It's the last turn");
            }
        } catch (CardNotFoundException | NoMoreCardException e) {
            System.out.println(e.getMessage());
        }finally {
            notifyObservers(currentPlayerDashboard);//TODO decidere cosa restituire più correttamente
        }
    }

    //missing nickname, this method must be fixed
    public void addPlayer(String nickname, String towerColor, String wizard){
        try {

            if (dashboardsCollection.size() < playersNum) {
                for(Dashboard player: dashboardsCollection ){
                    if(player.getTowerColor().toString().equals(towerColor))
                        throw new WrongDataplayerException("This tower color has been already chosen");
                    if(player.getPlayer().getNickname().equals(nickname))
                        throw new WrongDataplayerException("This nickname has been already chosen");
                    if(player.getWizard().equals(wizard))
                        throw new WrongDataplayerException("This wizard has already chosen");
                }

                //TODO gestire i team in qualche modo
                if (playersNum <= 3) {
                    dashboardsCollection.add(new Dashboard(towersNum, TowerColor.valueOf(towerColor), Wizard.valueOf(wizard), nickname));
                } else if (dashboardsCollection.size() == 1 || dashboardsCollection.size() == 3) {
                    dashboardsCollection.add(new Dashboard(towersNum, TowerColor.valueOf(towerColor), Wizard.valueOf(wizard), nickname));
                } else if (dashboardsCollection.size() == 2 || dashboardsCollection.size() == 4) {
                    dashboardsCollection.add(new Dashboard(0, TowerColor.valueOf(towerColor), Wizard.valueOf(wizard), nickname));
                }
                if (dashboardsCollection.size() == 1) {
                    currentPlayerDashboard = (Dashboard) ((ArrayList) dashboardsCollection).get(0);
                }
            } else throw new MaxNumberException("Max players number reached...");
        }catch(MaxNumberException | WrongDataplayerException e){
            System.out.println(e.getMessage());
        }
    }

    private void setTowersNum() {
        if (playersNum == 2) {
            towersNum = 8;
        } else if (playersNum == 3) {
            towersNum = 6;
        } else if (playersNum == 4)
            towersNum = 8;

    }


    //USED ONLY IN MATCHTEST
    public Dashboard showCurrentPlayerDashboard(){
        try {
            return new Dashboard(currentPlayerDashboard);
        } catch (CardNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //END TONSI

    //ZAMBO

    private void initializeMasters() {
        mastersMap=new HashMap<>();
        for (Color c : Color.values()) {
            mastersMap.put(c, new Master(c));
        }
    }

    //il metodo muove gli studenti scelti dall'ingresso alla dining room, non serve passare dashboard perché si basa su CurrentDashboard
    private void moveStudentFromEntranceToDR( Student studentToBeMoved ) {
        Student tmpStudent;
        try {
            tmpStudent = this.currentPlayerDashboard.removeStudentFromEntrance( studentToBeMoved );
            this.currentPlayerDashboard.moveToDR(tmpStudent);
        }
        catch ( MaxNumberException | WrongColorException | StudentIDAlreadyExistingException | InexistentStudentException | NullPointerException e ) {
            System.out.println(e.getMessage());
        }

    }

    private void moveStudentFromEntranceToIsland( Student chosenStudent, Island chosenIsland ) throws NoIslandException {
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

    }

    private void moveStudentFromEntranceToIsland( Student chosenStudent, int chosenIslandPosition ) throws NoIslandException {
        try {
            Student tmpStudent = this.currentPlayerDashboard.removeStudentFromEntrance(chosenStudent);
            if ( chosenIslandPosition<0 || chosenIslandPosition>(ISLANDSNUM-1) )
                throw new NoIslandException("chosenIslandPosition out of bound, moveStudentFromEntranceToIsland failed");
            //TODO possibile check sul fatto che l'isola non sia giá stata unificata ad un'altra
            if ( this.islands.get(chosenIslandPosition) == null )
                throw new NoIslandException("Island at chosenIslandPosition is null, moveStudentFromEntranceToIsland failed");
            else
                this.islands.get(chosenIslandPosition).addStudent(tmpStudent);
        }
        catch (InexistentStudentException | NullPointerException e ) {
            System.out.println(e.getMessage());
        }
    }

    //This method set the next dashboard (and so the player) that has to play, if there is a next player it notifies the player and after return true, if there are no more player
    //it returns false without notifying any player, in the planning phase if it's returned false the controller has to call the setDashboardOrder method
    private boolean setNextCurrDashboard() {
        if ( ! (dashboardsCollection instanceof ArrayList<Dashboard>) )
            throw new IllegalArgumentException("DashboardCollection is not an ArrayList");
        int currentPlayerPosition = dashboardsCollection.indexOf(this.currentPlayerDashboard);
        if ( currentPlayerPosition < (this.dashboardsCollection.size()-1) ) {
            currentPlayerPosition++;
            this.currentPlayerDashboard = dashboardsCollection.get(currentPlayerPosition);
            notifyObservers(currentPlayerDashboard.getPlayer());
            return true;
            //Views are notified only if another Player has to play the turn
        }
        else {
            currentPlayerPosition = 0;
            this.currentPlayerDashboard = dashboardsCollection.get(currentPlayerPosition);
            return false;
        }
    }

    public void setDashboardOrder() {
        Dashboard tmp;
        for ( int i=0; i<playersNum-1; i++ ) {
            for ( int j=0; j<playersNum-i-1; j++ ) {
                if ( dashboardsCollection.get(j).getCurrentCard().getValue() > dashboardsCollection.get(j+1).getCurrentCard().getValue() ) {
                    tmp = dashboardsCollection.get(j);
                    dashboardsCollection.set(j, dashboardsCollection.get(j+1));
                    dashboardsCollection.set(j+1, tmp);
                }
            }
        }
        this.currentPlayerDashboard = dashboardsCollection.get(0);
    }

    public void initializeAllEntrance(){
        try {
            for (Dashboard d : dashboardsCollection) {
                if (playersNum == 3)
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
        Dashboard maxStudentDashboard = null;
        Dashboard dashboardWithMaster = null;
        int maxStudents;
        for ( Color c: Color.values() ) {
            maxStudents = 0;
            maxStudentDashboard = null;
            dashboardWithMaster = null;
            for ( Dashboard d: dashboardsCollection ) {
                if ( d.getStudentsNumInDR(c)>maxStudents ) {
                    maxStudents = d.getStudentsNumInDR(c);
                    maxStudentDashboard = d;
                }
                if ( d.haveMaster(c) )
                    dashboardWithMaster = d;
            }
            if ( maxStudentDashboard!=null && dashboardWithMaster==null ) {
                maxStudentDashboard.insertMaster(mastersMap.remove(c));
            }
            else if ( maxStudentDashboard!=null && dashboardWithMaster!=maxStudentDashboard ) {
                maxStudentDashboard.insertMaster(dashboardWithMaster.removeMaster(c));
            }
        }
    }
    //TODO se volessimo aggiornare la view quando il master viene spostato bisogna aggiungere un notify in questo metodo

    //END ZAMBO

    //Start Vaia
    public void moveMotherNature(int posizioni) throws NoIslandException {
        int positionTmp = currentIsland;
        islands.get(positionTmp).setMotherNature(false);
        for (int i = 0; i < posizioni; i++){
            positionTmp = nextIsland(positionTmp);
        }
        currentIsland = positionTmp;
        islands.get(currentIsland).setMotherNature(true);
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
    public void setIslandsTowers(int islandToSet, ArrayList<Tower> towers) throws InvalidNumberOfTowers, NoListOfSameColoredTowers {
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
        catch (NoTowerException e){}
        try{if(islands.get(previousIslandTmp).getTowerColor() == islands.get(currentIsland).getTowerColor())
                mergeIsland(previousIslandTmp);}
        catch (NoTowerException e){}
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

    public Dashboard checkDashboardWithMoreInfluence() throws SameInfluenceException{
        ArrayList<Dashboard> dashboardListTmp = (ArrayList<Dashboard>)dashboardsCollection;
        Dashboard dasboardInfluencer = dashboardListTmp.get(0);
        Boolean exception = false;
        int influenceTmp = islands.get(currentIsland).getInfluenceByDashboard(dasboardInfluencer);
        for (int i = 1; i < dashboardsCollection.size(); i++){
            if (influenceTmp < islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(i))){
                dasboardInfluencer = dashboardListTmp.get(i);
                exception = false;
            }
            else if(influenceTmp == islands.get(currentIsland).getInfluenceByDashboard(dashboardListTmp.get(i)))
                exception = true;
        }
        if (exception == true)
            throw new SameInfluenceException("No change needed in current island");
        return dasboardInfluencer;
    }

    public void changeTowerColorOnIsland() throws SameInfluenceException {
        try{
        Dashboard dashboardTmp = checkDashboardWithMoreInfluence();
        ArrayList<Dashboard> dashboardListTmp = (ArrayList<Dashboard>)dashboardsCollection;
        int towersNum;
        if(islands.get(currentIsland).getTowerNum() == 0)
            islands.get(currentIsland).addTowers(dashboardTmp.removeTowers(1));
        else if(!dashboardTmp.getTowerColor().equals(islands.get(currentIsland).getTowerColor())){
            for (int i = 0; i< dashboardsCollection.size(); i++)
            {
                if(dashboardListTmp.get(i).getTowerColor().equals(islands.get(currentIsland).getTowerColor())) {
                    dashboardListTmp.get(i).addTowers(islands.get(currentIsland).removeTowers());
                    towersNum = islands.get(currentIsland).getTowerNum();
                    islands.get(currentIsland).addTowers(dashboardTmp.removeTowers(towersNum));
                }
            }
        }

        }
        catch (Exception e){}
    }
    // END VAIA
}
