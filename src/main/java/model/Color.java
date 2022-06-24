//Tonsi e Zambo
package model;

import java.io.Serializable;

public enum Color implements Serializable {
    RED, GREEN, BLUE, PINK, YELLOW;

    private boolean isColorBlocked=false;

    /** it returns the dimension of the enum*/
    public static int getDim(){ return YELLOW.ordinal()+1; }

    /**It locks a color by setting true "isColorBlocked"; used by the mushroom collector*/
    public void lockColor(){
        isColorBlocked=true;
    }

    /**It unlocks a color by setting false "isColorBlocked"; used by the mushroom collector*/
    public void unlockColor(){
        isColorBlocked=false;
    }

    /**It returns if the color is blocked; used by the mushroom collector*/
    public boolean isColorBlocked(){
        return isColorBlocked;
    }

}
