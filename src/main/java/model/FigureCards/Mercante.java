package model.FigureCards;
import model.Bag;
import model.MatchInterface;
import model.Student;
import model.exception.NoMoreStudentsException;

import java.util.List;

public class Mercante extends FigureCard {
    List<Student> studenti;
    Bag bag;

    public  void playCard(MatchInterface matchInterface){
        
    }
    public void add3Students() throws NoMoreStudentsException {
        for (int i=3; i == 0; i--)
            studenti.add(bag.removeStudent());
    }
}