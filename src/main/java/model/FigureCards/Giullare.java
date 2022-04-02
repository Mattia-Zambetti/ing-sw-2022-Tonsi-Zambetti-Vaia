package model.FigureCards;

import model.Bag;
import model.Student;
import model.exception.MaxNumberException;
import model.exception.WrongNumStudentsException;

import java.util.HashSet;
import java.util.Set;

public class Giullare extends FigureCardWithStudents{

    public Giullare(int studentsNum, int price, Set<Student> students) throws WrongNumStudentsException {
        super(studentsNum, price, students);
    }

    public  void playCard() throws MaxNumberException {
        Set<Student> chosenStudents=new HashSet<>();
        System.out.println("scegli 3 studenti");
        //TODO scanner

        getStudents().removeAll(chosenStudents);
        getStudents().addAll(Bag.instance().removeStudents(getStudentsNum()));
        notifyObservers(chosenStudents);
    }

}


