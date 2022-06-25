package controller;

import controller.choice.*;
import graphicAssets.CLIgraphicsResources;
import model.*;
import model.exception.*;
import model.figureCards.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.TestRemoteView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    static final int TOTALPLAYERSNUMBER=2;
    Match match;
    Controller controller;
    TestRemoteView testRemoteView1;
    TestRemoteView testRemoteView2;

    @BeforeEach void init() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException {

        //initServer();


        match = new NormalMatch(TOTALPLAYERSNUMBER);
        controller = new Controller(match);

        testRemoteView1 = new TestRemoteView();
        testRemoteView2 = new TestRemoteView();

        match.addObserver(testRemoteView1);
        match.addObserver(testRemoteView2);
        testRemoteView1.addObserver(controller);
        testRemoteView2.addObserver(controller);

        for(int i=1; i<=TOTALPLAYERSNUMBER; i++){
            match.setChoicePhase( new DataPlayerChoice(TOTALPLAYERSNUMBER, i));
            match.notifyMatchObservers();
        }

    }

    @Test
    void matchTest() {
        Choice toDoChoice;
        assertTrue( match.getChoice() instanceof DataPlayerChoice);
        toDoChoice = new DataPlayerChoice(2);
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("Player1"));
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("1"));
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        toDoChoice = new DataPlayerChoice(2);
        System.out.println(toDoChoice);
        toDoChoice.setChoiceParam("Player2");
        System.out.println(toDoChoice);
        toDoChoice.setChoiceParam("2");
        System.out.println(toDoChoice);
        toDoChoice.setChoiceParam("2");
        testRemoteView2.setChangedForObservers();
        testRemoteView2.notifyObservers(toDoChoice);

        assertEquals(2, match.showAllPlayers().size());
        assertTrue( match.getChoice() instanceof CardChoice );

        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        toDoChoice = testRemoteView2.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView2.setChangedForObservers();
        testRemoteView2.notifyObservers(toDoChoice);
        assertTrue( match.getChoice() instanceof CardChoice );
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("3"));
        testRemoteView2.setChangedForObservers();
        testRemoteView2.notifyObservers(toDoChoice);

        assertTrue( match.getChoice() instanceof MoveStudentChoice);
        assertEquals( match.showCurrentPlayerDashboard().getPlayer().getNickname(), "Player1");



        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("1"));
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("1"));
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("1"));
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("2"));
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("aa"));
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("2"));
        assertEquals(2, ((MoveStudentChoice)toDoChoice).getIslandID());
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);

        assertTrue( match.getChoice() instanceof MoveMotherNatureChoice);

        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("75"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        assertTrue( match.getChoice() instanceof MoveMotherNatureChoice );
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);

        assertTrue( match.getChoice() instanceof CloudChoice);
        toDoChoice.toString();

        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("aa"));
        assertFalse(toDoChoice.setChoiceParam("1")); //VIENE LANCIATA ECCEZIONE SUL FATTO CHE NON CI SIANO TORI SULL'ISOLA, ERRATO
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);

        assertEquals( match.showCurrentPlayerDashboard().getPlayer().getNickname(), "Player2");
        assertTrue( match.getChoice() instanceof MoveStudentChoice );

        assertEquals(CLIgraphicsResources.getStringColor("please try again",CLIgraphicsResources.ColorCLIgraphicsResources.ANSI_RED),toDoChoice.retryMessage());
    }


    //It tests how does data player choice work with every type of input(wrong and correct).
    //The Choice has to reject wrong typing and it has to increment the counter of inputs correctly
    @Test
    void DataPlayerChoiceTest2Players(){
        DataPlayerChoice toDoChoice = new DataPlayerChoice(TOTALPLAYERSNUMBER);

        assertTrue(toDoChoice.setChoiceParam("Zambo"));
        assertEquals("Zambo", toDoChoice.getPlayer().getNickname());

        assertTrue(toDoChoice.setChoiceParam("40"));
        assertEquals(1, toDoChoice.getNumChoice());
        assertTrue(toDoChoice.setChoiceParam("1"));
        assertEquals(2, toDoChoice.getNumChoice());

        assertTrue(toDoChoice.setChoiceParam("40"));
        assertEquals(2, toDoChoice.getNumChoice());
        assertFalse(toDoChoice.setChoiceParam("1"));


    }


    @Test
    void DataPlayerChoiceTest3Players(){
        match = new NormalMatch(TOTALPLAYERSNUMBER+1);
        controller = new Controller(match);

        testRemoteView1 = new TestRemoteView();
        testRemoteView2 = new TestRemoteView();
        TestRemoteView testRemoteView3 = new TestRemoteView();

        match.addObserver(testRemoteView1);
        match.addObserver(testRemoteView2);
        match.addObserver(testRemoteView3);
        testRemoteView1.addObserver(controller);
        testRemoteView2.addObserver(controller);
        testRemoteView3.addObserver(controller);

        for(int i=1; i<=TOTALPLAYERSNUMBER+1; i++){
            match.setChoicePhase( new DataPlayerChoice(TOTALPLAYERSNUMBER+1, i));
            match.notifyMatchObservers();
        }

        DataPlayerChoice toDoChoice = new DataPlayerChoice(TOTALPLAYERSNUMBER+1);

        assertTrue(toDoChoice.setChoiceParam("Zambo"));
        assertEquals("Zambo", toDoChoice.getPlayer().getNickname());

        System.out.println(toDoChoice);

        assertTrue(toDoChoice.setChoiceParam("40"));
        assertEquals(1, toDoChoice.getNumChoice());
        assertTrue(toDoChoice.setChoiceParam("1"));
        assertEquals(2, toDoChoice.getNumChoice());

        assertTrue(toDoChoice.setChoiceParam("40"));
        assertTrue(toDoChoice.setChoiceParam("aaa"));
        assertEquals(2, toDoChoice.getNumChoice());
        assertFalse(toDoChoice.setChoiceParam("3"));


        toDoChoice.setPossessor(1);
        assertEquals(1, toDoChoice.getPossessor());
    }

    @Test
    void StartingMatchChoiceTest() throws WrongCloudNumberException, MaxNumberException, CardNotFoundException {
        StartingMatchChoice choice=new StartingMatchChoice();

        System.out.println(choice);

        choice.setChoiceParam("aa");
        assertEquals(0, choice.getNumChoice());
        choice.setChoiceParam("6");
        assertEquals(0, choice.getNumChoice());
        choice.setChoiceParam("2");
        assertEquals(1, choice.getNumChoice());

        System.out.println(choice);

        choice.setChoiceParam("aa");
        assertEquals(1, choice.getNumChoice());
        choice.setChoiceParam("0");
        assertEquals(1, choice.getNumChoice());
        assertFalse(choice.setChoiceParam("1"));

        assertEquals(1, choice.getMatchType());
        assertEquals(2, choice.getTotalPlayersNumMatch());
    }


    @Test
    private void creationOfTheRightCard(FigureCard figureCardWanted) throws MaxNumberException, WrongDataplayerException, WrongColorException, NoMoreStudentsException {
        do {
            match = new ExpertMatch(2);

        }while(!(((ExpertMatch)match).showFigureCardsInGame()).contains(figureCardWanted));

        match.addPlayer("Tonsi", "BLACK", "WIZARD1",1);
        ((ExpertMatch) match).addCoinToCurrPlayer();
        ((ExpertMatch) match).addCoinToCurrPlayer();

        match.addPlayer("Zambo", "WHITE", "WIZARD2",2);
    }

    /**Tests that the CardChoice toString is correct     */
    @Test
    void cardChoiceStringTest() {
        Choice choice;
        Set<Card> cardSet = new HashSet<>();
        String cardListString = "Choose a card id from your deck:\n";
        Card card;
        for ( int i=0; i<5; i++ ) {
            card = new Card(i,i,i);
            cardSet.add(card);
            cardListString = cardListString.concat(card+"\n");
        }
        choice = new CardChoice(cardSet);

        assertEquals(choice.toString(match),cardListString);
        assertEquals(choice.whichChoicePhase(),"It's the card choice phase");
    }

     /**This test checks that methods about players in choice abstract class work correctly */
    @Test
    void choicePlayerTest() {
        Choice choice = new DataPlayerChoice(2,1);

        choice.setSendingPlayer(new Player("Giovanni"));

        assertEquals(new Player("Giovanni"), choice.getSendingPlayer());
    }

    /**Test the completed attribute of choice*/
    @Test
    void completedChoiceTest() {
        Choice choice = new DataPlayerChoice(2,1);

        assertFalse(choice.getChoiceCompleted());

        choice.setChoiceParam("1");
        choice.setChoiceParam("1");
        choice.setChoiceParam("1");

        assertTrue(choice.getChoiceCompleted());
    }

    /**Tests that the CloudChoice toString is correct     */
    @Test
    void cloudChoiceStringTest() {
        Choice choice = new CloudChoice();

        assertEquals(choice.toString(match),"Insert cloud number you want to take: ");
        assertEquals(choice.whichChoicePhase(),"It's the cloud choice phase");
    }

    /**Tests DataPlayerChoice playerNumber method
     */
    @Test
    void dataPlayerChoicePlayerNumberTest() {
        DataPlayerChoice dataPlayerChoice = new DataPlayerChoice(2, 1);

        assertEquals(2, dataPlayerChoice.getPlayerNum());

    }

    /**This test checks toString method of DataPlayerChoice */
    @Test
    void dataPlayerChoiceToStringTest() {
        DataPlayerChoice dataPlayerChoice = new DataPlayerChoice(2, 1);

        assertEquals(dataPlayerChoice.whichChoicePhase(), "It's the data player choice phase");
        assertEquals(dataPlayerChoice.toString(match), "Insert your name:");
    }

    /**This test check all methods of FigureCardActionChoice, doesn't work as well as */
    /*@Test
    void figureCardActionChoiceTest() {
        FigureCardActionChoice choice = new MushroomCollectorChoice();

        assertEquals(choice.whichChoicePhase(), "generic figure card");
    }*/

    /**This test checks that all FigureCardPlayedChoice methods works correctly */
    @Test
    void figureCardPlayedChoiceTest() throws WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, NoMoreStudentsException, WrongDataplayerException, WrongColorException {

        match = new ExpertMatch(2);
        match.addPlayer("Giovanni","BLACK","WIZARD1",1);
        match.addPlayer("Giorgio","WHITE","WIZARD2",2);

        ArrayList<FigureCard> figureCards = new ArrayList<>();
        FigureCardPlayedChoice choice;
        int coins;

        figureCards.addAll(match.showFigureCardsInGame());

        choice = new FigureCardPlayedChoice(figureCards);

        choice.setChosenFigureCard(1);
        assertEquals(figureCards.get(1), choice.getChosenFigureCard());

        choice.toString(match);
        choice.setChoiceParam("1");
        assertEquals(figureCards.get(0), choice.getChosenFigureCard());

        match.showCurrentPlayerDashboard().addCoin();
        match.showCurrentPlayerDashboard().addCoin();
        match.showCurrentPlayerDashboard().addCoin();
        match.showCurrentPlayerDashboard().addCoin();
        match.showCurrentPlayerDashboard().addCoin();
        match.showCurrentPlayerDashboard().addCoin();
        match.showCurrentPlayerDashboard().addCoin();
        coins = match.showCurrentPlayerDashboard().getCoinsNumber();
        coins -= choice.getChosenFigureCard().getPrice();
        choice.manageUpdate(match);

        assertEquals(coins, match.showCurrentPlayerDashboard().getCoinsNumber());

        assertEquals("It's the figure card played choice phase", choice.whichChoicePhase());

    }

    /*@Test
    void figureCardWithStudentsTest() throws Exception {
        ExpertMatch match2 = new ExpertMatch(2);
        FigureCardWithStudents figureCard = new Jester();
        FigureCardWithStudentsChoice choice = new JesterChoice(figureCard);

        match2.addPlayer("Giovanni","BLACK","WIZARD1",1);
        match2.addPlayer("Giorgio","WHITE","WIZARD2",2);

        figureCard.getStudentsOnCard().get(0)

    }*/


}
