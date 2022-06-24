package model;

import java.io.Serializable;

public class Master implements Serializable {
    private final Color color;

    public Master(Color color){
        this.color=color;
    }

    /**it returns the color of this master*/
    public Color getColor(){
        return color;
    }

    /**It returns true only if the Object o is a master and then the Master o has the same color of this Master*/
    @Override
    public boolean equals(Object o){
        if ( o instanceof Master m) {
            return m.getColor().equals(this.color);
        }
        return false;
    }

}
