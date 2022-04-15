package model.FigureCards;

import model.Bag;
import model.ExpertMatchInterface;

public class Jester extends FigureCardWithStudents{

    private static final int PRICECARD=1;

    protected static boolean alreadyPlayed=false;

    public Jester() throws Exception {
        setPrice(PRICECARD);
        studentsNumOnCard =6;
        setStudentsOnCard(Bag.removeStudents(studentsNumOnCard));
        alreadyPlayed=true;
    }

    public static boolean isAlreadyPlayed() {
        return alreadyPlayed;
    }

    @Override
    public void playCard(ExpertMatchInterface expertMatchInterface) {

    }

    @Override
    public String toString() {
        return "I'm the jester, I've got "+studentsOnCard.size()+" students";
    }
}


