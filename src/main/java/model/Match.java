package model;

import model.FigureCards.FigureCard;
import model.exception.MaxNumberException;
import model.exception.NoIslandException;
import model.exception.NoTowerException;

import java.util.*;

public class Match {
    private List<Island> islands;
    private List<Cloud> clouds;
    private Bag bag;
    private List<Dashboard> dashboards;
    private Dashboard currentPlayer;
    private HashMap<Color, Master> masters;
    private Collection<FigureCard> figureCards;
    private int playersNum;
    private boolean isExpertMode;
    private int currentIsland, totalNumIslands;
    private static List<Integer> islandPositions = new ArrayList<>();

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
        currentIsland = 0;
        totalNumIslands = 0;
        for (int i=0; i< ISLANDSNUM; i++){
            islands.add(new Island(motherNature, i));
            motherNature=false;
            islandPositions.add(i);
            totalNumIslands++;
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

    private void moveMotherNature(int posizioni) throws NoIslandException {
        int positionTmp = currentIsland;
        islands.get(positionTmp).setMotherNature(false);
        for (int i = 0; i < posizioni; i++){
            positionTmp = nextIsland(positionTmp);
        }
        currentIsland = positionTmp;
        islands.get(currentIsland).setMotherNature(true);
    }

    private void mergeIsland(int island){
        ArrayList<Student>[] studentsToBeMergedTmp;
        ArrayList<Student>[] studentsTmp;
        studentsToBeMergedTmp = islands.get(islandPositions.get(island)).getStudents();
        studentsTmp = islands.get(islandPositions.get(currentIsland)).getStudents();
        for (int i = 0; i< 5;i++)
        {
            studentsTmp[i].addAll(studentsToBeMergedTmp[i]);
        }
        islands.get(islandPositions.get(currentIsland)).setStudents(studentsTmp);
        islandPositions.remove(island);
        totalNumIslands--;
        islands.get(islandPositions.get(currentIsland)).addTowerNumber();
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
