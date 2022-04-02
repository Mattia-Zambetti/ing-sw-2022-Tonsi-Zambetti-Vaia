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

    private static final int ISLANDSNUM=12; //utile definire tanti attributi così per avere codice facilmente modificabile
    private static final int STUDENTSONCLOUD2PLAYERS= 3;
    private static final int STUDENTSONCLOUD3PLAYERS= 4;
    private static final int STUDENTSONCLOUD4PLAYERS= 3;
    private static final int INITIALNUMOFISLANDS= 12;

    public Match(int playersNum, boolean isExpertMode) throws MaxNumberException {
        if(playersNum<=4 && playersNum>1) {
            this.playersNum = playersNum;
            this.isExpertMode=isExpertMode;

            //per essere più precisi, a noi non serve sapere l'ordine totale ma solo la prossima/precedente, dovrebbe essere più efficiente
            islandsList = new ArrayList<Island>();

            initializeIslands();

            Cloud.setStudentsNumOnCloud(chooseStudentsNumOnCLoud());
            Bag.instance();


            clouds = new ArrayList<Cloud>();
            initializeClouds();

            dashboardsCollection = new ArrayList<Dashboard>();
            currentPlayerDashboard = null;
            for (Color c : Color.values()) {
                mastersMap.put(c, new Master(c));
            }
        }
        else throw new MaxNumberException("un match può avere dai 2 ai 4 giocatori");
    }

    //TONSI
    private int chooseStudentsNumOnCLoud() {
        if(playersNum ==2){
            return STUDENTSONCLOUD2PLAYERS;
        }else if(playersNum ==3)
            return STUDENTSONCLOUD3PLAYERS;
        else return STUDENTSONCLOUD4PLAYERS;
    }

    private void initializeClouds() throws MaxNumberException {
        for (int i=0; i< playersNum; i++){
            clouds.add(new Cloud(bag.removeStudents(Cloud.getStudentsNumOnCloud())));
        }
    }

    private void initializeIslands() {
        boolean motherNature=true;
        for (int i=0; i< ISLANDSNUM; i++){
            islandsList.add(new Island(motherNature, i+1));
            motherNature=false;
        }
    }

    private Set<Student> pullStudentsFromCloud(int cloudNum) throws MaxNumberException {
        if(cloudNum<=playersNum && cloudNum>0 && clouds.get(cloudNum).toString()!="") {
            return clouds.get(cloudNum).takeStudents();
        }else throw new MaxNumberException("wrong cloud's number");
    }

    private void refillClouds() throws MaxNumberException {
        for(Cloud c:clouds){
            c.refillCloud(Bag.removeStudents(Cloud.getStudentsNumOnCloud()));
        }
    }


    public void moveStudentsFromCloudToEntrance(int chosenCloud) throws MaxNumberException {
        if(chosenCloud<=playersNum && chosenCloud>0 && clouds.get(chosenCloud).toString().equals(""))
            currentPlayerDashboard.moveToEntrance(pullStudentsFromCloud(chosenCloud));
        else
            throw new MaxNumberException("Wrong cloud's number");
        notifyObservers(); //non so cosa potrebbe notificare per ora, vedremo
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
