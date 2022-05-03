package model;

import view.choice.Choice;

public interface MatchDataInterface {

    @Override
    String toString();

    Player showCurrentPlayer();

    Choice getChoice();

}
