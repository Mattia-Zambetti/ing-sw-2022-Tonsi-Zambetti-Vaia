// Zambo
package model;

import graphicAssets.CLIgraphicsResources;
import model.exception.*;

import java.io.Serializable;
import java.util.*;

public class Dashboard implements Serializable {
    private final Entrance entrance;
    private final HashMap<Color, DiningRoom> DiningRoomsList;
    private final ArrayList<Tower> towersCollection;  //TODO possibile cambiamento a HashSet quando riguarderemo la funzione di hash
    //private int towersNumber;
    private final TowerColor towerColor;
    private final Deck deck;
    private final HashMap<Color, Master> mastersList;
    private int coin,playerNumber;
    private boolean farmerEffect,dashboardWithTowers = false;
    private boolean isKnight = false; //per effetto carta personaggio
    private static final int MAX_NUM_OF_TOWER = 8;
    private Player player;

    private Dashboard buddy;


    public Dashboard ( int numberOfTowers, TowerColor colorOfTower, Wizard chosenWizard, String playerNickname, int playerNumber ) {

        if ( numberOfTowers > MAX_NUM_OF_TOWER )
            throw new IllegalArgumentException("Tried to create a Dashboard with a number of Towers higher than MAX_NUM_OF_TOWER ( =" + MAX_NUM_OF_TOWER + " )");

        entrance = new Entrance();
        DiningRoomsList = new HashMap<>(Color.getDim());
        for ( Color c : Color.values() ) {
            DiningRoomsList.put( c, new DiningRoom(c) );
        }
        this.towersCollection = new ArrayList<>(0);
        for ( int i=0; i<numberOfTowers; i++ ) {
            this.towersCollection.add(new Tower(colorOfTower, i));
        }
        if(numberOfTowers != 0)
            dashboardWithTowers = true;
        this.towerColor = colorOfTower;
        this.deck = new Deck(chosenWizard);
        this.mastersList = new HashMap<Color, Master>(Color.getDim());
        this.coin = 1; //At the start of a match it is always one
        this.isKnight = false;
        this.farmerEffect = false;
        this.player = new Player(playerNickname);
        this.playerNumber = playerNumber;
    }

    public Dashboard ( Dashboard dashboardToCopy ) throws NullPointerException {
        if ( dashboardToCopy == null )
            throw new NullPointerException("Tried to create a new Dashboard from a null Dashboard");
        this.entrance = new Entrance(dashboardToCopy.entrance);
        this.DiningRoomsList = new HashMap<>(dashboardToCopy.DiningRoomsList);
        this.towersCollection = new ArrayList<>(dashboardToCopy.towersCollection);
        this.towerColor = dashboardToCopy.towerColor;
        this.deck = new Deck(dashboardToCopy.deck);
        this.mastersList = new HashMap<>(dashboardToCopy.mastersList);
        this.coin = dashboardToCopy.coin;
        this.isKnight = dashboardToCopy.isKnight;
        this.farmerEffect = dashboardToCopy.farmerEffect;
        this.player = dashboardToCopy.player;
    }


    public int getTowersNum() {
        return this.towersCollection.size();
    }

    public boolean isFarmerEffect() {
        return farmerEffect;
    }

    public void setFarmerEffect(boolean farmerEffect) {
        this.farmerEffect = farmerEffect;
    }

    public TowerColor getTowerColor() {
        return this.towerColor;
    }

    /** Removes and returns as an ArrayList {@code numberOfTower} towers from the dashboard */
    public ArrayList<Tower> removeTowers ( int numberOfTower ) throws NoMoreTowerException {

        int newTowersNumber = this.towersCollection.size() - numberOfTower;
        if ( newTowersNumber <= 0 )
            throw new NoMoreTowerException("Insufficient number of tower", towerColor);

        ArrayList<Tower> tmp = new ArrayList<>(0);
        for ( int i=0; i<numberOfTower; i++) {
            tmp.add(this.towersCollection.get(this.towersCollection.size()-1));
            this.towersCollection.remove(this.towersCollection.size()-1);
        }

        return tmp;
    }

