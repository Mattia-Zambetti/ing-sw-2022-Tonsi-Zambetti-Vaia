package client;

import controller.choice.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Card;
import model.ExpertMatch;
import model.MatchDataInterface;
import model.*;
import model.exception.WrongColorException;
import model.figureCards.FigureCard;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControllerGUIGame extends ControllerGUIInterface implements Initializable {

    /**DataPlayer:*/
    @FXML
    private ImageView avatarTower;
    @FXML
    private ImageView wizardAvatar;
    @FXML
    private Text nickname;
    @FXML
    private AnchorPane avatarPane;

    //Dashboards:
    @FXML
    private AnchorPane Dashboard1;
    @FXML
    private AnchorPane Dashboard2;
    @FXML
    private AnchorPane Dashboard3;
    @FXML
    private AnchorPane Dashboard4;

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

    @FXML
    private Text hint;
    @FXML
    private AnchorPane hintBox;

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

    private Map<Color, ImageView> masterD1; //TODO initialize
    private Map<Integer, ImageView> towerD1; //TODO initialize

    private Map<String, DashboardView> playersDashboardView = new HashMap<>();


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

    private static Map<Color, Image> studentsImage= new HashMap<>(){{
        put(Color.GREEN, new Image(getClass().getResourceAsStream("/client/Images/GreenStudent.png" )));
        put(Color.BLUE, new Image(getClass().getResourceAsStream("/client/Images/BlueStudent.png" )));
        put(Color.PINK, new Image(getClass().getResourceAsStream("/client/Images/PinkStudent.png" )));
        put(Color.RED, new Image(getClass().getResourceAsStream("/client/Images/RedStudent.png" )));
        put(Color.YELLOW, new Image(getClass().getResourceAsStream("/client/Images/YellowStudent.png")));
    }};

    private static Map<TowerColor, Image> towersImage= new HashMap<>(){{
        put(TowerColor.BLACK, new Image(getClass().getResourceAsStream("/client/Images/BlackTower.png" )));
        put(TowerColor.GREY, new Image(getClass().getResourceAsStream("/client/Images/GreyTower.png" )));
        put(TowerColor.WHITE, new Image(getClass().getResourceAsStream("/client/Images/WhiteTower.png" )));
    }};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**Data player*/
        nickname.setText(client.getPlayer().getNickname().toUpperCase());
        nickname.setFont(Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),24));
        if(client.getMatchView().showAllPlayers().size()<4)
            avatarPane.setVisible(true);
        wizardAvatar.setImage(fromIntToWizard.get(ClientJavaFX.wizardThisPlayer));
        avatarTower.setImage(fromIntToTower.get(ClientJavaFX.towerColorThisPlayer));


        /**Cards:*/
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

        /**Hint box:*/
        hint.setFont(Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),14));
        showAllowedCommandKey();
        hintBox.setVisible(false);

    }

    public void showAllowedCommandKey(){
        hint.setText("You can press c to show/hide your cards");
        if(client.getMatchView().showAllPlayers().size()>=3)
            hint.setText("You can press c to show/hide your cards or d to show/hide the enemy dashboards");
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
        hintBox.setVisible(false);
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
        hint.setText(s);
    }


    /**Commands from the scene*/

    public void submitCardValue(Event event){
        if(client.getActualToDoChoice() instanceof CardChoice && client.isChoiceTime()) {
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
        hintBox.setVisible(true);
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
        if(((ImageView)event.getSource()).getParent().equals(boxCards)) {
            ((ImageView) event.getSource()).setFitHeight(((ImageView) event.getSource()).getFitHeight() * 1.1);
            ((ImageView) event.getSource()).setFitWidth(((ImageView) event.getSource()).getFitWidth() * 1.1);
        }
    }

    public void zoomCardOnExit(Event event){
        if(((ImageView)event.getSource()).getParent().equals(boxCards)) {
            ((ImageView) event.getSource()).setFitHeight(((ImageView) event.getSource()).getFitHeight() / 1.1);
            ((ImageView) event.getSource()).setFitWidth(((ImageView) event.getSource()).getFitWidth() / 1.1);
        }
    }

    @FXML
    public void keyPressedManager(KeyEvent e){
        System.out.println("c");
        if(e.getCode()==KeyCode.C){
            boxCards.setVisible(!boxCards.isVisible());
        }

        if(e.getCode()==KeyCode.D){
            if(client.getMatchView().showAllPlayers().size()>=3){
                Dashboard3.setVisible(!Dashboard3.isVisible());
                if(client.getMatchView().showAllCurrentCards().size()<3)
                    cardDb3.setVisible(!cardDb3.isVisible());
                if(client.getMatchView().showAllPlayers().size()>=4){
                    Dashboard4.setVisible(!Dashboard4.isVisible());
                    avatarPane.setVisible(!avatarPane.isVisible());
                    if(client.getMatchView().showAllCurrentCards().size()<4)
                        cardDb4.setVisible(!cardDb4.isVisible());
                }


            }

        }
    }

    //ZAMBO

    public void chooseStudent(MouseEvent event) {
        if ( event.getSource() instanceof ImageView ) {
            ImageView chosenStudent = (ImageView) event.getSource();
            if (client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 0) {
                client.getActualToDoChoice().setChoiceParam(String.valueOf(playersDashboardView.get(client.getPlayer().getNickname()).entranceStudents.indexOf(chosenStudent)+1));
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

    public void chooseGreenDR(MouseEvent event) {
        if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
            client.getActualToDoChoice().setChoiceParam("1");
        }
        synchronized ( client.getOutputStreamLock() ) {
            client.getOutputStreamLock().notifyAll();
        }
    }

    public void chooseRedDR(MouseEvent event) {
        if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
            client.getActualToDoChoice().setChoiceParam("1");
        }
        synchronized ( client.getOutputStreamLock() ) {
            client.getOutputStreamLock().notifyAll();
        }
    }

    public void chooseYellowDR(MouseEvent event) {
        if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
            client.getActualToDoChoice().setChoiceParam("1");
        }
        synchronized ( client.getOutputStreamLock() ) {
            client.getOutputStreamLock().notifyAll();
        }
    }

    public void choosePinkDR(MouseEvent event) {
        if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
            client.getActualToDoChoice().setChoiceParam("1");
        }
        synchronized ( client.getOutputStreamLock() ) {
            client.getOutputStreamLock().notifyAll();
        }
    }

    /** Method called when the BlueDR has been clicked.
     *  The first statement of the "if" it's called when the player move a student from the entrance.
     *  The second one when a figureCard is played (DA FARE)
     *
      * @param event
     */
    public void chooseBlueDR(MouseEvent event) {
        if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
            client.getActualToDoChoice().setChoiceParam("1");
        }
        synchronized ( client.getOutputStreamLock() ) {
            client.getOutputStreamLock().notifyAll();
        }
    }

    /**Puts all ImageView of entrance students into an ArrayList
     */
    private ArrayList<ImageView> initializeEntranceStudentsD1() {
        ArrayList<ImageView> entranceStudentsD1;
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
        return entranceStudentsD1;
    }

    /**Puts all ImageView of DR students into an ArrayList and set them invisible
     */
    private Map<Color, ArrayList<ImageView>> initializeDiningRoomStudentsD1() {
        Map<Color, ArrayList<ImageView>> diningRoomStudentsD1;
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

        return diningRoomStudentsD1;
    }

    /**Puts all ImageView of masters into a Map and set them invisible
     */
    private Map<Color, ImageView> initializeMastersD1() {
        Map<Color, ImageView> masters = new HashMap<>();

        masters.put(Color.GREEN, D1GreenMaster);
        masters.put(Color.RED, D1RedMaster);
        masters.put(Color.YELLOW, D1YellowMaster);
        masters.put(Color.PINK, D1PinkMaster);
        masters.put(Color.BLUE, D1BlueMaster);

        D1GreenMaster.setVisible(false);
        D1RedMaster.setVisible(false);
        D1YellowMaster.setVisible(false);
        D1PinkMaster.setVisible(false);
        D1BlueMaster.setVisible(false);

        return masters;
    }

    public void inizializeAllDasboards( Player clientPlayer, List<Player> otherPlayers ) {

        DashboardView clientDashboard = new DashboardView(initializeEntranceStudentsD1(), initializeDiningRoomStudentsD1(), initializeMastersD1(), null);

        playersDashboardView.put(clientPlayer.getNickname(), clientDashboard);

        //TODO altre dashboard, master e torri della prima
    }

    /**Update all dashboards in the GUI
     */
    public void updateDashboard() {
        Map<String, Dashboard> dashboardsData = client.getMatchView().showAllDashboards();
        for ( String playerNickname: playersDashboardView.keySet() ) {
            DashboardView currentDashboardView=playersDashboardView.get(playerNickname);
            Dashboard currentDashboardData = dashboardsData.get(playerNickname);

            //Update students in entrance
            int i = 0;
            for ( Student s: currentDashboardData.showEntrance() ) {
                currentDashboardView.setEntranceStudentVisible(i, true);
                currentDashboardView.setEntranceStudentColor(i, s.getColor());
                i++;
            }
            while ( i<9 ) {
                currentDashboardView.setEntranceStudentVisible(i, false);
                i++;
            }

            //Update students in dining room
            for ( Color c: Color.values() ) {
                try {
                    currentDashboardView.setDRStudentsNumber(currentDashboardData.getStudentsNumInDR(c),c);
                } catch (WrongColorException e) {
                    e.printStackTrace();
                }
            }

            //Update masters
            for ( Color c: Color.values() ) {
                if ( currentDashboardData.getMastersList().contains(new Master(c)) )
                    currentDashboardView.setMasterVisible(c, true);
                else
                    currentDashboardView.setMasterVisible(c, false);
            }

        }

    }

    private class DashboardView {
        private ArrayList<ImageView> entranceStudents;
        private Map<Color, ArrayList<ImageView>> diningRoomStudents;
        private Map<Color, ImageView> master;
        private Map<Integer, ImageView> tower;



        public DashboardView(ArrayList<ImageView> entranceStudents, Map<Color, ArrayList<ImageView>> diningRoomStudents, Map<Color, ImageView> master, Map<Integer, ImageView> tower ) {
            this.entranceStudents=entranceStudents;
            this.diningRoomStudents=diningRoomStudents;
            this.master=master;
            this.tower=tower;
        }

        public void setEntranceStudentColor(int studentNumber, Color studentColor) {
            entranceStudents.get(studentNumber).setImage(studentsImage.get(studentColor));
        }

        public void setEntranceStudentVisible(int studentNumber, boolean visible) {
            entranceStudents.get(studentNumber).setVisible(visible);
        }

        public void setDRStudentsNumber(int studentsNumber, Color drColor) {
            int i;
            for ( i=0; i<studentsNumber; i++ ) {
                diningRoomStudents.get(drColor).get(i).setVisible(true);
            }
            while (i<10) {
                diningRoomStudents.get(drColor).get(i).setVisible(false);
                i++;
            }
        }

        public void setMasterVisible(Color masterColor, boolean visible) {
            master.get(masterColor).setVisible(visible);
        }

    }

    public void updateGameView() {
        updateDashboard();

    }

    //ZAMBO END

}