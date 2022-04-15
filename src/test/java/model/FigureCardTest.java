package model;

import junit.framework.TestCase;
import model.FigureCards.Centaur;
import model.FigureCards.FigureCard;
import model.FigureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class FigureCardTest extends TestCase {
    Match expertMatch;
    FigureCard figureCardTest;

    @BeforeEach
    void init() {

    }


    //it tests if the centaur card is correctly played when it's called and if it throws the exception if it's called
    //two times in the same turn
    @Test
    void centaurTest() throws FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, MaxNumberException, WrongDataplayerException, WrongColorException {
        figureCardTest = new Centaur();

        do {
            expertMatch = new ExpertMatch(3);

        }while(!(((ExpertMatch)expertMatch).showFigureCardsInGame()).contains(figureCardTest));

        expertMatch.addPlayer("Tonsi", "BLACK", "WIZARD1");
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();

        expertMatch.addPlayer("Zambo", "GREY", "WIZARD2");


        assertEquals(3,figureCardTest.getPrice());

        ((ExpertMatch)expertMatch).playFigureCard(figureCardTest);

        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();
        ((ExpertMatch) expertMatch).addCoinToCurrPlayer();


        assertThrows(FigureCardAlreadyPlayedInThisTurnException.class, ()->((ExpertMatch)expertMatch).playFigureCard(figureCardTest));

        assertEquals(4, figureCardTest.getPrice());
        assertEquals(0, expertMatch.showCurrentPlayerDashboard().getCoinsNumber());


        assertTrue(((ExpertMatchInterface) expertMatch).isCentaurEffect());

        expertMatch.setNextCurrDashboard();
        assertFalse(((ExpertMatchInterface) expertMatch).isCentaurEffect());

        assertThrows(InsufficientCoinException.class, ()->((ExpertMatch)expertMatch).playFigureCard(figureCardTest));
    }

}