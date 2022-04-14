package model;

public enum TowerColor {
    WHITE, GREY, BLACK;

    private int counter=0;



    public int getCounter() {
        return counter;
    }

    public void counterplus(){
        counter++;
    }

    public void resetCounter(){
        counter=0;
    }
}
