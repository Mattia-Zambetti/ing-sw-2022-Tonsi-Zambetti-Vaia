package model.FigureCards;
import model.Bag;
import model.ExpertMatchInterface;

public class Mercante extends FigureCardWithStudents {

    private static final int PRICECARD=1;

    protected static boolean alreadyPlayed=false;

    public Mercante() throws Exception {
        setPrice(PRICECARD);
        studentsNumOnCard =4;
        setStudentsOnCard(Bag.removeStudents(studentsNumOnCard));
        alreadyPlayed=true;
    }

    public static boolean isAlreadyPlayed() {
        return alreadyPlayed;
    }

    public  void playCard(ExpertMatchInterface expertMatchInterface){
        
    }

    @Override
    public String toString() {
        return "I'm the merchant, I've got "+studentsOnCard.size()+" students";
    }
}