package model;

public final class Student {
    private Color color;
    private int ID;

    public Student( int studentID, Color studentColor){
        this.color = studentColor;
        this.ID = studentID;
    }

    public Color getColor() {
        return this.color;
    }

}
