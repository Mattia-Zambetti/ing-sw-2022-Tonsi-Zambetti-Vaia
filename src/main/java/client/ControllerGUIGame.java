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
