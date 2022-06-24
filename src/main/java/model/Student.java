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

    /**It clones the student s in this student*/
    public Student( Student s ){
        this.color = s.color;
        this.ID = s.ID;
    }

    /**It returns the color of this student*/
    public Color getColor() {
        return this.color;
    }

    /**It returns the id of this student*/
    public int getID() {
        return ID;
    }

    /** It returns true only if the Object o is a student and if the Student o
    *has the same color and the same id of this student */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student s)) return false;
        return s.color.ordinal() == this.color.ordinal() && ( s.ID == this.ID );
    }


    /**It allows to recognize the students in a set with their id*/
    @Override
    public int hashCode() {
        return ID;
    }

    /**It returns a string colored with the right color*/
    @Override
    public String toString(){
        return CLIgraphicsResources.ColorCLIgraphicsResources.getTextColor(color) + color.toString() + " student" + CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR;
    }

}
