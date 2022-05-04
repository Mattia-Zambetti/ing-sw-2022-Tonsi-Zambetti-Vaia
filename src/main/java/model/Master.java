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

}
