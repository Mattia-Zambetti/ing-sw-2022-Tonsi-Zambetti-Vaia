package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest extends TestCase {
    Player playerTest;

    @BeforeEach
    void init(){
        playerTest= new Player("Vaia",0);
    }

    //It's a test on the buddy's management and in general on every getter in the Player class
    @Test
    void setBuddyAndPlayerNumberTest(){
        Player buddyTest=new Player("Tonsi",1 );

        assertEquals(0,playerTest.getPlayerNumber());
        assertEquals(1,buddyTest.getPlayerNumber());

        playerTest.setBuddy(buddyTest);
        assertEquals(buddyTest.getNickname(), playerTest.getNicknameBuddy());
        assertEquals(buddyTest.getPlayerNumber(), playerTest.getPlayerNumberBuddy());
    }
}
