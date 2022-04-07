//Tonsi
package model;

public class Master {
    private boolean isFree;
    private final Color color;

    public Master(Color color){
        isFree= true;
        this.color=color;
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

}
