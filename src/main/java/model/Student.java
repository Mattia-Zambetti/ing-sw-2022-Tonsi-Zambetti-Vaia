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

    @Override
    public boolean equals(Object o) {
        boolean equalsResult = false;
        if (o instanceof Student) {
            Student s = (Student)o;
            if ( ( s.color.ordinal() == this.color.ordinal() ) && ( s.ID == this.ID ) )
                equalsResult = true;
        }
        return equalsResult;
    }

    @Override
    public String toString(){
        return color.toString()+" student";
    }



}
