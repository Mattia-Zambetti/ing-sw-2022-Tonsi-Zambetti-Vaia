package model;

import java.io.Serializable;
import java.util.Observable;
import view.choice.*;
import view.*;
import model.*;

public interface MatchDataInterface extends Serializable {

    @Override
    String toString();

    Choice getChoice();

    Player showCurrentPlayer();

    String getErrorMessage();
}
