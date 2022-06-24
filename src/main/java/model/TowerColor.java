package model;

import java.io.Serializable;

public enum TowerColor  implements Serializable {
    WHITE, BLACK, GREY;
    private int counter=0;

    /**It returns the counter of this tower color*/
    public int getCounter() {
        return counter;
    }

    /**it adds 1 to the counter of this tower color*/
    public void counterplus(){
        this.counter++;
    }

    /**it resets the counter to 0 of this tower color*/
    public void resetCounter(){
        counter=0;
    }
}
