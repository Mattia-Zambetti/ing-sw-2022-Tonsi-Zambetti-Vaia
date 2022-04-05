package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
public class MatchTest extends TestCase {
    Match match;
    static final int PLAYERSNUM=2;

    @BeforeEach void init() {
        match=new Match(PLAYERSNUM, false);
        assertEquals(PLAYERSNUM, match.getPlayersNum());
        match.addPlayer("Vaia", "BLACK", "WIZARD1");
    }

    //It tests if it's possible to create a Match with the wrong number of players
    /*@Test
    void TooManyPlayersException(){
        Match tmp;
        tmp=new Match(Match.getMAXPLAYERSNUM()+1,false);
        assertFalse(tmp.hasChanged());
        tmp=new Match(Match.getMINPLAYERSNUM()-1,false);
        assertFalse(tmp.hasChanged());
    }*/

    //it tests the presence of a wrong choice into
    // the parameters of the moveStudentsFromCloudToEntrance's method
    /*@Test
    void moveStudentsFromCloudWrongNumber(){
        Match tmp=new Match(PLAYERSNUM, false);
        tmp.addPlayer("Vaia", "BLACK", "WIZARD1");

        final int chosenCloud=PLAYERSNUM+1;
        assertThrows(MaxNumberException.class,()->match.moveStudentsFromCloudToEntrance(chosenCloud));


        match.moveStudentsFromCloudToEntrance(chosenCloud);
        match.moveStudentsFromCloudToEntrance(chosenCloud);
        assertNotSame(match, tmp);
    }*/


}
