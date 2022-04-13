package model;

import model.exception.NoIslandException;

import java.util.ArrayList;
import java.util.Set;

public interface ExpertMatchInterface {

    Dashboard showCurrentPlayerDashboard();

    void setIslandsStudents(int islandToSet, ArrayList<Student>[] students);

    boolean getIsColorBlocked(Color colorBlocked);

    Set<Student> moveFromBagToFigureCard(int studentsNum);

    void moveMotherNature(int position) throws NoIslandException;

    void setIslandBlocked(int islandPosition);


}
