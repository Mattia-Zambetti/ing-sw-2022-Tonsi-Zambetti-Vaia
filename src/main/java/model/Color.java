//Tonsi e Zambo
package model;

public enum Color{
    RED, GREEN, BLUE, PINK, YELLOW;

    private boolean isColorBlocked=false;

    //AGGIUNTA DA ZAMBO PER OTTENERE LA DIMENSIONE DELL'ENUM (inutile nel nostro caso ma utile in caso di ampliamento dell'enum se venissero aggiunte altre istanze)
    public static int getDim(){ return YELLOW.ordinal()+1; }

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
