// Zambo
package model;

public final class Student{
    private Color color;
    private int ID;

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

    //UPDATE:you can't insert students with same id in the same set
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student)o;
        return s.color.ordinal() == this.color.ordinal() && ( s.ID == this.ID );
    }

    @Override
    public int hashCode() {
        return ID;
    }

    @Override
    public String toString(){
        return color.toString()+" student";
    }



}
