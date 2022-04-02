package model;

import model.exception.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Island {
    private boolean isTower, isMotherNature, isForbidden, vaiaEffect;
    private int numberOfTowers, position;
    private static int totalNumIslands = 12;
    private TowerColor towerColor;
    private ArrayList<Student>[] students;
    private static List<Integer> islandPositions = new ArrayList<>();

    public Island ( boolean motherNature, int islandPosition ) {
        this.isTower = false;
        this.isMotherNature = motherNature;
        this.isForbidden = false;
        this.vaiaEffect = false;
        this.numberOfTowers = 0;
        this.position = islandPosition;
        this.towerColor = null;
        students[5] = new ArrayList<Student>(0);
        islandPositions.add(islandPosition);
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


    private void mergeIsland(Island island){
        for (int i = 0; i< 5;i++)
        {
                this.students[i].addAll(island.students[i]);
        }
        islandPositions.remove(island.position);
        totalNumIslands--;
        this.numberOfTowers++;
    }

    public void checkNearbyIslands(Island island){ //metodo che chiama il merge se la isola in parametro ha lo stesso colore della torre di this, match chiamerà questo metodo in base a "nextisland"e "previousIsland"
        if(island.towerColor == this.towerColor)
            mergeIsland(island);
    }

    //Da sistemare
    public int nextIsland(){ //metodo che ritorna l'indice della posizione della prosiima isola
        int tmp = islandPositions.indexOf(position);
        if(tmp + 1 == islandPositions.size())
            return islandPositions.get(0);
        return islandPositions.get(tmp + 1);
    }

    //Da sistemare
    public int previousIsland(){ //metodo che ritorna l'indice della posizione della isola precedente
        int tmp = islandPositions.indexOf(position);
        if(tmp - 1 == - 1)
            return islandPositions.get(islandPositions.size() - 1);
        return islandPositions.get(tmp - 1);
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
//prova


    }
