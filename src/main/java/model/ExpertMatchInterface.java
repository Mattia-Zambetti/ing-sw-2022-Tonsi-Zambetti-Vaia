package model;

import model.figureCards.FigureCard;

import java.io.Serializable;

public interface ExpertMatchInterface extends Serializable {

    void setPostManValue();

    void setIsFarmer();

    void setIsKnight();

    void setCentaurEffect(boolean centaurEffect);

    boolean isCentaurEffect();

    //void notifyFigureCard(FigureCardWithStudents figureCardWithStudents);

    void notifyFigureCard(FigureCard figureCard);



}
