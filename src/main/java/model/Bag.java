//Tonsi
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

    public static Student removeStudent() throws MaxNumberException {
         Optional<Student> student=students.stream().findAny();
         if(student.isPresent()){
             students.remove(student.get());
             return student.get();
         }
         else
             throw new MaxNumberException("Bag limit reached, no more students in the bag...");
    }

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

    public static int getSTUDENTSNUMCOLOR() {
        return STUDENTSNUMCOLOR;
    }

    public static int getStudentsNum(){
        return students.size();
    }

    //it recreate the bag from the start. Used for the tests and to create new matches
    public static void restoreBag(){
        bag=null;
        Bag.instance();
    }
}
