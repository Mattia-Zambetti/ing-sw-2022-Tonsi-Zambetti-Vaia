package model;

import java.io.Serializable;

public class Tower  implements Serializable {
    private final TowerColor color;
    private final int id;

    public Tower(TowerColor color, int id){
        this.color = color;
        this.id = id;
    }

    public Tower(Tower t){
        this.color = t.color;
        this.id = t.id;
    }

    public TowerColor getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        boolean equalsResult = false;
        if (o instanceof Tower tower) {
            if ( ( tower.color == this.color ) && ( tower.id == this.id ) )
                equalsResult = true;
        }
        return equalsResult;
    }
}
