package client;

import controller.choice.CardChoice;
import controller.choice.Choice;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private ImageView figureCard1;
    @FXML
    private ImageView figureCard2;
    @FXML
    private ImageView figureCard3;

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


    public void updateFigureCards() {
        if (client.isMatchCompletelyCreated()) {
            MatchDataInterface match = client.getMatchView();
            if(match instanceof ExpertMatch) {
                List<FigureCard> figureCards = match.showFigureCardsInGame();

                figureCard1.setImage(figureCardsMap.get(figureCards.get(0).getCardId()));
                figureCard2.setImage(figureCardsMap.get(figureCards.get(1).getCardId()));
                figureCard3.setImage(figureCardsMap.get(figureCards.get(2).getCardId()));
            }else {
                figureCard1.setVisible(false);
                figureCard2.setVisible(false);
                figureCard3.setVisible(false);
            }
        }
    }

    public void setInvisibleCards(){
        for (ImageView c: fromCardsToImages.values()) {
            c.setVisible(false);
        }
    }

    public void updateCardsView(){
        if(client.getActualToDoChoice() instanceof CardChoice && client.isChoiceTime()){
            for (Card c:client.getMatchView().showCurrentPlayerDashboard().showCards()){
                if(fromCardsToImages.containsKey(c)){
                    fromCardsToImages.get(c).setVisible(true);
                }else{
                    fromCardsToImages.get(c).setVisible(false);
                }
            }
        }else{
            for (ImageView c: fromCardsToImages.values()) {
                c.setVisible(false);
            }
        }
    }

    @Override
    public void switchScene(Choice choice) throws IOException {

    }

    @Override
    public void printMessageText(String s) {

    }

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

    }

    public void submitCardValue(Event event){
        if(client.getActualToDoChoice() instanceof CardChoice) {
            if (fromCardsToImages.containsValue((ImageView) event.getSource())) {
                client.getActualToDoChoice().setChoiceParam(""+fromImagesToCards.get(((ImageView) event.getSource())).getId());
                setInvisibleCards();
                synchronized ( client.getOutputStreamLock() ) {
                    client.getOutputStreamLock().notifyAll();
                }
            }
        }
    }
}
