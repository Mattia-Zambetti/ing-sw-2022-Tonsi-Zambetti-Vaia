// Zambo
package model;

import model.exception.*;
import java.util.*;

public class Dashboard {
    private Entrance entrance;
    private DiningRoom redDiningRoom, blueDiningRoom, yellowDiningRoom, pinkDiningRoom, greenDiningRoom;
    private int towersNumber;
    private final TowerColor towerColor;
    private Deck deck;
    private ArrayList<Master> mastersList;

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
        this.mastersList = new ArrayList<Master>(5);
    }

    public int getTowersNum() {
        return this.towersNumber;
    }

    public TowerColor getTowerColor() {
        //Provato a fare con la clone ma impossibile da implementare nella classe TowerColor
        final TowerColor tc = this.towerColor;
        return tc;
    }

    public void removeTowers ( int numberOfTower ) throws NegativeNumberOfTowerException {
        int newTowersNumber = this.towersNumber - numberOfTower;
        if ( newTowersNumber < 0 )
            throw new NegativeNumberOfTowerException("Insufficient number of tower");
        else
            this.towersNumber = newTowersNumber;
    }

    public Set<Student> showEntrance () {
        Set<Student> students = entrance.getStudents();
        return students;
    }

    public void moveToEntrance( Set<Student> studentsList ) throws MaxNumberException {
        entrance.insertStudents( studentsList );
    }

    public void moveToDR( Set<Student> studentsList ) throws MaxNumberException {
        for( Student s:studentsList ) {
            switch ( s.getColor() ) {
                case RED:
                    redDiningRoom.insertStudent(s);
                    break;
                case BLUE:
                    blueDiningRoom.insertStudent(s);
                    break;
                case YELLOW:
                    yellowDiningRoom.insertStudent(s);
                    break;
                case PINK:
                    pinkDiningRoom.insertStudent(s);
                    break;
                case GREEN:
                    greenDiningRoom.insertStudent(s);
                    break;
            }
        }
    }

    public Student removeStudentFromEntrance( Student chosenStudent ) throws InexistentStudentException{
        try {
            entrance.removeStudent(chosenStudent);
            return chosenStudent;
        }
        catch ( InexistentStudentException e ) {
            // da ricontrollare
            throw e;
        }
    }

    /*
    public Set<Card> showCards() {
        Set<Card> deckCopy;
        return deckCopy;
    }*/

    public void playChosenCard( Card chosenCard ) throws CardNotFoundException{
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
        mastersList.add(m.getColor().ordinal(),m);
    }

    /*public Master removeMaster( )*/

    // bisogna controllare che non ci sia un problema di violazione del rep invariant
    /*public DiningRoom getDiningRoom( Color diningRoomColor ) throws WrongColorException {
        switch ( diningRoomColor ) {
            case RED:
                return redDiningRoom;
            case BLUE:
                return blueDiningRoom;
            case YELLOW:
                return yellowDiningRoom;
            case PINK:
                return pinkDiningRoom;
            case GREEN:
                return greenDiningRoom;
            default:
                throw new WrongColorException("Wrong color from getDiningRoom method");
        }
    }*/

    // bisogna controllare che non ci sia un problema di violazione del rep invariant
    public Entrance getEntrance() {
        return this.entrance;
    }


    //fare getMasters che restituisce una copia della lista dei master presenti
    public ArrayList<Master> getMasters(){
        return mastersList;
    }
}
