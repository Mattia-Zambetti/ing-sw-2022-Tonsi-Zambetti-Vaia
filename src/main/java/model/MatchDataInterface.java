package model;

import java.util.Observable;
import view.choice.*;
import view.*;
import model.*;

public interface MatchDataInterface {

    @Override
    String toString();

    Choice getChoice();

    Player showCurrentPlayer();
}
