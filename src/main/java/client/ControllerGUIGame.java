package client;

import controller.choice.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Card;
import model.Color;
import model.ExpertMatch;
import model.MatchDataInterface;
import model.figureCards.FigureCard;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControllerGUIGame extends ControllerGUIInterface implements Initializable {

    //Dashboards:
    @FXML
    private ImageView Dashboard1;
    @FXML
    private ImageView Dashboard2;
    @FXML
    private ImageView Dashboard3;
    @FXML
    private ImageView Dashboard4;

    //Current cards:
    @FXML
    private  ImageView cardDb1;
    @FXML
    private  ImageView cardDb2;
    @FXML
    private  ImageView cardDb3;
    @FXML
    private  ImageView cardDb4;

    //Figure cards:
    @FXML
    private ImageView figureCard1;
    @FXML
    private ImageView figureCard2;
    @FXML
    private ImageView figureCard3;
    @FXML
    private GridPane expertMatchPane;

    //cards:
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;
    @FXML
    private ImageView card4;
    @FXML
    private ImageView card5;
    @FXML
    private ImageView card6;
    @FXML
    private ImageView card7;
    @FXML
    private ImageView card8;
    @FXML
    private ImageView card9;
    @FXML
    private ImageView card10;
    @FXML
    private HBox boxCards;

    //START Dashboard 1 Students, master and towers
    @FXML
    private ImageView D1EntranceStudent1;
    @FXML
    private ImageView D1EntranceStudent2;
    @FXML
    private ImageView D1EntranceStudent3;
    @FXML
    private ImageView D1EntranceStudent4;
    @FXML
    private ImageView D1EntranceStudent5;
    @FXML
    private ImageView D1EntranceStudent6;
    @FXML
    private ImageView D1EntranceStudent7;
    @FXML
    private ImageView D1EntranceStudent8;
    @FXML
    private ImageView D1EntranceStudent9;
    @FXML
    private ImageView D1GreenDRStudent1;
    @FXML
    private ImageView D1GreenDRStudent2;
    @FXML
    private ImageView D1GreenDRStudent3;
    @FXML
    private ImageView D1GreenDRStudent4;
    @FXML
    private ImageView D1GreenDRStudent5;
    @FXML
    private ImageView D1GreenDRStudent6;
    @FXML
    private ImageView D1GreenDRStudent7;
    @FXML
    private ImageView D1GreenDRStudent8;
    @FXML
    private ImageView D1GreenDRStudent9;
    @FXML
    private ImageView D1GreenDRStudent10;
    @FXML
    private ImageView D1RedDRStudent1;
    @FXML
    private ImageView D1RedDRStudent2;
    @FXML
    private ImageView D1RedDRStudent3;
    @FXML
    private ImageView D1RedDRStudent4;
    @FXML
    private ImageView D1RedDRStudent5;
    @FXML
    private ImageView D1RedDRStudent6;
    @FXML
    private ImageView D1RedDRStudent7;
    @FXML
    private ImageView D1RedDRStudent8;
    @FXML
    private ImageView D1RedDRStudent9;
    @FXML
    private ImageView D1RedDRStudent10;
    @FXML
    private ImageView D1YellowDRStudent1;
    @FXML
    private ImageView D1YellowDRStudent2;
    @FXML
    private ImageView D1YellowDRStudent3;
    @FXML
    private ImageView D1YellowDRStudent4;
    @FXML
    private ImageView D1YellowDRStudent5;
    @FXML
    private ImageView D1YellowDRStudent6;
    @FXML
    private ImageView D1YellowDRStudent7;
    @FXML
    private ImageView D1YellowDRStudent8;
    @FXML
    private ImageView D1YellowDRStudent9;
    @FXML
    private ImageView D1YellowDRStudent10;
    @FXML
    private ImageView D1PinkDRStudent1;
    @FXML
    private ImageView D1PinkDRStudent2;
    @FXML
    private ImageView D1PinkDRStudent3;
    @FXML
    private ImageView D1PinkDRStudent4;
    @FXML
    private ImageView D1PinkDRStudent5;
    @FXML
    private ImageView D1PinkDRStudent6;
    @FXML
    private ImageView D1PinkDRStudent7;
    @FXML
    private ImageView D1PinkDRStudent8;
    @FXML
    private ImageView D1PinkDRStudent9;
    @FXML
    private ImageView D1PinkDRStudent10;
    @FXML
    private ImageView D1BlueDRStudent1;
    @FXML
    private ImageView D1BlueDRStudent2;
    @FXML
    private ImageView D1BlueDRStudent3;
    @FXML
    private ImageView D1BlueDRStudent4;
    @FXML
    private ImageView D1BlueDRStudent5;
    @FXML
    private ImageView D1BlueDRStudent6;
    @FXML
    private ImageView D1BlueDRStudent7;
    @FXML
    private ImageView D1BlueDRStudent8;
    @FXML
    private ImageView D1BlueDRStudent9;
    @FXML
    private ImageView D1BlueDRStudent10;
    @FXML
    private ImageView D1GreenMaster;
    @FXML
    private ImageView D1RedMaster;
    @FXML
    private ImageView D1YellowMaster;
    @FXML
    private ImageView D1PinkMaster;
    @FXML
    private ImageView D1BlueMaster;
    @FXML
    private ImageView D1BlackTower1;
    @FXML
    private ImageView D1BlackTower2;
    @FXML
    private ImageView D1BlackTower3;
    @FXML
    private ImageView D1BlackTower4;
    @FXML
    private ImageView D1BlackTower5;
    @FXML
    private ImageView D1BlackTower6;
    @FXML
    private ImageView D1BlackTower7;
    @FXML
    private ImageView D1BlackTower8;

    //END

    private Map<Card, ImageView> fromCardsToImages;

    private Map<ImageView, Card> fromImagesToCards;

    private ArrayList<ImageView> entranceStudentsD1;
    private Map<Color, List<ImageView>> diningRoomStudentsD1;
    private Map<Color, ImageView> masterD1; //TODO initialize
    private Map<Integer, ImageView> towerD1; //TODO initialize


    private static Map<Integer,Image> figureCardsMap=new HashMap<>(){{
        put(1, new Image(getClass().getResourceAsStream("/client/Images/Centaur.jpg" )));
        put(2, new Image(getClass().getResourceAsStream("/client/Images/Jester.jpg" )));
        put(3, new Image(getClass().getResourceAsStream("/client/Images/Knight.jpg" )));
        put(4, new Image(getClass().getResourceAsStream("/client/Images/Merchant.jpg" )));
        put(5, new Image(getClass().getResourceAsStream("/client/Images/MushroomCollector.jpg")) );
        put(6, new Image(getClass().getResourceAsStream("/client/Images/Postman.jpg" )));
        put(7, new Image(getClass().getResourceAsStream("/client/Images/Princess.jpg" )));
        put(8, new Image(getClass().getResourceAsStream("/client/Images/GrannyGrass.jpg") ));
        put(9, new Image(getClass().getResourceAsStream("/client/Images/Thief.jpg" )));
        put(10, new Image(getClass().getResourceAsStream("/client/Images/Farmer.jpg" )));
        put(11, new Image(getClass().getResourceAsStream("/client/Images/Herald.jpg" )));
        put(12, new Image(getClass().getResourceAsStream("/client/Images/Minstrel.jpg" )));
    }};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card4.setVisible(false);
        card5.setVisible(false);
        card6.setVisible(false);
        card7.setVisible(false);
        card8.setVisible(false);
        card9.setVisible(false);
        card10.setVisible(false);

        fromCardsToImages =new HashMap<>();
        fromCardsToImages.put(new Card(1,1,1), card1);
        fromCardsToImages.put(new Card(2,1,2), card2);
        fromCardsToImages.put(new Card(3,2,3), card3);
        fromCardsToImages.put(new Card(4,2,4), card4);
        fromCardsToImages.put(new Card(5,3,5), card5);
        fromCardsToImages.put(new Card(6,3,6), card6);
        fromCardsToImages.put(new Card(7,4,7), card7);
        fromCardsToImages.put(new Card(8,4,8), card8);
        fromCardsToImages.put(new Card(9,5,9), card9);
        fromCardsToImages.put(new Card(10,5,10), card10);

        fromImagesToCards =new HashMap<>();
        fromImagesToCards.put(card1,new Card(1,1,1));
        fromImagesToCards.put(card2,new Card(2,1,2));
        fromImagesToCards.put(card3,new Card(3,2,3));
        fromImagesToCards.put(card4,new Card(4,2,4));
        fromImagesToCards.put(card5,new Card(5,3,5) );
        fromImagesToCards.put(card6, new Card(6,3,6));
        fromImagesToCards.put(card7,new Card(7,4,7));
        fromImagesToCards.put(card8,new Card(8,4,8));
        fromImagesToCards.put(card9,new Card(9,5,9));
        fromImagesToCards.put(card10,new Card(10,5,10));

        initializeEntranceStudentsD1();
        initializeDiningRoomStudentsD1();
    }



    /**updates*/
    public void updateInitialMatchView() {

        if (client.isMatchCompletelyCreated()) {
            MatchDataInterface match = client.getMatchView();

            //figure cards management and coins management:
            if(match instanceof ExpertMatch) {
                List<FigureCard> figureCards = match.showFigureCardsInGame();

                figureCard1.setImage(figureCardsMap.get(figureCards.get(0).getCardId()));
                figureCard2.setImage(figureCardsMap.get(figureCards.get(1).getCardId()));
                figureCard3.setImage(figureCardsMap.get(figureCards.get(2).getCardId()));
            }else {
                expertMatchPane.setVisible(false);

            }

            //Dashboards management:
            if(match.showAllPlayers().size()==3){
                Dashboard4.setVisible(false);
            }else if(match.showAllPlayers().size()==2) {
                Dashboard4.setVisible(false);
                Dashboard3.setVisible(false);
            }
        }
    }

    public void updateCardsView(){
        boxCards.setVisible(true);
        if(client.getActualToDoChoice() instanceof CardChoice && client.isChoiceTime()){
            for (Card c:client.getMatchView().showCurrentPlayerDashboard().showCards()){
                if(fromCardsToImages.containsKey(c)){
                    fromCardsToImages.get(c).setVisible(true);
                }else{
                    fromCardsToImages.get(c).setVisible(false);
                }
            }
            if(client.getMatchView().getPlacePlayerInTheOrder(client.getPlayer())>=2) {
                cardDb2.setImage(fromCardsToImages.get(client.getMatchView().showAllCurrentCards().get(0)).getImage());
                cardDb2.setVisible(true);
                if (client.getMatchView().showAllPlayers().size() == 3 && client.getMatchView().getPlacePlayerInTheOrder(client.getPlayer())>=3) {
                    cardDb3.setImage(fromCardsToImages.get(client.getMatchView().showAllCurrentCards().get(1)).getImage());
                    cardDb3.setVisible(true);
                }
                if (client.getMatchView().showAllPlayers().size() == 4 && client.getMatchView().getPlacePlayerInTheOrder(client.getPlayer())==4) {
                    cardDb4.setImage(fromCardsToImages.get(client.getMatchView().showAllCurrentCards().get(2)).getImage());
                    cardDb4.setVisible(true);
                }

            }

        }else{
            for (ImageView c: fromCardsToImages.values()) {
                c.setVisible(false);
            }
        }
    }

    public void setInvisibleCards(){
        boxCards.setVisible(false);
    }



    @Override
    public void switchScene(Choice choice) throws IOException {

    }

    @Override
    public void printMessageText(String s) {

    }


    /**Commands from the scene*/

    public void submitCardValue(Event event){
        if(client.getActualToDoChoice() instanceof CardChoice) {
            if (fromCardsToImages.containsValue((ImageView) event.getSource())) {
                client.getActualToDoChoice().setChoiceParam(""+fromImagesToCards.get(((ImageView) event.getSource())).getId());
                cardDb1.setVisible(true);
                cardDb1.setImage(((ImageView) event.getSource()).getImage());
                ((ImageView)event.getSource()).setVisible(false);
                setInvisibleCards();
                synchronized ( client.getOutputStreamLock() ) {
                    client.getOutputStreamLock().notifyAll();
                }
            }
        }
    }

    public void submitFigureCardValue(Event event){
        if(client.getMatchView() instanceof ExpertMatch
                && !(client.getActualToDoChoice() instanceof FigureCardActionChoice)
                && !(client.getActualToDoChoice() instanceof CardChoice)
                && !(client.getActualToDoChoice() instanceof DataPlayerChoice) && client.isFigureCardNotPlayed()) {
            Choice figureCardChoice = new FigureCardPlayedChoice(client.getMatchView().showFigureCardsInGame());
            client.setActualToDoChoiceQueue(client.getActualToDoChoice());
            client.setActualToDoChoice( figureCardChoice);
            client.setFigureCardNotPlayed(false);
            client.getActualToDoChoice().setSendingPlayer(client.getPlayer());
            try {
                client.getOutputStream().writeObject(client.getActualToDoChoiceQueue());
                client.getOutputStream().flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void zoomCardOnEnter(Event event){
        if(client.getActualToDoChoice() instanceof CardChoice) {
            ((ImageView) event.getSource()).setFitHeight(((ImageView) event.getSource()).getFitHeight() * 1.5);
            ((ImageView) event.getSource()).setFitWidth(((ImageView) event.getSource()).getFitWidth() * 1.5);
        }
    }

    public void zoomCardOnExit(Event event){
        if(client.getActualToDoChoice() instanceof CardChoice) {
            ((ImageView) event.getSource()).setFitHeight(((ImageView) event.getSource()).getFitHeight() / 1.5);
            ((ImageView) event.getSource()).setFitWidth(((ImageView) event.getSource()).getFitWidth() / 1.5);
        }
    }

    @FXML
    public void keyPressedManager(KeyEvent e){
        System.out.println("c");
        if(e.getCode()==KeyCode.C){
            boxCards.setVisible(!boxCards.isVisible());
        }

    }

    //ZAMBO
    public void chooseStudent(MouseEvent event) {
        if ( event.getSource() instanceof ImageView ) {
            ImageView chosenStudent = (ImageView) event.getSource();
            if (client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 0) {
                client.getActualToDoChoice().setChoiceParam(String.valueOf(entranceStudentsD1.indexOf(chosenStudent)+1));
                /*switch ( studentID ) {
                    case ("D1EntranceStudent1"):
                        client.getActualToDoChoice().setChoiceParam("1");
                        break;
                    case ("D1EntranceStudent2"):
                        client.getActualToDoChoice().setChoiceParam("2");
                        break;
                    case ("D1EntranceStudent3"):
                        client.getActualToDoChoice().setChoiceParam("3");
                        break;
                    case ("D1EntranceStudent4"):
                        client.getActualToDoChoice().setChoiceParam("4");
                        break;
                    case ("D1EntranceStudent5"):
                        client.getActualToDoChoice().setChoiceParam("5");
                        break;
                    case ("D1EntranceStudent6"):
                        client.getActualToDoChoice().setChoiceParam("6");
                        break;
                    case ("D1EntranceStudent7"):
                        client.getActualToDoChoice().setChoiceParam("7");
                        break;
                    case ("D1EntranceStudent8"):
                        client.getActualToDoChoice().setChoiceParam("8");
                        break;
                    case ("D1EntranceStudent9"):
                        client.getActualToDoChoice().setChoiceParam("9");
                        break;
                }*/
            }
        }else
            throw new IllegalArgumentException("chooseStudent method called by an Object that is not an ImageView");
    }

    public void chooseIsland(MouseEvent event) {
        if ( event.getSource() instanceof ImageView ) {
            String islandID = ((ImageView) event.getSource()).getId();
            if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
                client.getActualToDoChoice().setChoiceParam("2");
                switch(islandID) {
                    case("island1"):
                        client.getActualToDoChoice().setChoiceParam("0");
                        break;
                    case("island2"):
                        client.getActualToDoChoice().setChoiceParam("1");
                        break;
                    case("island3"):
                        client.getActualToDoChoice().setChoiceParam("2");
                        break;
                    case("island4"):
                        client.getActualToDoChoice().setChoiceParam("3");
                        break;
                    case("island5"):
                        client.getActualToDoChoice().setChoiceParam("4");
                        break;
                    case("island6"):
                        client.getActualToDoChoice().setChoiceParam("5");
                        break;
                    case("island7"):
                        client.getActualToDoChoice().setChoiceParam("6");
                        break;
                    case("island8"):
                        client.getActualToDoChoice().setChoiceParam("7");
                        break;
                    case("island9"):
                        client.getActualToDoChoice().setChoiceParam("8");
                        break;
                    case("island10"):
                        client.getActualToDoChoice().setChoiceParam("9");
                        break;
                    case("island11"):
                        client.getActualToDoChoice().setChoiceParam("10");
                        break;
                    case("island12"):
                        client.getActualToDoChoice().setChoiceParam("11");
                        break;
                }
            }
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }else
            throw new IllegalArgumentException("chooseIsland method called by an Object that is not an ImageView");
    }

    public void chooseDR(MouseEvent event) {
        if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
            client.getActualToDoChoice().setChoiceParam("1");
        }
        synchronized ( client.getOutputStreamLock() ) {
            client.getOutputStreamLock().notifyAll();
        }
    }

    /**Put all ImageView of entrance students into an ArrayList
     */
    private void initializeEntranceStudentsD1() {
        entranceStudentsD1 = new ArrayList<>();
        entranceStudentsD1.add(D1EntranceStudent1);
        entranceStudentsD1.add(D1EntranceStudent2);
        entranceStudentsD1.add(D1EntranceStudent3);
        entranceStudentsD1.add(D1EntranceStudent4);
        entranceStudentsD1.add(D1EntranceStudent5);
        entranceStudentsD1.add(D1EntranceStudent6);
        entranceStudentsD1.add(D1EntranceStudent7);
        entranceStudentsD1.add(D1EntranceStudent8);
        entranceStudentsD1.add(D1EntranceStudent9);
    }

    /**Put all ImageView of DR students into an ArrayList and set them invisible
     */
    private void initializeDiningRoomStudentsD1() {
        diningRoomStudentsD1 = new HashMap<>();
        //Green students
        diningRoomStudentsD1.put(Color.GREEN, new ArrayList<>());
        diningRoomStudentsD1.get(Color.GREEN).add(D1GreenDRStudent1);
        diningRoomStudentsD1.get(Color.GREEN).add(D1GreenDRStudent2);
        diningRoomStudentsD1.get(Color.GREEN).add(D1GreenDRStudent3);
        diningRoomStudentsD1.get(Color.GREEN).add(D1GreenDRStudent4);
        diningRoomStudentsD1.get(Color.GREEN).add(D1GreenDRStudent5);
        diningRoomStudentsD1.get(Color.GREEN).add(D1GreenDRStudent6);
        diningRoomStudentsD1.get(Color.GREEN).add(D1GreenDRStudent7);
        diningRoomStudentsD1.get(Color.GREEN).add(D1GreenDRStudent8);
        diningRoomStudentsD1.get(Color.GREEN).add(D1GreenDRStudent9);
        diningRoomStudentsD1.get(Color.GREEN).add(D1GreenDRStudent10);
        //Red students
        diningRoomStudentsD1.put(Color.RED, new ArrayList<>());
        diningRoomStudentsD1.get(Color.RED).add(D1RedDRStudent1);
        diningRoomStudentsD1.get(Color.RED).add(D1RedDRStudent2);
        diningRoomStudentsD1.get(Color.RED).add(D1RedDRStudent3);
        diningRoomStudentsD1.get(Color.RED).add(D1RedDRStudent4);
        diningRoomStudentsD1.get(Color.RED).add(D1RedDRStudent5);
        diningRoomStudentsD1.get(Color.RED).add(D1RedDRStudent6);
        diningRoomStudentsD1.get(Color.RED).add(D1RedDRStudent7);
        diningRoomStudentsD1.get(Color.RED).add(D1RedDRStudent8);
        diningRoomStudentsD1.get(Color.RED).add(D1RedDRStudent9);
        diningRoomStudentsD1.get(Color.RED).add(D1RedDRStudent10);
        //Yellow students
        diningRoomStudentsD1.put(Color.YELLOW, new ArrayList<>());
        diningRoomStudentsD1.get(Color.YELLOW).add(D1YellowDRStudent1);
        diningRoomStudentsD1.get(Color.YELLOW).add(D1YellowDRStudent2);
        diningRoomStudentsD1.get(Color.YELLOW).add(D1YellowDRStudent3);
        diningRoomStudentsD1.get(Color.YELLOW).add(D1YellowDRStudent4);
        diningRoomStudentsD1.get(Color.YELLOW).add(D1YellowDRStudent5);
        diningRoomStudentsD1.get(Color.YELLOW).add(D1YellowDRStudent6);
        diningRoomStudentsD1.get(Color.YELLOW).add(D1YellowDRStudent7);
        diningRoomStudentsD1.get(Color.YELLOW).add(D1YellowDRStudent8);
        diningRoomStudentsD1.get(Color.YELLOW).add(D1YellowDRStudent9);
        diningRoomStudentsD1.get(Color.YELLOW).add(D1YellowDRStudent10);
        //Pink students
        diningRoomStudentsD1.put(Color.PINK, new ArrayList<>());
        diningRoomStudentsD1.get(Color.PINK).add(D1PinkDRStudent1);
        diningRoomStudentsD1.get(Color.PINK).add(D1PinkDRStudent2);
        diningRoomStudentsD1.get(Color.PINK).add(D1PinkDRStudent3);
        diningRoomStudentsD1.get(Color.PINK).add(D1PinkDRStudent4);
        diningRoomStudentsD1.get(Color.PINK).add(D1PinkDRStudent5);
        diningRoomStudentsD1.get(Color.PINK).add(D1PinkDRStudent6);
        diningRoomStudentsD1.get(Color.PINK).add(D1PinkDRStudent7);
        diningRoomStudentsD1.get(Color.PINK).add(D1PinkDRStudent8);
        diningRoomStudentsD1.get(Color.PINK).add(D1PinkDRStudent9);
        diningRoomStudentsD1.get(Color.PINK).add(D1PinkDRStudent10);
        //Blue students
        diningRoomStudentsD1.put(Color.BLUE, new ArrayList<>());
        diningRoomStudentsD1.get(Color.BLUE).add(D1BlueDRStudent1);
        diningRoomStudentsD1.get(Color.BLUE).add(D1BlueDRStudent2);
        diningRoomStudentsD1.get(Color.BLUE).add(D1BlueDRStudent3);
        diningRoomStudentsD1.get(Color.BLUE).add(D1BlueDRStudent4);
        diningRoomStudentsD1.get(Color.BLUE).add(D1BlueDRStudent5);
        diningRoomStudentsD1.get(Color.BLUE).add(D1BlueDRStudent6);
        diningRoomStudentsD1.get(Color.BLUE).add(D1BlueDRStudent7);
        diningRoomStudentsD1.get(Color.BLUE).add(D1BlueDRStudent8);
        diningRoomStudentsD1.get(Color.BLUE).add(D1BlueDRStudent9);
        diningRoomStudentsD1.get(Color.BLUE).add(D1BlueDRStudent10);

        for ( Color c: Color.values() ) {
            for ( ImageView i : diningRoomStudentsD1.get(c) )
                i.setVisible(false);
        }
    }


    //ZAMBO END

}