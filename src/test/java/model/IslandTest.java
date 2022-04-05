package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class IslandTest extends TestCase {
    Island island;
    @BeforeEach
    void init(){ island = new Island( false, 0);}

    @Test
    void removeEmptyIslandTowers(){
        ArrayList<Tower> tmp = island.removeTowers();
        assertEquals(tmp.size(), 0);
    }

    @Test
    void addIslandTowersAndRemove(){
        Tower tower = new Tower(TowerColor.GREY,0);
        Tower tower1 = new Tower(TowerColor.GREY,0);
        Tower tower2 = new Tower(TowerColor.GREY,0);
        ArrayList<Tower> tmp = new ArrayList<Tower>(0);
        tmp.add(tower);
        tmp.add(tower1);
        tmp.add(tower2);
        island.addTowers(tmp);
        ArrayList<Tower> tmp1 = island.removeTowers();
        assertEquals(tmp1.size(), 3);
        ArrayList<Tower> tmp2 = island.removeTowers();
        assertEquals(tmp2.size(), 0);
    }

    @Test
    void removeIslandTowers(){
        ArrayList<Tower> tmp = island.removeTowers();
        assertEquals(tmp.size(), 0);
    }
}
