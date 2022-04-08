// Zambo
package model;

import model.exception.*;
import java.util.*;

public class Dashboard {
    private final Entrance entrance;
    private final HashMap<Color, DiningRoom> DiningRoomsList;
    private final ArrayList<Tower> towersCollection;  //TODO possibile cambiamento a HashSet quando riguarderemo la funzione di hash
    //private int towersNumber;
    private final TowerColor towerColor;
    private final Deck deck;
    private final HashMap<Color, Master> mastersList;
    private int coin;
    private boolean isKnight; //per effetto carta personaggio
    private static final int MAX_NUM_OF_TOWER = 8;

    //prova per vedere se funziona il rebase

    public Dashboard ( int numberOfTowers, TowerColor colorOfTower, Wizard chosenWizard ) {
        entrance = new Entrance();
        DiningRoomsList = new HashMap<>(Color.getDim());
        for ( Color c : Color.values() ) {
            DiningRoomsList.put( c, new DiningRoom(c) );
        }
        this.towersCollection = new ArrayList<>(0);
        for ( int i=0; i<numberOfTowers; i++ ) {
            this.towersCollection.add(new Tower(colorOfTower, i));
        }
        this.towerColor = colorOfTower;
        this.deck = new Deck(chosenWizard);
        this.mastersList = new HashMap<Color, Master>(Color.getDim());
        this.coin = 1; //Inizialmente sempre a 1
        this.isKnight = false; //Settato a false per la modalità esperto
    }

    public Dashboard ( Dashboard dashboardToCopy ) throws CardNotFoundException {
        this.entrance = new Entrance(dashboardToCopy.entrance);
        this.DiningRoomsList = new HashMap<>(dashboardToCopy.DiningRoomsList);
        this.towersCollection = new ArrayList<>(dashboardToCopy.towersCollection);
        this.towerColor = dashboardToCopy.towerColor;
        this.deck = new Deck(dashboardToCopy.deck);
        this.mastersList = new HashMap<>(dashboardToCopy.mastersList);
        this.coin = dashboardToCopy.coin;
        this.isKnight = dashboardToCopy.isKnight;
    }

    //Restituisce il numero di torri presenti nella dashboard
    public int getTowersNum() {
        return this.towersCollection.size();
    }

    //Restituisce il colore delle torri
    public TowerColor getTowerColor() {
        //Provato a fare con la clone ma impossibile da implementare nella classe TowerColor
        return this.towerColor;
    }


    public ArrayList<Tower> removeTowers ( int numberOfTower ) throws NegativeNumberOfTowerException {
        int newTowersNumber = this.towersCollection.size() - numberOfTower;
        if ( newTowersNumber < 0 )
            throw new NegativeNumberOfTowerException("Insufficient number of tower");

        ArrayList<Tower> tmp = new ArrayList<>(0);
        for ( int i=0; i<numberOfTower; i++) {
            tmp.add(this.towersCollection.get(this.towersCollection.size()-1));
            this.towersCollection.remove(this.towersCollection.size()-1);
        }

        return tmp;
    }

    public void addTowers ( ArrayList<Tower> towersToBeAdded ) throws MaxNumberOfTowerPassedException{
        int newTowersNumber = this.towersCollection.size() + towersToBeAdded.size();
        if ( newTowersNumber > MAX_NUM_OF_TOWER )
            throw new MaxNumberOfTowerPassedException("Too much tower on the Dashboard");

        this.towersCollection.addAll(towersToBeAdded);
        //TODO possibile Exception sull'inserimento di una torre uguale ad una già presente
    }

    public Set<Student> showEntrance () {
        return entrance.getStudents();
    }

    public void moveToEntrance( Set<Student> studentsList ) throws MaxNumberException, StudentIDAlreadyExistingException {
        entrance.insertStudents( studentsList );
    }

    public Student removeStudentFromEntrance( Student chosenStudent ){
        try {
            entrance.removeStudent(chosenStudent);
            return new Student(chosenStudent);
        }
        catch ( InexistentStudentException e ) {
            System.out.println(e.toString());
            return null;
        }
    }

    public void moveToDR( Set<Student> studentsSet ) throws MaxNumberException, WrongColorException, StudentIDAlreadyExistingException, InexistentStudentException {
        for( Student s:studentsSet ) {
            insertInDRbyStudentColor(s);
            this.entrance.removeStudent(s);
        }
    }

    public void moveToDR( Student student ) throws MaxNumberException, WrongColorException, StudentIDAlreadyExistingException, InexistentStudentException {
        insertInDRbyStudentColor(student);
            this.entrance.removeStudent(student);
    }

    private void insertInDRbyStudentColor(Student student) throws MaxNumberException, NullPointerException, WrongColorException, StudentIDAlreadyExistingException {
        if ( this.DiningRoomsList.containsKey(student.getColor()) )
            this.DiningRoomsList.get(student.getColor()).insertStudent(student);
        else
            throw new WrongColorException("Color not found during the insertion of student in DR");
    }

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
        return new Card(this.deck.getCurrentCard());
    }

    public void insertMaster( Master m ) {
        //valutare se può causare un'eccezione in qualche caso
        mastersList.put(m.getColor(),m);
    }

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
        if ( this.coin > coinToBeRemoved )
            this.coin = coinToBeRemoved;
        else
            throw new InsufficientCoinException("You tried to remove too much coin, number of coin is: "+this.coin+", you tried to remove "+coinToBeRemoved+" coins");
    }

    public boolean hasKnightPrivilege() {
        return isKnight;
    }

    public void setKnight( boolean setValue ) {
        //TODO controllare possibile eccezione in caso non venga cambiato il valore
        isKnight = setValue;
    }

}
