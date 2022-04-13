package model.FigureCards;

import model.Bag;
import model.ExpertMatchInterface;

public class Giullare extends FigureCardWithStudents{

    private static final int PRICECARD=1;

    public Giullare() throws Exception {
        setPrice(PRICECARD);
        studentsNumOnCard =6;
        setStudentsOnCard(Bag.removeStudents(studentsNumOnCard));
    }


    @Override
    public void playCard(ExpertMatchInterface expertMatchInterface) {

    }
}


