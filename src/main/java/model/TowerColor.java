package model;

import java.io.Serializable;

public enum TowerColor  implements Serializable {
    WHITE, BLACK, GREY;

    private int counter=0;




    public int getCounter() {
        return counter;
    }

    public void counterplus(){
        this.counter++;
    }

    public void resetCounter(){
        counter=0;
    }
}
