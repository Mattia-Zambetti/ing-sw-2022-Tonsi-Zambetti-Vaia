package model.exception;

import model.*;

public class NoMoreTowerException extends FinishedGameExceptions{

    private TowerColor endedTowerColor;

    public NoMoreTowerException(String errorMessage, TowerColor endedTowerColor){
        super(errorMessage);
        this.endedTowerColor = endedTowerColor;
    }

    @Override
    public void manageException(Match match) {
        match.setWinnerPlayerByTowerColor(endedTowerColor);
        match.notifyEndMatch();
    }
}
