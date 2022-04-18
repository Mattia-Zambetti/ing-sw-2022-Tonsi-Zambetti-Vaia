package model;

import model.exception.CardNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    Deck deck;

    @BeforeEach
    void init(){
        deck=new Deck(Wizard.WIZARD1);
    }

    //it tests the creation of a deck copy with a constructor, and it tests also if the overrided
    //equals method works
    @Test
    void cloneTest() throws CardNotFoundException {
        Deck tmpTest=new Deck(deck);
        assertTrue(deck.getCards().containsAll(tmpTest.getCards()) &&
                tmpTest.getCards().containsAll(deck.getCards()));
        assertEquals(deck, tmpTest);

        Deck tmpTest2=new Deck(Wizard.WIZARD2);
        assertNotSame(tmpTest2,tmpTest);

        tmpTest2=new Deck(tmpTest);
        assertEquals(tmpTest, tmpTest2);

        tmpTest=tmpTest2;
        assertEquals(tmpTest,tmpTest2);
    }

    //it tests the playCard() method, with and without exception
    @Test
    void playCardTest() throws CardNotFoundException {
        Card cTestPresence=new Card(2,1,2);

        assertTrue(deck.getCards().contains(cTestPresence));
        deck.playCard(cTestPresence);
        assertEquals(cTestPresence, deck.getCurrentCard());
        assertFalse(deck.getCards().contains(deck.getCurrentCard()));

        final Card cTestAbsent=new Card(3,4,160);
        assertThrows(CardNotFoundException.class,()->deck.playCard(cTestAbsent));
    }

    //it tests that, at the start of the match, the current card is null, and it tests
    //also if the current card is correctly set
    @Test
    void getCurrentPlayerTestNullNotNull() throws CardNotFoundException {
        assertEquals(new Card(0,0,0),deck.getCurrentCard());
        Card cTestPresence=new Card(2,1,2);
        deck.playCard(cTestPresence);
        assertNotNull(deck.getCurrentCard());
        assertEquals(cTestPresence,deck.getCurrentCard());
    }

    //It tests if toString work correctly by returning a string on the screen
    @Test
    void showCardsScreenTest(){
        System.out.println(deck.toString());
    }


}
