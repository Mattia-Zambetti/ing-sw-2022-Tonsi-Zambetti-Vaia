package model;

import controller.choice.Choice;
import model.figureCards.FigureCard;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface MatchDataInterface extends Serializable {

    @Override
    String toString();

    Dashboard showCurrentPlayerDashboard();
    Map<String, Dashboard> showAllDashboards();
    List<Integer> getIslandPositions();
    Choice getChoice();

    List<Island> getIslands();

    int getCurrentIsland();

    List<Cloud> getClouds();

    Player showCurrentPlayer();

    String getErrorMessage();

    List<Player> showAllPlayers();

    List<FigureCard> showFigureCardsInGame();

    List<Card> showAllCurrentCards();

    int getPlacePlayerInTheOrder(Player player);
}
