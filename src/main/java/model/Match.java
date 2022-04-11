//Tonsi, Zambo,Vaia
package model;

import model.FigureCards.FigureCard;
import model.exception.*;

import java.util.*;
public class Match extends Observable{
    private List<Island> islands;
    private List<Cloud> clouds;
    private Bag bag;
    private Collection<Dashboard> dashboardsCollection; //Ipotizzo che l'ordine delle
    // Dashboard nella collection sia lo stesso dei turni dei giocatori nella partita
    private Dashboard currentPlayerDashboard;
    private HashMap<Color, Master> mastersMap;
    private Collection<FigureCard> figureCards;
    private int playersNum;
    private boolean isExpertMode;
    private int currentIsland,totalNumIslands; //serve?
    private static List<Integer> islandPositions = new ArrayList<>();
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
                mastersMap=new HashMap<>();
                for (Color c : Color.values()) {
                    mastersMap.put(c, new Master(c));
                }
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
            if(i!=0 && i!=ISLANDSNUM-1)
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
        }catch (MaxNumberException e){
            System.out.println(e.getMessage());
        }catch (WrongCloudNumberException e){
            System.out.println(e.getMessage());
            notifyObservers(currentPlayerDashboard); //TODO qui bisogna capire cosa notifichiamo,
                                                    // per dire di ripetere la scelta
        }
    }

    //SEMI TESTED
    //it returnes the string version of the clouds content
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

    //il metodo muove gli studenti scelti dall'ingresso alla dining room, non serve passare dashboard perché si basa su CurrentDashboard
    private void moveStudentFromEntranceToDR( Student studentToBeMoved ) {
        Student tmpStudent;
        tmpStudent = this.currentPlayerDashboard.removeStudentFromEntrance( studentToBeMoved );
        this.currentPlayerDashboard.moveToDR( tmpStudent );
    }

    private void moveStudentFromEntranceToIsland( Student chosenStudent, Island chosenIsland ) throws NoIslandException {
        Student tmpStudent = this.currentPlayerDashboard.removeStudentFromEntrance(chosenStudent);
        //chosenIsland
        for ( Island isl : islands) {
            if (isl.equals(chosenIsland)) {
                isl.addStudent(tmpStudent);
                return;
            }
        }
        throw new NoIslandException("Island not found, moveStudentFromEntranceToIsland failed");
    }

    private void moveStudentFromEntranceToIsland( Student chosenStudent, int chosenIslandPosition ) throws NoIslandException {
        Student tmpStudent = this.currentPlayerDashboard.removeStudentFromEntrance(chosenStudent);
        //chosenIsland
        if ( chosenIslandPosition<0 || chosenIslandPosition>(ISLANDSNUM-1) )
            throw new NoIslandException("chosenIslandPosition out of bound, moveStudentFromEntranceToIsland failed");
        //TODO possibile check sul fatto che l'isola non sia giá stata unificata ad un'altra
        if ( this.islands.get(chosenIslandPosition) == null )
            throw new NoIslandException("Island at chosenIslandPosition is null, moveStudentFromEntranceToIsland failed");
        else
            this.islands.get(chosenIslandPosition).addStudent(tmpStudent);
    }

    //TODO MERGE quando faremo il merge meglio cambiare il tipo statico di dashboardCollection in ArrayList e togliere dashboardsCollectionArrayListRef
    private void setNextCurrDashboard() {
        ArrayList<Dashboard> dashboardsCollectionArrayListRef;
        if ( ! (dashboardsCollection instanceof ArrayList<Dashboard>) )
            return;
        dashboardsCollectionArrayListRef = (ArrayList<Dashboard>) this.dashboardsCollection;
        int currentPlayerPosition = dashboardsCollectionArrayListRef.indexOf(this.currentPlayerDashboard);
        if ( currentPlayerPosition < (this.dashboardsCollection.size()-1) )
            currentPlayerPosition++;
        else
            currentPlayerPosition = 0;
        this.currentPlayerDashboard = dashboardsCollectionArrayListRef.get(currentPlayerPosition);
    }


    //END ZAMBO


    private void moveMotherNature(int posizioni) throws NoIslandException {
        int positionTmp = currentIsland;
        islands.get(positionTmp).setMotherNature(false);
        for (int i = 0; i < posizioni; i++){
            positionTmp = nextIsland(positionTmp);
        }
        currentIsland = positionTmp;
        islands.get(currentIsland).setMotherNature(true);
    }

    private void mergeIsland(int islandToBeMerged) throws NegativeNumberOfTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers {
        ArrayList<Student>[] studentsToBeMergedTmp;
        ArrayList<Student>[] studentsTmp;
        studentsToBeMergedTmp = islands.get(islandToBeMerged).getStudents();
        studentsTmp = islands.get(currentIsland).getStudents();
        for (int i = 0; i< 5;i++)
        {
            studentsTmp[i].addAll(studentsToBeMergedTmp[i]);
        }
        islands.get(currentIsland).setStudents(studentsTmp);
        islandPositions.remove(islandToBeMerged);
        totalNumIslands--;
        islands.get(currentIsland).addTowers(islands.get(islandToBeMerged).removeTowers());
    }

    private void checkNearbyIslands() throws NoTowerException, NoIslandException, NegativeNumberOfTowerException, InvalidNumberOfTowers, NoListOfSameColoredTowers {
        int nextIslandTmp, previousIslandTmp;
        nextIslandTmp = nextIsland(currentIsland);
        previousIslandTmp = previousIsland(currentIsland);
        if(islands.get(nextIslandTmp).getTowerColor() == islands.get(currentIsland).getTowerColor() && islands.get(nextIslandTmp).getTowerColor() != null)
            mergeIsland(nextIslandTmp);
        if(islands.get(previousIslandTmp).getTowerColor() == islands.get(currentIsland).getTowerColor() && islands.get(previousIslandTmp).getTowerColor() != null)
            mergeIsland(previousIslandTmp);
    }
//
    private int nextIsland(int position) throws NoIslandException { //metodo che ritorna l'indice della posizione della prosiima isola
        int tmp = islandPositions.indexOf(position);
        if(tmp > -1){
        if(tmp + 1 == islandPositions.size())
            return islandPositions.get(0);
        return islandPositions.get(tmp + 1);}
        else throw new NoIslandException("Isola non trovata");
    }

    private int previousIsland(int position) throws NoIslandException{ //metodo che ritorna l'indice della posizione della isola precedente
        int tmp = islandPositions.indexOf(position);
        if(tmp > -1){
        if(tmp - 1 == - 1)
            return islandPositions.get(islandPositions.size() - 1);
        return islandPositions.get(tmp - 1);}
        else throw new NoIslandException("Isola non trovata");
    }
}
