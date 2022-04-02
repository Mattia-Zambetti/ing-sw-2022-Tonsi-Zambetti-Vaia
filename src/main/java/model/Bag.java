//Tonsi
package model;

import model.exception.MaxNumberException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Bag {
    private final static int NUMSTUDENTSCOLOR=26;
    private static Set<Student> students;
    private static Bag bag;

    private Bag(){}

    public static Bag instance(){
        int numStudent=0;
        if (bag==null){
            bag= new Bag();
            for(Color c: Color.values()){
                for(int i=0; i<NUMSTUDENTSCOLOR; i++){
                    students.add(new Student(numStudent, c));
                    numStudent++;
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
             throw new MaxNumberException("Studenti nel sacchetto finiti");
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
                throw new MaxNumberException("Studenti nel sacchetto finiti");
        }
        return studentstmp;
    }
}
