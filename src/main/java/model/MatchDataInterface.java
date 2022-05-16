package model;

import controller.choice.Choice;

import java.io.Serializable;
import java.util.List;

public interface MatchDataInterface extends Serializable {

    @Override
    String toString();

    Choice getChoice();

    Player showCurrentPlayer();

    String getErrorMessage();

    List<Player> showAllPlayers();
}