    /** Add all towers contained in {@code towersToBeAdded} into the dashboard */
    public void addTowers ( ArrayList<Tower> towersToBeAdded ) throws MaxNumberOfTowerPassedException, TowerIDAlreadyExistingException, NullPointerException {
        if ( towersToBeAdded == null )
            throw new NullPointerException("Tried to add null List instead of a List of towers in your Dashboard");
        int newTowersNumber = this.towersCollection.size() + towersToBeAdded.size();
        if ( newTowersNumber > MAX_NUM_OF_TOWER )
            throw new MaxNumberOfTowerPassedException("Too much tower on the Dashboard");
        for ( Tower tToAdd : towersToBeAdded ) {
            if ( tToAdd == null )
                throw new NullPointerException("Tried to add null instead of a tower in your Dashboard");
            for ( Tower t : this.towersCollection )
                if ( t.getId() == tToAdd.getId() )
                    throw new TowerIDAlreadyExistingException("Tried to add a tower with the same ID of another one already present in your Dashboard");
        }

        this.towersCollection.addAll(towersToBeAdded);
    }

    /** Returns all the students contained in the entrance */
    public Set<Student> showEntrance () {
        return entrance.getStudents();
    }

    /** Move all students contained in {@code studentsList} into the entrance of the dashboards */
    public void moveToEntrance( Set<Student> studentsList ) throws MaxNumberException, StudentIDAlreadyExistingException {
        entrance.insertStudents( studentsList );
    }

    /** Removes and returns the students contained in {@code chosenStudent} from the entrance */
    public Student removeStudentFromEntrance( Student chosenStudent ) throws InexistentStudentException, NullPointerException {
            return entrance.removeStudent(chosenStudent);
    }

    /** Moves all students contained in {@code studentsSet} into the correct DR of the dashboard by checking the student color */
    public void moveToDR(Set<Student> studentsSet ) throws MaxNumberException, WrongColorException, StudentIDAlreadyExistingException, InexistentStudentException {
        for( Student s:studentsSet ) {
            insertInDRbyStudentColor(s);
        }
    }

    /** Moves a single student into the correct DR of the dashboard */
    public void moveToDR(Student student ) throws MaxNumberException, WrongColorException, StudentIDAlreadyExistingException, InexistentStudentException {
        insertInDRbyStudentColor(student);
    }

    /** Inserts the student into the correct DR by checking his color */
    private void insertInDRbyStudentColor(Student student) throws MaxNumberException, NullPointerException, WrongColorException, StudentIDAlreadyExistingException {
        if ( this.DiningRoomsList.containsKey(student.getColor()) )
            this.DiningRoomsList.get(student.getColor()).insertStudent(student);
        else
            throw new WrongColorException("Color not found during the insertion of student in DR");
    }


    public void addStudentToEntrance(Student student) throws MaxNumberException, StudentIDAlreadyExistingException {
        entrance.insertStudent(student);
    }

    /** Returns all students contained in every DR, the returned List contains all students despite the color */
    public List<Student> showDiningRoom(){
        Set<Student> tmp = new HashSet<>();
        for(Color c : Color.values())
            tmp.addAll(DiningRoomsList.get(c).getStudents());
        return tmp.stream().toList();
    }

    /** Removes the {@code student} from the correct DR */
    public void removeInDRbyStudentColor(Student student) throws MaxNumberException, NullPointerException, WrongColorException, StudentIDAlreadyExistingException {
        if ( this.DiningRoomsList.containsKey(student.getColor()) )
            this.DiningRoomsList.get(student.getColor()).removeStudent(student);
        else
            throw new WrongColorException("Color not found during the insertion of student in DR");
    }

    /** Removes and returns a Set of students composed by {@code studentsToRemove} students of the chosen color, taken from the DR */
    public Set<Student> removeStudentFromDRbyColor(Color color,int studentsToRemove) throws MaxNumberException, NullPointerException, WrongColorException, StudentIDAlreadyExistingException {
        Set<Student> tmp;
        Student studentToBag;
        tmp =DiningRoomsList.get(color).getStudents();
        Set<Student> tmpToAddToBag = new HashSet<>();
        if(tmp.size() < studentsToRemove){
            for(Student s: tmp){
                studentToBag = this.DiningRoomsList.get(color).removeStudentByColor();
                if(studentToBag != null)
                    tmpToAddToBag.add(studentToBag);
            }
        }
        else{
            for (int i = 0; i < studentsToRemove; i++){
                studentToBag = this.DiningRoomsList.get(color).removeStudentByColor();
                if(studentToBag != null)
                    tmpToAddToBag.add(studentToBag);
            }
        }
        return tmpToAddToBag;
    }

