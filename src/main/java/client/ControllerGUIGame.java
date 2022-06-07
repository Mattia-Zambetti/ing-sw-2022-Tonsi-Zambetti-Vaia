package client;

import controller.choice.Choice;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private static Map<Integer,String> figureCardsMap=new HashMap<>(){{
        put(1, "Centaur.jpg" );
        put(2, "Jester.jpg" );
        put(3, "Knight.jpg" );
        put(4, "Merchant.jpg" );
        put(5, "MushroomCollector.jpg" );
        put(6, "Postman.jpg" );
        put(7, "Princess.jpg" );
        put(8, "GrannyGrass.jpg" );
        put(9, "Thief.jpg" );
        put(10, "Farmer.png" );
        put(11, "Herald.jpg" );
        put(12, "Minstrel.jpg" );
    }};

    public void updateMatchView() {
        if (client.isMatchCompletelyCreated()) {
            MatchDataInterface match = client.getMatchView();
            if(match instanceof ExpertMatch) {
                List<FigureCard> figureCards = match.showFigureCardsInGame();
                figureCard1.setImage(new Image(figureCardsMap.get(figureCards.get(0).getCardId())));
                figureCard2.setImage(new Image(figureCardsMap.get(figureCards.get(1).getCardId())));
                figureCard3.setImage(new Image(figureCardsMap.get(figureCards.get(2).getCardId())));
            }else {
                figureCard1.setVisible(false);
                figureCard2.setVisible(false);
                figureCard3.setVisible(false);
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

    }
}
