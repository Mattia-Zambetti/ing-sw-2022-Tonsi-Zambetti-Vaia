package model;

import model.exception.InvalidNumberOfTowers;
import model.exception.NoListOfSameColoredTowers;
import model.exception.NoTowerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IslandTest{
    Island island;
    ArrayList<Student>[] students;
    Dashboard dashboard;
    Master master;
    @BeforeEach
    void init(){ island = new Island( false, 0);
        students = new ArrayList[5];
        for (int i = 0; i < students.length; i++){
            students[i] = new ArrayList<Student>(0);
        }
        Student student = new Student(0,Color.RED);
        students[0].add(student);
        student = new Student(0,Color.YELLOW);
        students[4].add(student);
        student = new Student(1,Color.BLUE);
        students[2].add(student);
        student = new Student(2,Color.YELLOW);
        students[4].add(student);
        dashboard = new Dashboard(8,TowerColor.GREY,Wizard.WIZARD1, "Island", 0);
        master = new Master(Color.YELLOW);
        dashboard.insertMaster(master);
        master = new Master(Color.RED);
        dashboard.insertMaster(master);
    }

    @Test
    void removeEmptyIslandTowers() throws InvalidNumberOfTowers {
        //ArrayList<Tower> tmp = island.removeTowers();
        assertThrows(InvalidNumberOfTowers.class, ()->island.removeTowers());
    }

    @Test
    void addIslandTowersAndRemoveAndGetColorAndGet() throws InvalidNumberOfTowers, NoListOfSameColoredTowers, NoTowerException {
        Tower tower = new Tower(TowerColor.GREY,0);
        Tower tower1 = new Tower(TowerColor.GREY,0);
        Tower tower2 = new Tower(TowerColor.GREY,0);
        ArrayList<Tower> tmp = new ArrayList<Tower>(0);
        tmp.add(tower);
        tmp.add(tower1);
        tmp.add(tower2);
        island.addTowers(tmp);
        TowerColor c = island.getTowerColor();
        assertEquals(TowerColor.GREY, c);
        ArrayList<Tower> tmp1 = island.removeTowers();
        assertEquals(tmp1.size(), 3);
        assertThrows(InvalidNumberOfTowers.class, ()-> island.removeTowers());
        tmp.remove(0);
        assertThrows(InvalidNumberOfTowers.class, ()->island.addTowers(tmp));
        tower2 = new Tower(TowerColor.BLACK,0);
        tmp.add(tower2);
        assertThrows(NoListOfSameColoredTowers.class, ()->island.addTowers(tmp));
        tmp.remove(tower2);
        tower2 = new Tower(TowerColor.GREY,0);
        tmp.add(tower2);
        island.addTowers(tmp);
        assertEquals(tmp.size(), island.getTower().size());
        island.addTowers(tmp);
        assertEquals(tmp.size() * 2, island.getTower().size());
        island.removeTowers();
        assertThrows(NoTowerException.class, ()->island.getTower());

    }

    @Test
    void TestSetAndGetStudents(){
        island.setStudents(students);
        int tmp = island.getStudentsNumByColor(Color.YELLOW);
        ArrayList<Student> tmp1[];
        assertEquals(tmp, 2);
        tmp1 = island.getStudents();
        assertEquals(students[4].size(), tmp1[4].size());
    }

    @Test
    void TestGetInfluenceByDashboard() throws InvalidNumberOfTowers, NoListOfSameColoredTowers {
        island.setStudents(students);
        dashboard.insertMaster(master);
        int tmp = island.getInfluenceByDashboard(dashboard);
        assertEquals(3, tmp);
        Master master1 = new Master(Color.BLUE);
        dashboard.insertMaster(master1);
        tmp = island.getInfluenceByDashboard(dashboard);
        assertEquals(4, tmp);
        Tower tower = new Tower(TowerColor.GREY,0);
        Tower tower1 = new Tower(TowerColor.GREY,0);
        Tower tower2 = new Tower(TowerColor.GREY,0);
        ArrayList<Tower> tmpTowers = new ArrayList<Tower>();
        tmpTowers.add(tower);
        tmpTowers.add(tower1);
        tmpTowers.add(tower2);
        island.addTowers(tmpTowers);
        tmp = island.getInfluenceByDashboard(dashboard);
        assertEquals(7, tmp);

    }
    @Test
    void TestAddStudent(){
        island.setStudents(students);
        Student student = new Student(2,Color.YELLOW);
        ArrayList<Student>[] tmp1 = island.getStudents();
        assertEquals(students[4].size(), tmp1[4].size());
        System.out.println(students[4].size());
        island.addStudent(student);
        tmp1 = island.getStudents();
        assertEquals(students[4].size() + 1 , tmp1[4].size());
    }

    @Test
    void TestGetTowerNum() throws InvalidNumberOfTowers, NoListOfSameColoredTowers {
        Tower tower = new Tower(TowerColor.GREY,0);
        Tower tower1 = new Tower(TowerColor.GREY,0);
        Tower tower2 = new Tower(TowerColor.GREY,0);
        ArrayList<Tower> tmp = new ArrayList<Tower>(0);
        tmp.add(tower);
        tmp.add(tower1);
        tmp.add(tower2);
        island.addTowers(tmp);
        assertEquals(3, island.getTowerNum());
        island.removeTowers();
        assertEquals(0, island.getTowerNum());
    }

    @Test
    void TestGetPosition(){
        assertEquals(0,island.getPosition());
    }

    @Test
    void TestGetSetForbidden(){
        assertEquals(false,island.checkForbidden());
        island.setForbidden(false);
        assertEquals(false,island.checkForbidden());
        island.setForbidden(true);
        assertEquals(true,island.checkForbidden());
    }

    @Test
    void TestGetSetMotherNature(){
        assertEquals(false,island.checkIsMotherNature());
        island.setMotherNature(false);
        assertEquals(false,island.checkIsMotherNature());
        island.setMotherNature(true);
        assertEquals(true,island.checkIsMotherNature());
    }

    @Test
    void TestGetTotalStudentsNum(){
        assertEquals(0,island.getTotalStudentsNum());
        island.setStudents(students);
        assertEquals(4,island.getTotalStudentsNum());
        Student student = new Student(6,Color.RED);
        island.addStudent(student);
        assertEquals(5,island.getTotalStudentsNum());
        island.setStudents(students);
        assertEquals(4,island.getTotalStudentsNum());
    }


}
