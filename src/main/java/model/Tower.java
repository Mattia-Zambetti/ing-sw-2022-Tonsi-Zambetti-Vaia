package model;

public class Tower {
    private TowerColor color;
    private int id;

    public Tower(TowerColor color, int id){
        this.color = color;
        this.id = id;
    }

    public Tower(Tower t){
        this.color = t.color;
        this.id = t.id;
    }

    @Override
    public boolean equals(Object o) {
        boolean equalsResult = false;
        if (o instanceof Tower) {
            Tower tower = (Tower)o;
            if ( ( tower.color == this.color ) && ( tower.id == this.id ) )
                equalsResult = true;
        }
        return equalsResult;
    }
}