    /** Returns the number of students in the {@code drColor} DR */
    public int getStudentsNumInDR ( Color drColor ) throws WrongColorException {
        if ( this.DiningRoomsList.containsKey(drColor) )
            return this.DiningRoomsList.get(drColor).getStudentsNumber();
        else
            throw new WrongColorException("Color not found during the search of students in DR");
    }

    public Set<Card> showCards() {
        return deck.getCards();
    }

    public void playChosenCard( Card chosenCard ) throws CardNotFoundException {
            deck.playCard(chosenCard);
    }

    public Card getCurrentCard() {
        return this.deck.getCurrentCard();
    }

    public void insertMaster( Master m ) {
        mastersList.put(m.getColor(),m);
    }

    /** Remove the master of the chosen color if present, otherwise throws NoMasterException */
    public Master removeMaster( Color color ) throws NoMasterException{
        Master RemovedMaster;
        RemovedMaster = mastersList.remove(color);
        if ( RemovedMaster == null )
            throw new NoMasterException("Error: "+color.toString()+" master is absent");
        return RemovedMaster;
    }

    public boolean haveMaster( Color color ) {
        return mastersList.containsKey(color);
    }

    /** Returns the List of Master owned by this player */
    public Collection<Master> getMastersList() {
        HashMap<Color, Master> mastersListCopy;
        mastersListCopy = (HashMap<Color, Master>) mastersList.clone();
        //Testare che la clone crei effettivamente una copia separata tale per cui le modifiche non si ripercuotono sull'oggetto principale
        return mastersListCopy.values().stream().toList();
    }

    public void addCoin() {
        this.coin++;
    }

    public int getCoinsNumber() {
        return this.coin;
    }

    public void removeCoin ( int coinToBeRemoved ) throws InsufficientCoinException {
        if ( this.coin >= coinToBeRemoved )
            this.coin -= coinToBeRemoved;
        else
            throw new InsufficientCoinException("You tried to remove too much coin, number of coin is: "+this.coin+", you tried to remove "+coinToBeRemoved+" coins");
    }

    public boolean hasKnightPrivilege() {
        return isKnight;
    }

    /**Tt sets the isKnight boolean true while the player has played the knight figure card*/
    public void setKnight( boolean setValue ) {
        isKnight = setValue;
    }

    public Player getPlayer() {
        return player;
    }

    public Wizard getWizard() {
        return deck.getWizard();
    }

    /** Returns the CLI version of this dashboard as a String */
    @Override
    public String toString() {
        String OutputString = "\n====================================================\n";
        for ( int j=0; j<((41-getPlayer().getNickname().length())/2); j++)
            OutputString = OutputString.concat(" ");
        OutputString = OutputString.concat(getPlayer().getNickname() + " dashboard\n____________________________________________________\n");
        OutputString = OutputString.concat("          "+entrance.toString());
        OutputString = OutputString.concat("\n____________________________________________________");
        for ( Color c : Color.values() ) {
            OutputString = OutputString.concat("\n "+DiningRoomsList.get(c).toString()+"\t");
            if ( mastersList.containsKey(c) )
                OutputString = OutputString.concat(CLIgraphicsResources.ColorCLIgraphicsResources.getTextColor(c) + " - HAVE MASTER (=" + CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR);
            else
                OutputString = OutputString.concat(CLIgraphicsResources.ColorCLIgraphicsResources.getTextColor(c) + " - NO MASTER )=" + CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR);
        }
        OutputString = OutputString.concat("\n____________________________________________________");
        OutputString = OutputString.concat("\n               NUMBER OF TOWERS : " + towersCollection.size() + "\n              COLOR OF TOWERS : " + towerColor.toString() );
        OutputString = OutputString.concat("\n====================================================\n");
        return OutputString;
    }

    /** Sets the buddy of the PLayer who owns this dashboard */
    public void setBuddy(Dashboard buddy){
        this.buddy = buddy;
    }

    /** Returns the dashboard of the Player who has the towers between Players in the same team */
    public Dashboard getBuddyWithTowers(){
        if(dashboardWithTowers)
            return this;
        return this.buddy;
    }

    public Dashboard getBuddy(){
        return this.buddy;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dashboard d))
            return false;
        return player.equals(d.getPlayer());
    }*/

}
