package model;

import model.exception.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Island {
    private boolean isTower, isMotherNature, isForbidden, vaiaEffect;
    private int numberOfTowers, position;
    private TowerColor towerColor;
    private ArrayList<Student>[] students;
    private ArrayList<Tower> towerList;

    public Island ( boolean motherNature, int islandPosition ) {
        students = new ArrayList[5];
        this.isTower = false;
        this.isMotherNature = motherNature;
        this.isForbidden = false;
        this.vaiaEffect = false;
        this.numberOfTowers = 0;
        this.position = islandPosition;
        this.towerColor = null;
        this.towerList = new ArrayList<>(0);
        for (int i = 0; i < students.length; i++)
            students[i] = new ArrayList<Student>(0);
    }

    public TowerColor getTowerColor() throws NoTowerException {
        if ( numberOfTowers == 0 )
            throw new NoTowerException("No tower in this island");
        return this.towerColor;
    }

    private int getTotalStudentsNum() {
        int studentsCount=0;
        for ( int i=0; i<5; i++) {
            for ( int j=0; j<students[i].size(); j++) {
                studentsCount++;
            }
        }
        return studentsCount;
    }

    private int getStudentsNumByColor( Color color ) {
        return students[color.ordinal()].size();
    }

    public int getInfluenceByDashboard ( Dashboard dashboard ) {
        int influence=0;
        ArrayList<Master> tmp = (ArrayList<Master>)dashboard.getMastersList();

        for ( int i=0; i<tmp.size(); i++) {
            influence += students[tmp.get(i).getColor().ordinal()].size();
        }

        //Bisognerà aggiungere il controllo su modalità esperto e su VaiaEffect, o forse fare due metodi diversi, uno in modalità esperto e un oin modalità normale
        if (isTower)
            if(towerColor== dashboard.getTowerColor())
                influence++;
        return influence;

    }

    public void changeTowerColor(TowerColor color){
        if(!isTower)
            addTower(color);
        else {
            towerColor = null;
            addTower(color);
        }

    }

    private void addTower(TowerColor color){
        towerColor = color;
        isTower = true;
    }

    public ArrayList<Student>[] getStudents() {
        ArrayList<Student>[] tmp = students.clone();
        return tmp;
    }

    public void setStudents(ArrayList<Student>[] students) {
        this.students = students;
    }

    public int getPosition() {
        return position;
    }

    public void addStudent(Student student){
        students[student.getColor().ordinal()].add(student);
    }

    public void setForbidden(boolean forbidden){
        isForbidden = forbidden;
    }

    public void addTowerNumber() {
        this.numberOfTowers++;
    }

    public int getNumberOfTowers() {
        return numberOfTowers;
    }

    public boolean checkForbidden(){
        return isForbidden;
    }

    public void setMotherNature(boolean motherNature){
        isMotherNature = motherNature;
    }

    public boolean checkIsMotherNature(){
        return isMotherNature;
    }
}
