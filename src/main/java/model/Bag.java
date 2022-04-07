//Tonsi, TESTED
package model;

import model.exception.MaxNumberException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Bag {
    private final static int STUDENTSNUMCOLOR =26;
    private static Set<Student> students;
    private static Bag bag;

    private Bag(){}

    //it creates the unique object bag, and when it's created, it returns the Bag object
    //to the caller
    public static Bag instance(){
        students=new HashSet<>();
        if (bag==null){
            bag= new Bag();
            for(Color c: Color.values()){
                for(int i = 0; i< STUDENTSNUMCOLOR; i++){
                    students.add(new Student(i, c));
                }
            }
        }
        return bag;
    }

    //it allows to remove only a student from the bag, it's not so important,
    //but I leave it because it's confortable
    public static Student removeStudent() throws MaxNumberException {
         Optional<Student> student=students.stream().findAny();
         if(student.isPresent()){
             students.remove(student.get());
             return student.get();
         }
         else
             throw new MaxNumberException("Bag limit reached, no more students in the bag...");
    }

    //it allows to remove "numStudents" students from the bag
    public static Set<Student> removeStudents(int numStudents) throws MaxNumberException {
        Set<Student> studentstmp= new HashSet<>();
        Optional<Student> studentTmp;
        for(int i=0; i<numStudents; i++){
            studentTmp=students.stream().findAny();
            if(studentTmp.isPresent()){
                students.remove(studentTmp.get());
                studentstmp.add(studentTmp.get());
            }
            else
                throw new MaxNumberException("Bag limit reached, now it's empty."+i+" students returned...");
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
        Bag.instance();
    }
}
