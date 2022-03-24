// Zambo
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

    @Override
    public boolean equals(Object o) {
        boolean equals = false;
        if (o instanceof Student) {
            Student s = (Student)o;
            if ( ( s.color == this.color ) && ( s.ID == this.ID ) )
                equals = true;
        }
        return equals;
    }

}
