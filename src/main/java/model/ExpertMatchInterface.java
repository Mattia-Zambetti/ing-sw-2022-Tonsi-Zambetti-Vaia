package model;

import model.exception.NoIslandException;

import java.util.ArrayList;

public interface ExpertMatchInterface {

    Dashboard showCurrentPlayerDashboard();

    void setIslandsStudents(int islandToSet, ArrayList<Student>[] students);

    void moveMotherNature(int position) throws NoIslandException;

    void setIslandBlocked(int islandPosition);


}
