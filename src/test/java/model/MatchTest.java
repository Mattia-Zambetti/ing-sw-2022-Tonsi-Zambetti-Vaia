package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchTest extends TestCase {
    Match match;
    static final int PLAYERSNUM=2;

    @BeforeEach void init() {
        match=new Match(PLAYERSNUM, false);
        assertEquals(PLAYERSNUM, match.getPlayersNum());
    }

    //It tests if it's possible to create a Match with the wrong number of players
    @Test
    void TooManyPlayersException(){
        Match tmp;
        tmp=new Match(Match.getMAXPLAYERSNUM()+1,false);
        assertFalse(tmp.hasChanged());
        tmp=new Match(Match.getMINPLAYERSNUM()-1,false);
        assertFalse(tmp.hasChanged());
    }

    //it tests the presence of a wrong choice into
    // the parameters of the moveStudentsFromCloudToEntrance's method
    @Test
    void moveStudentsFromCloudWrongNumber(){
        
        int chosenCloud=PLAYERSNUM+1;
        match.moveStudentsFromCloudToEntrance(chosenCloud);
        assertFalse(match.hasChanged());
        chosenCloud=0;
        match.moveStudentsFromCloudToEntrance(chosenCloud);
        assertFalse(match.hasChanged());
        chosenCloud=PLAYERSNUM;
        match.moveStudentsFromCloudToEntrance(chosenCloud);
        assertTrue(match.hasChanged());
    }


}
