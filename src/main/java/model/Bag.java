//Tonsi, TESTED
package model;

import model.exception.MaxNumberException;
import model.exception.NoMoreStudentsException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class Bag  implements Serializable {
    private final static int INITIALSTUDENTS=2;
    private final static int STUDENTSNUMCOLOR =24;
    private static Set<Student> studentsInBag;
    private static Bag bag;
    private static int counter;

    private Bag(){}

    //it creates the unique object bag, and when it's created, it returns the Bag object
    //to the caller(IT CREATES ALSO THE STUDENTS TO INITIALIZE THE ISLANDS)
    public static void initialInstance(){
        studentsInBag=new HashSet<>();
        counter=0;
        if (bag==null){
            bag=new Bag();
            for(Color c: Color.values()){
                for(int i = 0; i< INITIALSTUDENTS; i++){
                    studentsInBag.add(new Student(counter, c));
                    counter++;
                }
            }
        }
    }

    //it creates the students into the bag to start the real match(after the initialization)
    public static void createAllStudents() throws MaxNumberException {
        if (studentsInBag.size()==0){
            for (Color c : Color.values()) {
                for (int i = 0; i < STUDENTSNUMCOLOR; i++) {
                    studentsInBag.add(new Student(counter, c));
                    counter++;
                }
            }
        }else throw new MaxNumberException("The bag isn't empty...");
    }

    public static int getINITIALSTUDENTS() {
        return INITIALSTUDENTS;
    }

    //it allows to remove only a student from the bag, it's not so important,
    //but I leave it because it's confortable
    public static Student removeStudent() throws NoMoreStudentsException {
        Optional<Student> student;
        if(studentsInBag.size()!=0)
            student = studentsInBag.stream().skip(new Random().nextInt(studentsInBag.size())).findAny();
        else student=studentsInBag.stream().skip(new Random().nextInt(1)).findAny();

        if(student.isPresent()){
             studentsInBag.remove(student.get());
             return student.get();
         }
         else throw new NoMoreStudentsException("Bag limit reached, no more students in the bag...");
    }

    //it allows to remove "numStudents" students from the bag
    public static Set<Student> removeStudents(int numStudents) throws NoMoreStudentsException {
        Set<Student> studentstmp= new HashSet<>();
        for(int i=0; i<numStudents; i++){
            Optional<Student> studentTmp;
            if(studentsInBag.size()!=0)
                studentTmp = studentsInBag.stream().skip(new Random().nextInt(studentsInBag.size())).findAny();
            else studentTmp=studentsInBag.stream().skip(new Random().nextInt(1)).findAny();

            if(studentTmp.isPresent()){
                studentsInBag.remove(studentTmp.get());
                studentstmp.add(studentTmp.get());
            }
            else
                throw new NoMoreStudentsException("Bag limit reached, now it's empty."+i+" students returned...");
        }
        return new HashSet<>(studentstmp);
    }

    //it is the getter for the static final variable that contain the students number
    //of every color at the start of a match
    public static int getSTUDENTSNUMCOLOR() {
        return STUDENTSNUMCOLOR;
    }

    //it returns the students number in the bag
    public static int getStudentsNum(){
        return studentsInBag.size();
    }

    //it recreates the bag from the start. Used for tests and to create new matches
    public static void restoreBag(){
        bag=null;
        Bag.initialInstance();
    }
    public static void addStudents(Set<Student> students){
        studentsInBag.addAll(students);
    }
}
