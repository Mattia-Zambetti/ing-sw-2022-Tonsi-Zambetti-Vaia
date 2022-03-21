// Zambo
package model;

public class Dashboard {
    private Entrance entrance;
    private DiningRoom redDiningRoom, blueDiningRoom, yellowDiningRoom, pinkDiningRoom, greenDiningRoom;
    private int towersNumber;
    private final TowerColor towerColor;
    public Dashboard ( int numberOfTowers, TowerColor colorOfTower ) {
        entrance = new Entrance();
        redDiningRoom = new DiningRoom(Color.RED);
        blueDiningRoom = new DiningRoom(Color.BLUE);
        yellowDiningRoom = new DiningRoom(Color.YELLOW);
        pinkDiningRoom = new DiningRoom(Color.PINK);
        greenDiningRoom = new DiningRoom(Color.GREEN);
        this.towersNumber = numberOfTowers;
        this.towerColor = colorOfTower;
    }

    public int getTowersNum() {
        return this.towersNumber;
    }

    public TowerColor getTowerColor() {
        return this.towerColor;
    }

    public void removeTowers ( int numberOfTower ) throws NegativeNumberOfTowerException {
        int newTowersNumber = this.towersNumber - numberOfTower;
        if ( newTowersNumber < 0 )
            throw new NegativeNumberOfTowerException("Insufficient number of tower");
        else
            this.towersNumber = newTowersNumber;
    }

    // bisogna controllare che non ci sia un problema di violazione del rep invariant
    public DiningRoom getDiningRoom( Color diningRoomColor ) throws WrongColorException {
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
    }

    // bisogna controllare che non ci sia un problema di violazione del rep invariant
    public Entrance getEntrance() {
        return this.entrance;
    }
}
