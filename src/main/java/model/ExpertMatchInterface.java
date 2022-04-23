package model;

import model.figureCards.FigureCard;
import model.figureCards.FigureCardWithStudents;

import java.util.ArrayList;

public interface ExpertMatchInterface {

    Dashboard showCurrentPlayerDashboard();

    void setIslandsStudents(int islandToSet, ArrayList<Student>[] students);

    //void moveMotherNature(int position) throws NoIslandException;

    void setPostManValue();

    void setIsKnight();

    void setCentaurEffect(boolean centaurEffect);

    boolean isCentaurEffect();

    void notifyStudentsOnFigureCard(FigureCardWithStudents figureCardWithStudents);

    void notifyIslandFigureCard(FigureCard figureCard);


}
