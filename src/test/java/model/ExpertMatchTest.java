package model;

import junit.framework.TestCase;
import model.FigureCards.FigureCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ExpertMatchTest extends TestCase {
    ExpertMatch match;

    @BeforeEach
    void init(){
        match=new ExpertMatch(3);
    }

    //it shows the type of figure cards created, and if the students in the bag and on
    //the figure cards is correct
    @Test
    void randomCardCreationTest(){
        List<FigureCard> figureCards= match.showFigureCardsInGame();
        for (FigureCard f: figureCards) {
            System.out.println(f.toString());
        }
        System.out.println("There are " +Bag.getStudentsNum()+ " students in the bag. The " +
                "initial number was "+Bag.getSTUDENTSNUMCOLOR()*5);
    }

}
