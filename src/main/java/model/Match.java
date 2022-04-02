package model;

import model.FigureCards.FigureCard;
import model.exception.MaxNumberException;

import java.util.*;

public class Match extends Observable{
    private List<Island> islands;
    private List<Cloud> clouds;
    private Bag bag;
    private List<Dashboard> dashboards;
    private Dashboard currentPlayer;
    private HashMap<Color, Master> masters;
    private Collection<FigureCard> figureCards;
    private int playersNum;
    private boolean isExpertMode;

    private static final int ISLANDSNUM=12; //utile definire tanti attributi così per avere codice facilmente modificabile
    private static final int STUDENTSONCLOUD2PLAYERS= 3;
    private static final int STUDENTSONCLOUD3PLAYERS= 4;
    private static final int STUDENTSONCLOUD4PLAYERS= 3;

    public Match(int playersNum, boolean isExpertMode) throws MaxNumberException {
        if(playersNum<=4 && playersNum>1) {
            this.playersNum = playersNum;
            this.isExpertMode=isExpertMode;

            islands = new LinkedList<Island>(); //per essere più precisi, a noi non serve sapere l'ordine totale ma solo
                                                // la prossima/precedente, dovrebbe essere più efficiente
            initializeIslands();

            Cloud.setStudentsNumOnCloud(chooseStudentsNumOnCLoud());
            bag.instance();


            clouds = new ArrayList<Cloud>();
            initializeClouds();

            dashboards = new ArrayList<Dashboard>();
            currentPlayer = null;
            for (Color c : Color.values()) {
                masters.put(c, new Master(c));
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
            islands.add(new Island(motherNature, i+1));
            motherNature=false;
        }
    }

    private Set<Student> pullStudentsFromCloud(int cloudNum) throws MaxNumberException {
        if(cloudNum<=playersNum && cloudNum>0 && clouds.get(cloudNum).toString()!="") {
            return clouds.get(cloudNum).takeStudents();
        }else throw new MaxNumberException("Numero di nuvola sbagliato");
    }

    private void refillClouds() throws MaxNumberException {
        for(Cloud c:clouds){
            c.refillCloud(Bag.removeStudents(Cloud.getStudentsNumOnCloud()));
        }
    }


    public void chooseCloud(int chosenCloud) throws MaxNumberException {
        if(chosenCloud<=playersNum && chosenCloud>0 )
            currentPlayer.moveToEntrance(pullStudentsFromCloud(chosenCloud));
        else
            throw new MaxNumberException("Numero scelto errato");
        notifyObservers(); //non so cosa potrebbe notificare per ora, vedremo
    }

    private void toStringStudentsOnClass(){
        String res="";
        for(Cloud c: clouds){
            res+=c.toString();
        }
        notifyObservers(res);
    }
}
