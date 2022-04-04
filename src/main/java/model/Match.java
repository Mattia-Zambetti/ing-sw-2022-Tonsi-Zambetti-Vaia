//Tonsi, Zambo, Vaia
package model;

import model.FigureCards.FigureCard;
import model.exception.MaxNumberException;
import model.exception.NoIslandException;
import model.exception.NoTowerException;

import java.util.*;
public class Match extends Observable{
    private List<Island> islands;
    private List<Cloud> clouds;
    private Bag bag;
    private Collection<Dashboard> dashboardsCollection; //Ipotizzo che l'ordine delle Dashboard nella collection sia lo stesso dei turni dei giocatori nella partita
    private Dashboard currentPlayerDashboard;
    private HashMap<Color, Master> mastersMap;
    private Collection<FigureCard> figureCards;
    private int playersNum;
    private boolean isExpertMode;
    private int currentIsland, totalNumIslands;
    private static List<Integer> islandPositions = new ArrayList<>();

    //utile definire tanti attributi così per avere codice facilmente modificabile
    private static final int ISLANDSNUM=12;

    private static final int STUDENTSONCLOUD2PLAYERS= 3;
    private static final int STUDENTSONCLOUD3PLAYERS= 4;
    private static final int STUDENTSONCLOUD4PLAYERS= 3;

    //Da Zambo, ho rimosso una costante che avevo scritto io ma che c'era già e che non veniva usata, spero non dia problemi durante il merge

    private static final int MAXPLAYERSNUM=4;
    private static final int MINPLAYERSNUM=2;

    public Match(int playersNum, boolean isExpertMode) {
        try {
            this.playersNum = playersNum;
            if (this.playersNum <= MAXPLAYERSNUM && this.playersNum >= MINPLAYERSNUM) {

                this.isExpertMode = isExpertMode;

                //per essere più precisi, a noi non serve sapere l'ordine totale ma solo la prossima/precedente, dovrebbe essere più
                // efficiente la linked
                islands = new ArrayList<Island>();

                initializeIslands();
            islands = new LinkedList<Island>(); //per essere più precisi, a noi non serve sapere l'ordine totale ma solo
                                                // la prossima/precedente, dovrebbe essere più efficiente
            initializeIslands();

                Cloud.setStudentsNumOnCloud(chooseStudentsNumOnCLoud());
                Bag.instance();


                clouds = new ArrayList<Cloud>();
                initializeClouds();

                dashboardsCollection = new ArrayList<Dashboard>();
                currentPlayerDashboard = null;
                mastersMap=new HashMap<>();
                for (Color c : Color.values()) {
                    mastersMap.put(c, new Master(c));
                }
            } else throw new MaxNumberException("A match can have only from 2 to 4 players");
        }catch (MaxNumberException e){
            System.out.println(e.getMessage());
        }
        //else throw new MaxNumberException("un match può avere dai 2 ai 4 giocatori");
    }

    //TONSI


    public static int getMAXPLAYERSNUM() {
        return MAXPLAYERSNUM;
    }

    public static int getMINPLAYERSNUM() {
        return MINPLAYERSNUM;
    }

    public int getPlayersNum() {
        return playersNum;
    }

    private int chooseStudentsNumOnCLoud() {
        if(playersNum ==2){
            return STUDENTSONCLOUD2PLAYERS;
        }else if(playersNum ==3)
            return STUDENTSONCLOUD3PLAYERS;
        else return STUDENTSONCLOUD4PLAYERS;
    }

    private void initializeClouds() {
        try {
            for (int i = 0; i < playersNum; i++) {
                clouds.add(new Cloud(bag.removeStudents(Cloud.getStudentsNumOnCloud())));
            }
        }catch(MaxNumberException e){
            System.out.println(e.getMessage());
        }
    }

    private void initializeIslands() {
        boolean motherNature=true;
        currentIsland = 0;
        totalNumIslands = 0;
        for (int i=0; i< ISLANDSNUM; i++){
            islands.add(new Island(motherNature, i));
            //islands.add(new Island(motherNature, i+1));
            motherNature=false;
        }
    }

    private Set<Student> pullStudentsFromCloud(int cloudNum) {
        try {
            if (cloudNum <= playersNum && cloudNum > 0 && clouds.get(cloudNum).toString() != "") {
                return clouds.get(cloudNum).takeStudents();
            } else throw new MaxNumberException("wrong cloud's number");
        }catch (MaxNumberException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void refillClouds() throws MaxNumberException {
        for(Cloud c:clouds){
            c.refillCloud(Bag.removeStudents(Cloud.getStudentsNumOnCloud()));
        }
    }


    public void moveStudentsFromCloudToEntrance(int chosenCloud) {
        try {
            if (chosenCloud <= playersNum && chosenCloud > 0 && !clouds.get(chosenCloud-1).toString().equals(""))
                currentPlayerDashboard.moveToEntrance(pullStudentsFromCloud(chosenCloud-1));
            else
                throw new MaxNumberException("This cloud doesn't exist");
            notifyObservers();//non so cosa potrebbe notificare per ora, vedremo
        }catch (MaxNumberException e){
            System.out.println(e.getMessage());
        }
    }

    private void toStringStudentsOnClass() {
        String res = "";
        for (Cloud c : clouds) {
            res += c.toString();
        }
        notifyObservers(res);
    }

    public void showCards(){
        notifyObservers(new ArrayList<>(currentPlayerDashboard.showCards()));
    }

    public void chooseCard(Card chosenCard){
        currentPlayerDashboard.playChosenCard(chosenCard);
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
        if ( chosenIslandPosition<0 || chosenIslandPosition>(this.totalNumIslands-1) )
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

    private void mergeIsland(int islandToBeMerged){
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
        islands.get(currentIsland).addTowerNumber();
    }

    private void checkNearbyIslands() throws NoTowerException, NoIslandException {
        int nextIslandTmp, previousIslandTmp;
        nextIslandTmp = nextIsland(currentIsland);
        previousIslandTmp = previousIsland(currentIsland);
        if(islands.get(nextIslandTmp).getTowerColor() == islands.get(currentIsland).getTowerColor())
            mergeIsland(nextIslandTmp);
        if(islands.get(previousIslandTmp).getTowerColor() == islands.get(currentIsland).getTowerColor())
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
