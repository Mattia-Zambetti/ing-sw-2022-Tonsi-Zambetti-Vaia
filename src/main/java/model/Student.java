// Zambo
package model;

import graphicAssets.CLIgraphicsResources;

import java.io.Serializable;

public final class Student implements Serializable {
    private final Color color;
    private final int ID;

    public Student( int studentID, Color studentColor){
        this.color = studentColor;
        this.ID = studentID;
    }

    public Student( Student s ){
        this.color = s.color;
        this.ID = s.ID;
    }

    public Color getColor() {
        return this.color;
    }

    public int getID() {
        return ID;
    }

    //UPDATE: you can't insert students with same id in the same set, simply the second isn't added
    //UPDATE: added specific Exception in both DR and Entrance, need to check for other Classes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student s)) return false;
        return s.color.ordinal() == this.color.ordinal() && ( s.ID == this.ID );
    }


    @Override
    public int hashCode() {
        return ID;
    }

    @Override
    public String toString(){
        return CLIgraphicsResources.ColorCLIgraphicsResources.getTextColor(color) + color.toString() + " student" + CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR;
    }



}
