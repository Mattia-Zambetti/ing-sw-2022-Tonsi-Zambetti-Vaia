package model.FigureCards;
import model.Bag;
import model.ExpertMatchInterface;

public class Princess extends FigureCardWithStudents {
    private static final int PRICECARD=2;

    public Princess() throws Exception {
        setPrice(PRICECARD);
        studentsNumOnCard =4;
        setStudentsOnCard(Bag.removeStudents(studentsNumOnCard));
        cardId=7;
    }

    public  void playCard(ExpertMatchInterface expertMatchInterface){

    }

    @Override
    public String toString() {
        return "I'm the princess, I've got "+studentsOnCard.size()+" students";
    }



}