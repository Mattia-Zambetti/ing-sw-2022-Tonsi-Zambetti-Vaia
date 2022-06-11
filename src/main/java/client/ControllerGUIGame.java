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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Card;
import model.ExpertMatch;
import model.MatchDataInterface;
import model.figureCards.FigureCard;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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

    private Map<Card, ImageView> fromCardsToImages;

    private Map<ImageView, Card> fromImagesToCards;

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
}