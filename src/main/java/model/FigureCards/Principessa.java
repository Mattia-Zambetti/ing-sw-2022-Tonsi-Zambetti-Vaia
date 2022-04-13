package model.FigureCards;
import model.Bag;
import model.ExpertMatchInterface;

public class Principessa extends FigureCardWithStudents {
    private static final int PRICECARD=2;

    public Principessa() throws Exception {
        setPrice(PRICECARD);
        studentsNumOnCard =4;
        setStudentsOnCard(Bag.removeStudents(studentsNumOnCard));
    }

    public  void playCard(ExpertMatchInterface expertMatchInterface){

    }
}