package model;

import model.exception.*;

import java.util.ArrayList;

public class Island {
    private boolean isTower, isMotherNature, isForbidden, vaiaEffect;
    private int numberOfTowers, position;
    private TowerColor towerColor;
    private ArrayList<Student>[] students;

    public Island ( boolean motherNature, int islandPosition ) {
        this.isTower = false;
        this.isMotherNature = motherNature;
        this.isForbidden = false;
        this.vaiaEffect = false;
        this.numberOfTowers = 0;
        this.position = islandPosition;
        this.towerColor = null;
        students[5] = new ArrayList<Student>(0);
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
        ArrayList<Master> tmp = dashboard.getMasters();

        for ( int i=0; i<tmp.size(); i++) {
            influence += students[tmp.get(i).getColor().ordinal()].size();
        }

        //Bisognerà aggiungere il controllo su modalità esperto e su VaiaEffect, o forse fare due metodi diversi, uno in modalità esperto e un oin modalità normale
        if (isTower)
            if(towerColor== dashboard.getTowerColor())
                influence++;
        return influence;

    }







}
