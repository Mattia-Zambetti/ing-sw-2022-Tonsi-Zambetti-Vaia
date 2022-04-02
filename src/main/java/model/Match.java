package model;

import model.FigureCards.FigureCard;
import model.exception.MaxNumberException;

import java.util.*;

public class Match {
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
            bag.instance();


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

    private Set<Student> pullAndPushStudentsOnCloud(int cloudNum) throws MaxNumberException {
        return clouds.get(cloudNum).takeAndPutStudents(bag.removeStudents(Cloud.getStudentsNumOnCloud()));
    }

    public void chooseCloud() throws MaxNumberException {
        int number=1;
        int chosenCloud=0;
        for (Cloud c:clouds) {
            System.out.println("Cloud"+ number+":\n"+c.toString());
            number++;
        }
        //non mi ricordo come si legge da tastiera, domani guardo
        if(chosenCloud<=playersNum && chosenCloud>0 )
            pullAndPushStudentsOnCloud(chosenCloud);
        else
            throw new MaxNumberException("Numero scelto errato");
    }

    //ZAMBO
    //aspetto metodo di davide per rimuovere da nuvola
    private void moveStudentsFromCloudToEntrance( Cloud chosenCloud ) {
    }

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


}
