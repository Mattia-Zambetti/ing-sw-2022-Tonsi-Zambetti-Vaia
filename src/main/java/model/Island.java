package model;

import graphicAssets.CLIgraphicsResources;
import model.exception.*;

import java.io.Serializable;
import java.util.*;

public class Island implements Serializable {
    private boolean isMotherNature, isForbidden; //da stampare
    public static boolean centaurEffect = false; //da stampare
    private int position, numOfTowers; //da stampare
    private TowerColor towerColor; //da stampare
    private ArrayList<Student>[] students; //da stampare
    private ArrayList<Tower> towerList;
    private boolean expectingTowers = false;

    public Island ( boolean motherNature, int islandPosition ) {
        students = new ArrayList[5];
        this.numOfTowers = 0;
        this.isMotherNature = motherNature;
        this.isForbidden = false;
        this.position = islandPosition;
        this.towerColor = null;
        this.towerList = new ArrayList<>(0);
        for (int i = 0; i < students.length; i++)
            students[i] = new ArrayList<Student>(0);
    }

    //return a copy of the towers on this island
    public ArrayList<Tower> getTower() throws NoTowerException {
        if ( towerList.size() == 0 )
            throw new NoTowerException("No tower in this island");
        return new ArrayList<Tower>(towerList);
    }

    //return the total number of students on the island
    public int getTotalStudentsNum() {
        int studentsCount=0;
        for ( int i=0; i<5; i++) {
                studentsCount+= students[i].size();
        }
        return studentsCount;
    }

    //return the total number of students on the island based on the color
    public int getStudentsNumByColor( Color color ) {
        return students[color.ordinal()].size();
    }

    //it calculates the influence of a specific dashboard based on the students on this island
    public int getInfluenceByDashboard ( Dashboard dashboard ) {
        int influence=0;
        List<Master> tmp = (List<Master>) dashboard.getMastersList();

        for ( int i=0; i<tmp.size(); i++) {
            if(!tmp.get(i).getColor().isColorBlocked())
                influence += students[tmp.get(i).getColor().ordinal()].size();
        }

        if(!centaurEffect){
            if (towerList.size() != 0)
                if(towerList.get(0).getColor() == dashboard.getBuddyWithTowers().getTowerColor())
                    influence = influence + towerList.size();
        }
        return influence;
    }

    //removes and returns the towers that were on this island
    public ArrayList<Tower> removeTowers() throws InvalidNumberOfTowers{
        if(towerList.size() != 0){
            ArrayList<Tower> tmpTowers = new ArrayList<Tower>(towerList);
            numOfTowers = towerList.size();
            towerList.clear();
            expectingTowers = true;
            return tmpTowers;
        }
        else throw new InvalidNumberOfTowers("Prima bisogna inserire delle torri");
    }

    //it adds the towers that are given through paramater to the island,and it controls if the number of towers passed is the same as the old number of towers
    public void addTowers (ArrayList<Tower> towers) throws InvalidNumberOfTowers, NoListOfSameColoredTowers{
        final TowerColor tmpColor = towers.get(0).getColor();
        if(!expectingTowers){
            if (towers.stream().allMatch(tower -> tower.getColor().equals(tmpColor))){
                towerList.addAll(towers);
                numOfTowers = towerList.size();
            }
            else throw new NoListOfSameColoredTowers("Towers with different color, expected same color");
        }
        else{
            if(numOfTowers == towers.size()){
                if (towers.stream().allMatch(tower -> tower.getColor().equals(tmpColor))){
                    towerList.addAll(towers);
                    numOfTowers = towerList.size();
                    expectingTowers = false;
                }
                else throw new NoListOfSameColoredTowers("Towers with different color, expected same color");
            }
            else throw new InvalidNumberOfTowers("Wrong number of towers, expected: " + numOfTowers + " towers, but given: "+ towers.size());
        }
    }

    public ArrayList<Student>[] getStudents() {
        ArrayList<Student>[] tmp = students.clone();
        return tmp;
    }

    public void setStudents(ArrayList<Student>[] students) {
        //this.students = new ArrayList[5];
        for (int i = 0; i < 5; i++){
            this.students[i] = new ArrayList<>(students[i]);
        }
    }

    public int getTowerNum(){ return towerList.size(); }

    //it the return the color of the first tower, and if there is no tower it throws an exception
    public TowerColor getTowerColor() throws NoTowerException{
        if(towerList.size() != 0)
            return towerList.get(0).getColor();
        else throw new NoTowerException("No towers on this Island");
    }

    public int getPosition() {
        return position;
    }

    //it adds the student and it put it with the students with the same color
    public void addStudent(Student student){
        students[student.getColor().ordinal()].add(student);
    }

    public void setForbidden(boolean forbidden){
        isForbidden = forbidden;
    }

    public static void setCentaurEffect(boolean centaur){
        centaurEffect = centaur;
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

    @Override
    public String toString() {
        String OutputString =   "\n======================" +
                                "\n       ISLAND " + position + "      ";
        if ( isMotherNature )
            OutputString = OutputString.concat("\n     MOTHER NATURE    ");
        else
            OutputString = OutputString.concat("\n                      ");
        for ( Color c : Color.values() ) {
            OutputString = OutputString.concat("\n  " + CLIgraphicsResources.ColorCLIgraphicsResources.getTextColor(c) + getStudentsNumByColor(c) + " " + c.toString() + " students" + CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR);
        }
        OutputString = OutputString.concat("\n                      ");
        if ( numOfTowers > 0 ) {
            try {
                OutputString = OutputString.concat("\n    " + numOfTowers + " " + getTowerColor().toString() + " TOWERS");
            } catch (NoTowerException e) {
                e.printStackTrace();
            }
        }
        else
            OutputString = OutputString.concat("\n      NO TOWERS       ");
        if ( isForbidden )
            OutputString = OutputString.concat("\n   FORBIDDEN ISLAND   ");
        if ( centaurEffect )
            OutputString = OutputString.concat("\n    CENTAUR EFFECT    ");

        OutputString = OutputString.concat("\n======================");


        return OutputString;
    }
}
