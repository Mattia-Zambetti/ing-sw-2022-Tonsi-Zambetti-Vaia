//Tonsi
package model;

public class Master {
    private boolean isFree;
    private Color color;
    private Player owner;

    public Master(Color color){
        isFree= true;
        this.color=color;
        owner=null;
    }

    public boolean getIfFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree){
        this.isFree=isFree;
    }

    public Color getColor(){
        return color;
    }

    public Player getOwner(){
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
