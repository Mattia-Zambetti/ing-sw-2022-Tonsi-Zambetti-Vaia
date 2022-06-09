package model;

import controller.choice.Choice;
import model.figureCards.FigureCard;

import java.io.Serializable;
import java.util.List;

public interface MatchDataInterface extends Serializable {

    @Override
    String toString();

    Dashboard showCurrentPlayerDashboard();
    List<Integer> getIslandPositions();
    Choice getChoice();

    Player showCurrentPlayer();

    String getErrorMessage();

    List<Player> showAllPlayers();

    List<FigureCard> showFigureCardsInGame();

    List<Card> showAllCurrentCards();

    int getPlacePlayerInTheOrder(Player player);
}
