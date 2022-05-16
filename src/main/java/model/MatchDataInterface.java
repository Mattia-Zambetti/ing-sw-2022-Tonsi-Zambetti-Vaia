package model;

import java.io.Serializable;
import java.util.*;

import controller.choice.Choice;

public interface MatchDataInterface extends Serializable {

    @Override
    String toString();

    Dashboard showCurrentPlayerDashboard();
    List<Integer> getIslandPositions();
    Choice getChoice();

    Player showCurrentPlayer();

    String getErrorMessage();

    List<String> showAllPlayersNickname();
}
