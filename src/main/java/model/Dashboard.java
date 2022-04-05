// Zambo
package model;

import model.exception.*;
import java.util.*;

public class Dashboard {
    private final Entrance entrance;
    private final DiningRoom redDiningRoom, blueDiningRoom, yellowDiningRoom, pinkDiningRoom, greenDiningRoom;
    private int towersNumber;
    private final TowerColor towerColor;
    private final Deck deck;
    private final HashMap<Color, Master> mastersList;
    private int coin;
    private boolean isKnight; //per effetto carta personaggio

    //prova per vedere se funziona il rebase

    public Dashboard ( int numberOfTowers, TowerColor colorOfTower, Wizard chosenWizard ) {
        entrance = new Entrance();
        redDiningRoom = new DiningRoom(Color.RED);
        blueDiningRoom = new DiningRoom(Color.BLUE);
        yellowDiningRoom = new DiningRoom(Color.YELLOW);
        pinkDiningRoom = new DiningRoom(Color.PINK);
        greenDiningRoom = new DiningRoom(Color.GREEN);
        this.towersNumber = numberOfTowers;
        this.towerColor = colorOfTower;
        //da sistemare con Davide la questione file json per generare le carte alla cre<ione del deck
        this.deck = new Deck(chosenWizard);
        this.mastersList = new HashMap<Color, Master>(Color.getDim());
        this.coin = 1; //Inizialmente sempre a 1
        this.isKnight = false; //Settato a false per la modalità esperto
    }

    //Restituisce il numero di torri presenti nella dashboard
    public int getTowersNum() {
        return this.towersNumber;
    }

    //Restituisce il colore delle torri
    public TowerColor getTowerColor() {
        //Provato a fare con la clone ma impossibile da implementare nella classe TowerColor
        return this.towerColor;
    }


    public void removeTowers ( int numberOfTower ) throws NegativeNumberOfTowerException {
        int newTowersNumber = this.towersNumber - numberOfTower;
        if ( newTowersNumber < 0 )
            throw new NegativeNumberOfTowerException("Insufficient number of tower");
        else
            this.towersNumber = newTowersNumber;
    }

    //TODO aggiungere metodo addTowers(int numberOfTowers)

    public ArrayList<Student> showEntrance () {
        return entrance.getStudents();
    }

    public void moveToEntrance( Set<Student> studentsList ) throws MaxNumberException {
        entrance.insertStudents( studentsList );
    }

    public void moveToDR( Set<Student> studentsSet ) {
        try{
            for( Student s:studentsSet ) {
                choseDRfromStudentColor(s);
                this.entrance.removeStudent(s);
            }
        }
        catch ( MaxNumberException e ) {
            System.out.println(e.toString());
        }
        catch ( NullPointerException e ) {
            System.out.println(e.toString());
        }
        catch ( WrongColorException e ) {
            System.out.println(e.toString());
        }
        catch (InexistentStudentException e) {
            System.out.println(e.toString());
        }
    }

    public void moveToDR( Student student ) {
        try {
            choseDRfromStudentColor(student);
            this.entrance.removeStudent(student);
        }
        catch ( MaxNumberException e ) {
            System.out.println(e.toString());
        }
        catch ( NullPointerException e ) {
            System.out.println(e.toString());
        }
        catch ( WrongColorException e ) {
            System.out.println(e.toString());
        }
        catch (InexistentStudentException e) {
            System.out.println(e.toString());
        }
    }

    private void choseDRfromStudentColor(Student student) throws MaxNumberException, WrongColorException {
        switch ( student.getColor() ) {
            case RED:
                redDiningRoom.insertStudent(student);
                break;
            case BLUE:
                blueDiningRoom.insertStudent(student);
                break;
            case YELLOW:
                yellowDiningRoom.insertStudent(student);
                break;
            case PINK:
                pinkDiningRoom.insertStudent(student);
                break;
            case GREEN:
                greenDiningRoom.insertStudent(student);
                break;
        }
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

    public Set<Card> showCards() {
        return deck.getCards();
    }

    public void playChosenCard( Card chosenCard ){
        try{
            deck.playCard(chosenCard);
        }
        catch ( CardNotFoundException e )
        {
            System.out.println(e.toString());
        }
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
        return mastersListCopy.values();
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
