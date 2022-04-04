//Tonsi, Zambo, Vaia
package model;

import model.FigureCards.FigureCard;
import model.exception.MaxNumberException;

import java.util.*;
public class Match extends Observable{
    private List<Island> islandsList;
    private List<Cloud> clouds;
    private Bag bag;
    private Collection<Dashboard> dashboardsCollection;
    private Dashboard currentPlayerDashboard;
    private HashMap<Color, Master> mastersMap;
    private Collection<FigureCard> figureCards;
    private int playersNum;
    private boolean isExpertMode;

    //utile definire tanti attributi così per avere codice facilmente modificabile
    private static final int ISLANDSNUM=12;

    private static final int STUDENTSONCLOUD2PLAYERS= 3;
    private static final int STUDENTSONCLOUD3PLAYERS= 4;
    private static final int STUDENTSONCLOUD4PLAYERS= 3;

    private static final int INITIALNUMOFISLANDS= 12;//ma c'è già sopra

    private static final int MAXPLAYERSNUM=4;
    private static final int MINPLAYERSNUM=2;

    public Match(int playersNum, boolean isExpertMode) {
        try {
            this.playersNum = playersNum;
            if (this.playersNum <= MAXPLAYERSNUM && this.playersNum >= MINPLAYERSNUM) {

                this.isExpertMode = isExpertMode;

                //per essere più precisi, a noi non serve sapere l'ordine totale ma solo la prossima/precedente, dovrebbe essere più
                // efficiente la linked
                islandsList = new ArrayList<Island>();

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
        for (int i=0; i< ISLANDSNUM; i++){
            islandsList.add(new Island(motherNature, i+1));
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

    private void moveFromEntranceToIsland( Student chosenStudent, Island chosenIsland ) {
        Student tmpStudent = this.currentPlayerDashboard.removeStudentFromEntrance(chosenStudent);
        //chosenIsland

    }

    private void setNextCurrDashboard() {

    }
    //END ZAMBO


}
