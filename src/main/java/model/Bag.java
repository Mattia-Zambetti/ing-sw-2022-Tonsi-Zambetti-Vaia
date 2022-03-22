package model;

import model.exception.MaxNumberException;

import java.util.Optional;
import java.util.Set;

public class Bag {
    private final static int NUMSTUDENTSCOLOR=26;
    private Set<Student> students;
    private static Bag bag;

    private Bag(){}


    public Bag instance(){
        if(bag==null){
            bag= new Bag();
            for(Color c: Color.values()){
                for(int i=0; i<NUMSTUDENTSCOLOR; i++)
                    students.add(new Student(c));
            }
        }
        return bag;
    }

    public Bag instanceWithCounterStudents(){
        int numStudent=0;
        if (bag==null){
            bag= new Bag();
            for(Color c: Color.values()){
                for(int i=0; i<NUMSTUDENTSCOLOR; i++){
                    students.add(new Student(c,numStudent));
                    numStudent++;
                }
            }
        }
        return bag;
    }

    public Student removeStudent() throws MaxNumberException {
         Optional<Student> student=students.stream().findAny();

         if(student.isPresent()){
             students.remove(student.get());
             return student.get();
         }
         else
             throw new MaxNumberException("Studenti nel sacchetto finiti");
    }
}
