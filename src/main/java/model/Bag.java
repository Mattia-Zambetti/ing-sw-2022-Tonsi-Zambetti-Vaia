//Tonsi, TESTED
package model;

import model.exception.NoMoreStudentsException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Bag {
    private final static int INITIALSTUDENTS=2;
    private final static int STUDENTSNUMCOLOR =24;
    private static Set<Student> students;
    private static Bag bag;
    private static int counter;

    private Bag(){}

    //it creates the unique object bag, and when it's created, it returns the Bag object
    //to the caller(IT CREATES ALSO THE STUDENTS TO INITIALIZE THE ISLANDS)
    public static Bag initialInstance(){
        students=new HashSet<>();
        counter=0;
        if (bag==null){
            bag= new Bag();
            for(Color c: Color.values()){
                for(int i = 0; i< INITIALSTUDENTS; i++){
                    students.add(new Student(counter, c));
                    counter++;
                }
            }
        }
        return bag;
    }

    //it creates the students into the bag to start the real match(after the initialization)
    public static void createAllStudents() {
        students = new HashSet<>();
        if (students.size()==0){
            for (Color c : Color.values()) {
                for (int i = 0; i < STUDENTSNUMCOLOR; i++) {
                    students.add(new Student(counter, c));
                    counter++;
                }
            }
        }
    }

    public static int getINITIALSTUDENTS() {
        return INITIALSTUDENTS;
    }

    //it allows to remove only a student from the bag, it's not so important,
    //but I leave it because it's confortable
    public static Student removeStudent() throws NoMoreStudentsException {
         Optional<Student> student=students.stream().findAny();
         if(student.isPresent()){
             students.remove(student.get());
             return student.get();
         }
         else
             throw new NoMoreStudentsException("Bag limit reached, no more students in the bag...");
    }

    //it allows to remove "numStudents" students from the bag
    public static Set<Student> removeStudents(int numStudents) throws NoMoreStudentsException {
        Set<Student> studentstmp= new HashSet<>();
        Optional<Student> studentTmp;
        for(int i=0; i<numStudents; i++){
            studentTmp=students.stream().findAny();
            if(studentTmp.isPresent()){
                students.remove(studentTmp.get());
                studentstmp.add(studentTmp.get());
            }
            else
                throw new NoMoreStudentsException("Bag limit reached, now it's empty."+i+" students returned...");
        }
        return studentstmp;
    }

    //it is the getter for the static final variable that contain the students number
    //of every color at the start of a match
    public static int getSTUDENTSNUMCOLOR() {
        return STUDENTSNUMCOLOR;
    }

    //it returns the students number in the bag
    public static int getStudentsNum(){
        return students.size();
    }

    //it recreates the bag from the start. Used for tests and to create new matches
    public static void restoreBag(){
        bag=null;
        Bag.initialInstance();
    }
}
