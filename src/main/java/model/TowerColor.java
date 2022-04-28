package model;

public enum TowerColor {
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
