package model.FigureCards;
import model.Bag;
import model.ExpertMatchInterface;

public class Mercante extends FigureCardWithStudents {

    private static final int PRICECARD=1;

    public Mercante() throws Exception {
        setPrice(PRICECARD);
        studentsNumOnCard =4;
        setStudentsOnCard(Bag.removeStudents(studentsNumOnCard));
    }

    public  void playCard(ExpertMatchInterface expertMatchInterface){
        
    }
}