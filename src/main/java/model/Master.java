//Tonsi
package model;

import java.io.Serializable;

public class Master implements Serializable {
    private final Color color;

    public Master(Color color){
        this.color=color;
    }

    public Color getColor(){
        return color;
    }

    @Override
    public boolean equals(Object o){
        if ( o instanceof Master m) {
            return m.getColor().equals(this.color);
        }
        return false;
    }

}
