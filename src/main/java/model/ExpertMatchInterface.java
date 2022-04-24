package model;

import model.figureCards.FigureCard;
import model.figureCards.FigureCardWithStudents;

public interface ExpertMatchInterface {

    void setPostManValue();

    void setIsKnight();

    void setCentaurEffect(boolean centaurEffect);

    boolean isCentaurEffect();

    void notifyStudentsOnFigureCard(FigureCardWithStudents figureCardWithStudents);

    void notifyIslandFigureCard(FigureCard figureCard);


}
