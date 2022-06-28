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
import java.util.List;
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
        toDoChoice.setSendingPlayer(match.showAllPlayers().get(0));
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        toDoChoice = testRemoteView2.getCurrentMatch().getChoice();
        toDoChoice.setSendingPlayer(match.showAllPlayers().get(1));
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
        toDoChoice.setSendingPlayer(match.showCurrentPlayer());
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("1"));
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        toDoChoice.setSendingPlayer(match.showCurrentPlayer());
        System.out.println(toDoChoice);
        assertTrue(toDoChoice.setChoiceParam("1"));
        System.out.println(toDoChoice);
        assertFalse(toDoChoice.setChoiceParam("1"));
        testRemoteView1.setChangedForObservers();
        testRemoteView1.notifyObservers(toDoChoice);
        toDoChoice = testRemoteView1.getCurrentMatch().getChoice();
        toDoChoice.setSendingPlayer(match.showCurrentPlayer());
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
        toDoChoice.setSendingPlayer(match.showCurrentPlayer());
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
        toDoChoice.setSendingPlayer(match.showCurrentPlayer());
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
    void figureCardPlayedChoiceTest() throws WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, CardNotFoundException, NoMoreStudentsException, WrongDataplayerException, WrongColorException, NoMasterException {

        match = new ExpertMatch(2);
        match.addPlayer("Giovanni","BLACK","WIZARD1",1);
        match.addPlayer("Giorgio","WHITE","WIZARD2",2);

        ArrayList<FigureCard> figureCards = new ArrayList<>();
        FigureCardPlayedChoice choice;
        int coins;

        figureCards.addAll(match.showFigureCardsInGame());

        choice = new FigureCardPlayedChoice(figureCards);
        choice.setSendingPlayer(match.showCurrentPlayer());

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

    /** This test checks that students are correctly chosen through FigureCardWithStudentsChoice methods */
    @Test
    void figureCardWithStudentsTest() throws Exception {
        ExpertMatch match2 = new ExpertMatch(2);
        FigureCardWithStudents figureCard = new Jester();
        FigureCardWithStudentsChoice choice = new JesterChoice(figureCard);

        match2.addPlayer("Giovanni","BLACK","WIZARD1",1);
        match2.addPlayer("Giorgio","WHITE","WIZARD2",2);

        choice.setChosenStudent();

        for ( Student s : choice.getChosenStudent() )
            System.out.println(s);

    }

    /** This test checks that GrannyGrassChoice works correctly, blocking island through its methods, getting list of island in the toString method */
    @Test
    void grannyGrassChoiceTest() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException, NoMoreBlockCardsException, WrongCloudNumberException, NoIslandException, StudentIDAlreadyExistingException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, InexistentStudentException, CardNotFoundException {
        FigureCard figureCard = new GrannyGrass();
        GrannyGrassChoice choice = new GrannyGrassChoice();

        creationOfTheRightCard(figureCard);

        choice.toString(match);

        choice.setChoiceParam("1");
        choice.manageUpdate(match);

        assertTrue(match.getIslands().get(1).checkForbidden());

        assertEquals("Granny grass played from the current player",choice.whichChoicePhase());
    }

    /** This test checks that GrannyGrassChoice works correctly, blocking island through its methods, getting list of island in the setIslandPositionTmp method */
    @Test
    void grannyGrassChoiceIslandPosTest() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException, NoMoreBlockCardsException, WrongCloudNumberException, NoIslandException, StudentIDAlreadyExistingException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, InexistentStudentException, CardNotFoundException {
        FigureCard figureCard = new GrannyGrass();
        GrannyGrassChoice choice = new GrannyGrassChoice();

        creationOfTheRightCard(figureCard);

        choice.setIslandPositionTmp(match.getIslandPositions(), match);

        choice.setChoiceParam("1");
        choice.manageUpdate(match);

        assertTrue(match.getIslands().get(1).checkForbidden());
    }

    /** This test checks that the HeraldChoice works correctly activating the herald effect, islandPositions updated in toString(match)
     */
    @Test
    void heraldChoiceTest() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException, NoMasterException, SameInfluenceException, CardNotFoundException, FinishedGameEndTurnException, WrongCloudNumberException, NoMoreBlockCardsException, NoMoreTowerException, InvalidNumberOfTowers, NoTowerException, StudentIDAlreadyExistingException, FigureCardAlreadyPlayedInThisTurnException, InexistentStudentException, FinishedGameIslandException, TowerIDAlreadyExistingException, NoIslandException, InsufficientCoinException, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException {
        FigureCard figureCard = new Herald();
        HeraldChoice choice = new HeraldChoice();
        int i=0,j=0;

        creationOfTheRightCard(figureCard);

        do {
            j=0;
            i++;
            for (Player p : match.showAllPlayers()) {
                for (Student s : match.showCurrentPlayerDashboard().showEntrance()) {
                    match.moveStudentFromEntranceToDR(s);
                }
                match.moveStudentsFromCloudToEntrance(j);
                j++;
            }
        }while (i<10);
        match.checkAndMoveMasters();

        choice.toString(match);
        choice.setChoiceParam("3");
        choice.manageUpdate(match);

        for ( Integer position: match.getIslandPositions() ) {
            if (position!=3)
                assertTrue(match.getIslands().get(position).getTowerNum()==0);
            else
                assertTrue(match.getIslands().get(position).getTowerNum()==1);

        }

    }

    /** This test checks that the HeraldChoice works correctly activating the herald effect, islandPositions updated in setIslandPositionTmp
     */
    @Test
    void heraldChoiceIslandPosTest() throws NoMoreStudentsException, MaxNumberException, WrongDataplayerException, WrongColorException, NoMasterException, SameInfluenceException, CardNotFoundException, FinishedGameEndTurnException, WrongCloudNumberException, NoMoreBlockCardsException, NoMoreTowerException, InvalidNumberOfTowers, NoTowerException, StudentIDAlreadyExistingException, FigureCardAlreadyPlayedInThisTurnException, InexistentStudentException, FinishedGameIslandException, TowerIDAlreadyExistingException, NoIslandException, InsufficientCoinException, NoListOfSameColoredTowers, MaxNumberOfTowerPassedException {
        FigureCard figureCard = new Herald();
        HeraldChoice choice = new HeraldChoice();
        int i=0,j=0;

        creationOfTheRightCard(figureCard);

        do {
            j=0;
            i++;
            for (Player p : match.showAllPlayers()) {
                for (Student s : match.showCurrentPlayerDashboard().showEntrance()) {
                    match.moveStudentFromEntranceToDR(s);
                }
                match.moveStudentsFromCloudToEntrance(j);
                j++;
            }
        }while (i<10);
        match.checkAndMoveMasters();

        choice.setIslandPositionTmp(match.getIslandPositions());
        choice.setChoiceParam("4");
        choice.manageUpdate(match);

        for ( Integer position: match.getIslandPositions() ) {
            if (position!=4)
                assertTrue(match.getIslands().get(position).getTowerNum()==0);
            else
                assertTrue(match.getIslands().get(position).getTowerNum()==1);

        }

        assertEquals("Herald played from the current player", choice.whichChoicePhase());
    }

    /** This test checks that JesterChoice works correctly by swapping two students from the entrance with two from the figure card*/
    @Test
    void jesterChoiceTest() throws Exception {
        FigureCardWithStudents figureCard = new Jester();
        JesterChoice choice;
        Set<Student> entrance;

        creationOfTheRightCard(figureCard);

        for ( FigureCard f : match.showFigureCardsInGame() ) {
            if (f instanceof Jester)
                figureCard = (FigureCardWithStudents) f;
        }

        choice = new JesterChoice(figureCard);

        choice.toString(match);

        //Set num of students to move
        choice.setChoiceParam("2");

        entrance=match.showCurrentPlayerDashboard().showEntrance();
        Student figureCardS1=  ((Jester)figureCard).getStudentsOnCard().get(0);
        Student figureCardS2=  ((Jester)figureCard).getStudentsOnCard().get(1);
        Student entranceS1 = match.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0);
        Student entranceS2 = match.showCurrentPlayerDashboard().showEntrance().stream().toList().get(1);
        //choose student from figureCard
        choice.setChoiceParam("1");
        //System.out.println(choice.toString(match));
        choice.setChoiceParam("2");
        choice.setStudentsInEntrance(match.showCurrentPlayerDashboard().showEntrance().stream().toList());
        choice.setChoiceParam("1");
        choice.setChoiceParam("2");

        choice.manageUpdate(match);

        assertFalse(((Jester)figureCard).getStudentsOnCard().contains(figureCardS1));
        assertFalse(((Jester)figureCard).getStudentsOnCard().contains(figureCardS2));
        assertTrue(((Jester)figureCard).getStudentsOnCard().contains(entranceS1));
        assertTrue(((Jester)figureCard).getStudentsOnCard().contains(entranceS2));
        assertFalse(match.showCurrentPlayerDashboard().showEntrance().contains(entranceS1));
        assertFalse(match.showCurrentPlayerDashboard().showEntrance().contains(entranceS2));
        assertTrue(match.showCurrentPlayerDashboard().showEntrance().contains(figureCardS1));
        assertTrue(match.showCurrentPlayerDashboard().showEntrance().contains(figureCardS2));

        assertEquals("Jester played from the current player", choice.whichChoicePhase());

    }

    /** This test checks that MerchantChoice activates correctly the merchant effect */
    @Test
    void merchantChoiceTest() throws Exception {
        FigureCardWithStudents figureCard = new Merchant();
        MerchantChoice choice;

        creationOfTheRightCard(figureCard);

        for ( FigureCard f : match.showFigureCardsInGame() ) {
            if (f instanceof Merchant)
                figureCard = (FigureCardWithStudents) f;
        }
        choice = new MerchantChoice(figureCard);

        Student figureCardStudent = figureCard.getStudentsOnCardByInt(0);
        //System.out.println(figureCardStudent);
        //System.out.println(figureCardStudent.getID());
        assertFalse(match.getIslands().get(1).getStudents()[figureCardStudent.getColor().ordinal()].contains(figureCardStudent));

        choice.toString(match);
        choice.setChoiceParam("1");
        choice.setChoiceParam("1");

        choice.manageUpdate(match);

        /*System.out.println(match.getIslands().get(1).getStudents()[figureCardStudent.getColor().ordinal()]);
        for ( Student s : match.getIslands().get(1).getStudents()[figureCardStudent.getColor().ordinal()] ) {
            System.out.println(s);
            System.out.println(s.getID());
        }*/

        assertTrue(match.getIslands().get(1).getStudents()[figureCardStudent.getColor().ordinal()].contains(figureCardStudent));
    }

    /** This test check that side methods of MerchantChoice work */
    @Test
    void merchantChoiceSideMethodsTest() throws Exception {
        FigureCardWithStudents figureCard = new Merchant();
        MerchantChoice choice;

        creationOfTheRightCard(figureCard);

        for ( FigureCard f : match.showFigureCardsInGame() ) {
            if (f instanceof Merchant)
                figureCard = (FigureCardWithStudents) f;
        }
        choice = new MerchantChoice(figureCard);

        assertEquals("Merchant played from the current player", choice.whichChoicePhase());
        assertEquals(0, choice.getNumChoice());
        choice.toString(match);
        choice.setChoiceParam("1");
        assertEquals(1, choice.getNumChoice());
        choice.setIslandPositionSize(match.getIslands().size());
        assertEquals("",choice.toString());
    }

    /** This test checks that minstrelChoice works correctly and effectively activates the minstrel effect */
    @Test
    void minstrelChoiceTest() throws Exception {
        FigureCard figureCard = new Minstrel();
        MinstrelChoice choice = new MinstrelChoice();

        creationOfTheRightCard(figureCard);

        Student studentDR1 = match.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0);
        //Student studentDR2 = match.showCurrentPlayerDashboard().showEntrance().stream().toList().get(1);
        match.moveStudentFromEntranceToDR(studentDR1);
        //match.moveStudentFromEntranceToDR(studentDR2);
        Student studentEntrance1 = match.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0);
        //Student studentEntrance2 = match.showCurrentPlayerDashboard().showEntrance().stream().toList().get(1);

        choice.toString(match);
        choice.setChoiceParam("1");
        choice.setChoiceParam("1");
        choice.setChoiceParam("1");

        choice.manageUpdate(match);

        assertTrue(match.showCurrentPlayerDashboard().showEntrance().contains(studentDR1));
        assertFalse(match.showCurrentPlayerDashboard().showEntrance().contains(studentEntrance1));
        assertTrue(match.showCurrentPlayerDashboard().showDiningRoom().contains(studentEntrance1));
        assertFalse(match.showCurrentPlayerDashboard().showDiningRoom().contains(studentDR1));
    }

    /** This test checks side methods of minstrelChoice */
    @Test
    void minstrelChoiceSideMethodsTest() throws Exception {
        FigureCard figureCard = new Minstrel();
        MinstrelChoice choice = new MinstrelChoice();

        creationOfTheRightCard(figureCard);

        Student studentDR1 = match.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0);
        match.moveStudentFromEntranceToDR(studentDR1);
        Student studentEntrance1 = match.showCurrentPlayerDashboard().showEntrance().stream().toList().get(0);

        choice.toString(match);
        choice.setChoiceParam("1");
        List<Student> studentDR = new ArrayList<>();
        studentDR.add(studentDR1);
        List<Student> studentEntrance = new ArrayList<>();
        studentEntrance.add(studentEntrance1);
        choice.setStudentsFromEntrance(studentEntrance);
        choice.setStudentsFromDr(studentDR);

        choice.manageUpdate(match);
        choice.setCompleted(true);

        assertTrue(match.showCurrentPlayerDashboard().showEntrance().contains(studentDR1));
        assertFalse(match.showCurrentPlayerDashboard().showEntrance().contains(studentEntrance1));
        assertTrue(match.showCurrentPlayerDashboard().showDiningRoom().contains(studentEntrance1));
        assertFalse(match.showCurrentPlayerDashboard().showDiningRoom().contains(studentDR1));

        assertEquals("Minstrel played from the current player",choice.whichChoicePhase());
    }



}
