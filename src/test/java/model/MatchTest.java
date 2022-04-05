package model;

import junit.framework.TestCase;
import model.exception.AlreadyFilledCloudException;
import model.exception.MaxNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchTest extends TestCase {
    Match match;
    static final int PLAYERSNUM=2;

    @BeforeEach void init() {
        match=new Match(PLAYERSNUM, false);
        assertEquals(PLAYERSNUM, match.getPlayersNum());
        match.addPlayer("Vaia", "BLACK", "WIZARD1");


    }

    //It tests if it's possible to create a Match with the wrong number of players
    @Test
    void TooManyPlayersException() {
        Match tmp1;
        tmp1 = new Match(Match.getMAXPLAYERSNUM() + 1, false);
        tmp1=new Match(Match.getMINPLAYERSNUM() - 1, false);
    }


    //it tests the presence of a wrong choice (or not) into
    // the parameters of the moveStudentsFromCloudToEntrance's method
    @Test
    void moveStudentsFromCloudParamWrongAndCorrect(){
        Match tmp=new Match(PLAYERSNUM, false);
        tmp.addPlayer("Vaia", "BLACK", "WIZARD1");
        Bag.restoreBag();
        System.out.println(Bag.size());

        int chosenCloud=PLAYERSNUM+1;
        match.moveStudentsFromCloudToEntrance(chosenCloud);
        chosenCloud=0;
        match.moveStudentsFromCloudToEntrance(chosenCloud);
        chosenCloud=PLAYERSNUM;
        match.moveStudentsFromCloudToEntrance(chosenCloud);
        //TODO missing assert
    }

    //It tests if the method refillClouds() correctly by refilling without students
    // (and with,that is a possible error) on the clouds
    @Test
    void refillCloudsTest() throws MaxNumberException, AlreadyFilledCloudException {
        System.out.println(match.toStringStudentsOnCloud());
        for(int i=0; i<PLAYERSNUM; i++){
            match.moveStudentsFromCloudToEntrance(i+1);
            System.out.println(match.toStringStudentsOnCloud());
        }
        assertEquals("",match.toStringStudentsOnCloud());
        match.refillClouds();
        System.out.println(match.toStringStudentsOnCloud());

        AlreadyFilledCloudException e=assertThrows(AlreadyFilledCloudException.class,()->match.refillClouds());
        System.out.println(match.toStringStudentsOnCloud());
        assertEquals("there's a cloud that is already filled",e.getMessage());

    }




}
