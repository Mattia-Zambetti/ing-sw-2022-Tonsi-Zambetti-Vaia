package model;

import java.io.Serializable;
import java.util.*;

import controller.choice.Choice;
import view.choice.*;

public interface MatchDataInterface extends Serializable {

    @Override
    String toString();

    Choice getChoice();

    Player showCurrentPlayer();

    String getErrorMessage();

    List<String> showAllPlayersNickname();
}
