package model;

public enum Wizard {
    WIZARD1, WIZARD2, WIZARD3, WIZARD4;

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
