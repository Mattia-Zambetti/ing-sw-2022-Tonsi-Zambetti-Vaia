package model;

import model.exception.*;

import java.util.*;

public class Island {
    private boolean isMotherNature, isForbidden, vaiaEffect;
    private int position;
    private TowerColor towerColor;
    private ArrayList<Student>[] students;
    private ArrayList<Tower> towerList;

    public Island ( boolean motherNature, int islandPosition ) {
        students = new ArrayList[5];
        this.isMotherNature = motherNature;
        this.isForbidden = false;
        this.vaiaEffect = false;
        this.position = islandPosition;
        this.towerColor = null;
        this.towerList = new ArrayList<>(0);
        for (int i = 0; i < students.length; i++)
            students[i] = new ArrayList<Student>(0);
    }

    public ArrayList<Tower> getTower() throws NoTowerException {
        if ( towerList.size() == 0 )
            throw new NoTowerException("No tower in this island");
        return this.towerList;
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
        if (towerList.size() != 0)
            if(towerList.get(0).getColor() == dashboard.getTowerColor())
                influence++;
        return influence;
    }

    public ArrayList<Tower> removeTowers(){
        ArrayList<Tower> tmpTowers = new ArrayList<Tower>(towerList);
        towerList.clear();
        return tmpTowers;
    }

    public void addTowers(ArrayList<Tower> towers){
        towerList.addAll(towers);
    }

    public ArrayList<Student>[] getStudents() {
        ArrayList<Student>[] tmp = students.clone();
        return tmp;
    }

    public void setStudents(ArrayList<Student>[] students) {
        this.students = students;
    }

    public int getTowerNum(){ return towerList.size(); }

    public TowerColor getTowerColor(){
        if(towerList.size() != 0)
            return towerList.get(0).getColor();
        return null;
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
