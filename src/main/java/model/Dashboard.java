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

    public Set<Student> showEntrance () {
        return entrance.getStudents();
    }

    public void moveToEntrance( Set<Student> studentsList ) throws MaxNumberException {
        entrance.insertStudents( studentsList );
    }

    public void moveToDR( Set<Student> studentsList ) {
        try{
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
        catch ( MaxNumberException e ) {
            System.out.println(e.toString());
        }
    }

    public Student removeStudentFromEntrance( Student chosenStudent ){
        try {
            entrance.removeStudent(chosenStudent);
            return chosenStudent;
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

}
