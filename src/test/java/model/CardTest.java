package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest  {
    Card cardTest;


    @BeforeEach
    void init(){
        cardTest=new Card(1,1,1);
    }

    //it tests getter methods and to String method
    @Test
    void getTest(){
        assertEquals(1,cardTest.getId());
        assertEquals(1,cardTest.getValue());
        assertEquals(1,cardTest.getMovementValue());
        assertEquals("Card "+1+":\n" +
                "value: " + 1 +"\n"+
                "movementValue: " + 1 +"\n",cardTest.toString());
    }

    //it tests how the overrided method equals work
    @Test
    void overridedEqualsTest(){
        Card equalsCard=new Card(1,1,1);
        assertEquals(equalsCard,cardTest);

        Card notSameCard=new Card(1,2,3);
        assertNotSame(notSameCard,cardTest);

        equalsCard=cardTest;
        assertEquals(equalsCard,cardTest);

        Deck deckTest=new Deck(Wizard.WIZARD2);
        assertNotSame(deckTest,cardTest);
    }

    //It tests if it's possible insert into a set two cards with the same id and
    // if it find a Card starting from a copy
    @Test
    void hashCodeTest(){
        HashSet<Card> cardsTestHash=new HashSet<>();
        cardsTestHash.add(new Card(1,2,3));
        cardsTestHash.add(new Card(1,2,3));
        assertEquals(1, cardsTestHash.size());

        cardsTestHash.add(new Card(cardTest));
        assertEquals(2, cardsTestHash.size());

        assertTrue(cardsTestHash.contains(cardTest));

    }

}
