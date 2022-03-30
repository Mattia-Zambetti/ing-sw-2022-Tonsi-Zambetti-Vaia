package model.FigureCards;
import model.Bag;
import model.Student;
import model.exception.MaxNumberException;

import java.util.*;

public class Mercante extends FigureCard {
    List<Student> studenti;
    Bag bag;

    public  void playCard(){
        
    }
    public void add3Students() throws MaxNumberException {
        for (int i=3; i == 0; i--)
            studenti.add(bag.removeStudent());
    }
}