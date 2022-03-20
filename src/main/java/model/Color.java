//Tonsi
package model;

public enum Color{
    RED, GREEN, BLUE, PINK, YELLOW;
    private boolean isColorBlocked=false;

    public void lockColor(){
        isColorBlocked=true;
    }

    public void unlockColor(){
        isColorBlocked=false;
    }


    public boolean isColorBlocked(){
        return isColorBlocked;
    }
}
