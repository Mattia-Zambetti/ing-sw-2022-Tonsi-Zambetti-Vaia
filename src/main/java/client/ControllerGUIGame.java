package client;

import controller.choice.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.*;
import model.exception.NoIslandException;
import model.exception.NoTowerException;
import model.exception.WrongColorException;
import model.figureCards.FigureCard;
import model.figureCards.FigureCardWithStudents;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControllerGUIGame extends ControllerGUIInterface implements Initializable {

    /**choice phase:*/
    @FXML
    private Text choicePhaseMessage;

    //in zoom methods:
    private boolean zoomCard=false;
    private boolean zoomFigureCard=false;

    /**messages:*/

    @FXML
    private Text playerTurnMessage;

    @FXML
    private Rectangle rectangleMessage;

    @FXML
    private Text hint;

    /**DataPlayer:*/
    @FXML
    private ImageView avatarTower;
    @FXML
    private ImageView wizardAvatar;
    @FXML
    private Text nickname;
    @FXML
    private AnchorPane avatarPane;
    @FXML
    private ImageView avatarFigureCard;


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

    protected Map<ImageView, Integer> fromFigureCardsToInteger;

    @FXML
    private ImageView coinImg;
    @FXML
    private Label CoinNumber;


    @FXML
    private ImageView FC1Student1;
    @FXML
    private ImageView FC1Student2;
    @FXML
    private ImageView FC1Student3;
    @FXML
    private ImageView FC1Student4;
    @FXML
    private ImageView FC1Student5;
    @FXML
    private ImageView FC1Student6;

    @FXML
    private ImageView FC2Student1;
    @FXML
    private ImageView FC2Student2;
    @FXML
    private ImageView FC2Student3;
    @FXML
    private ImageView FC2Student4;
    @FXML
    private ImageView FC2Student5;
    @FXML
    private ImageView FC2Student6;

    @FXML
    private ImageView FC3Student1;
    @FXML
    private ImageView FC3Student2;
    @FXML
    private ImageView FC3Student3;
    @FXML
    private ImageView FC3Student4;
    @FXML
    private ImageView FC3Student5;
    @FXML
    private ImageView FC3Student6;

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

    //students on islands


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

    //Students on islands

    @FXML
    private  Text numBStudentIsl1;

    @FXML
    private Text numBStudentIsl10;

    @FXML
    private Text numBStudentIsl11;

    @FXML
    private Text numBStudentIsl12;

    @FXML
    private Text numBStudentIsl2;

    @FXML
    private Text numBStudentIsl3;

    @FXML
    private Text numBStudentIsl4;

    @FXML
    private Text numBStudentIsl5;

    @FXML
    private Text numBStudentIsl6;

    @FXML
    private Text numBStudentIsl7;

    @FXML
    private Text numBStudentIsl8;

    @FXML
    private Text numBStudentIsl9;

    @FXML
    private  Text numGStudentIsl1;

    @FXML
    private Text numGStudentIsl10;

    @FXML
    private Text numGStudentIsl11;

    @FXML
    private Text numGStudentIsl12;

    @FXML
    private Text numGStudentIsl2;

    @FXML
    private Text numGStudentIsl3;

    @FXML
    private Text numGStudentIsl4;

    @FXML
    private Text numGStudentIsl5;

    @FXML
    private Text numGStudentIsl6;

    @FXML
    private Text numGStudentIsl7;

    @FXML
    private Text numGStudentIsl8;

    @FXML
    private Text numGStudentIsl9;

    @FXML
    private Text numPStudentIsl1;

    @FXML
    private Text numPStudentIsl10;

    @FXML
    private Text numPStudentIsl11;

    @FXML
    private Text numPStudentIsl12;

    @FXML
    private Text numPStudentIsl2;

    @FXML
    private Text numPStudentIsl3;

    @FXML
    private Text numPStudentIsl4;

    @FXML
    private Text numPStudentIsl5;

    @FXML
    private Text numPStudentIsl6;

    @FXML
    private Text nuPStudentIsl8;

    @FXML
    private Text numPStudentIsl9;

    @FXML
    private Text numRStudentIsl1;

    @FXML
    private Text numRStudentIsl10;

    @FXML
    private Text numRStudentIsl11;

    @FXML
    private Text numRStudentIsl12;

    @FXML
    private Text numRStudentIsl2;

    @FXML
    private Text numRStudentIsl3;

    @FXML
    private Text numRStudentIsl4;

    @FXML
    private Text numRStudentIsl5;

    @FXML
    private Text numRStudentIsl6;

    @FXML
    private Text numRStudentIsl7;

    @FXML
    private Text numRStudentIsl8;

    @FXML
    private Text numRStudentIsl9;

    @FXML
    private Text numYStudentIsl1;

    @FXML
    private Text numYStudentIsl10;

    @FXML
    private Text numYStudentIsl11;

    @FXML
    private Text numYStudentIsl12;

    @FXML
    private Text numYStudentIsl2;

    @FXML
    private Text numYStudentIsl3;

    @FXML
    private Text numYStudentIsl4;

    @FXML
    private Text numPStudentIsl7;

    @FXML
    private Text numYStudentIsl5;

    @FXML
    private Text numYStudentIsl6;

    @FXML
    private Text numYStudentIsl7;

    @FXML
    private Text numYStudentIsl8;

    @FXML
    private Text numYStudentIsl9;

    //Towers on island

    @FXML
    private ImageView BlackTower1;

    @FXML
    private ImageView BlackTower11;

    @FXML
    private ImageView BlackTower12;

    @FXML
    private ImageView BlackTower2;

    @FXML
    private ImageView BlackTower3;

    @FXML
    private ImageView BlackTower4;

    @FXML
    private ImageView BlackTower5;

    @FXML
    private ImageView BlackTower6;

    @FXML
    private ImageView BlackTower7;

    @FXML
    private ImageView BlackTower8;

    @FXML
    private ImageView BlackTower9;

    @FXML
    private ImageView BlackTower10;

    @FXML
    private Text towerNum1;

    @FXML
    private Text towerNum10;

    @FXML
    private Text towerNum11;

    @FXML
    private Text towerNum12;

    @FXML
    private Text towerNum2;

    @FXML
    private Text towerNum3;

    @FXML
    private Text towerNum4;

    @FXML
    private Text towerNum6;

    @FXML
    private Text towerNum5;

    @FXML
    private Text towerNum7;

    @FXML
    private Text towerNum8;

    @FXML
    private Text towerNum9;

    //motherNature

    @FXML
    private ImageView motherNature1;

    @FXML
    private ImageView motherNature10;

    @FXML
    private ImageView motherNature11;

    @FXML
    private ImageView motherNature12;

    @FXML
    private ImageView motherNature2;

    @FXML
    private ImageView motherNature3;

    @FXML
    private ImageView motherNature4;

    @FXML
    private ImageView motherNature5;

    @FXML
    private ImageView motherNature6;

    @FXML
    private ImageView motherNature7;

    @FXML
    private ImageView motherNature8;

    @FXML
    private ImageView motherNature9;

    //clouds

    @FXML
    private ImageView cloud1;

    @FXML
    private ImageView cloud2;

    @FXML
    private ImageView cloud3;

    @FXML
    private ImageView cloud4;

    @FXML
    private ImageView student1OnCloud1;

    @FXML
    private ImageView student1OnCloud2;

    @FXML
    private ImageView student1OnCloud3;

    @FXML
    private ImageView student1OnCloud4;

    @FXML
    private ImageView student2OnCloud1;

    @FXML
    private ImageView student2OnCloud2;

    @FXML
    private ImageView student2OnCloud3;

    @FXML
    private ImageView student2OnCloud4;

    @FXML
    private ImageView student3OnCloud1;

    @FXML
    private ImageView student3OnCloud3;

    @FXML
    private ImageView student3OnCloud2;

    @FXML
    private ImageView student3OnCloud4;

    @FXML
    private Region cloudRegion1;

    @FXML
    private Region cloudRegion2;

    @FXML
    private Region cloudRegion3;

    @FXML
    private Region cloudRegion4;

    @FXML
    private GridPane CloudsPane;



    //END

    private Map<Card, ImageView> fromCardsToImages;

    private Map<ImageView, Card> fromImagesToCards;

    private Map<Color, ImageView> masterD1; //TODO initialize
    private Map<Integer, ImageView> towerD1; //TODO initialize

    private Map<Color, Text> island1NumStudents;

    private Map<Color, Text> island2NumStudents;

    private Map<Color, Text> island3NumStudents;

    private Map<Color, Text> island4NumStudents;

    private Map<Color, Text> island5NumStudents;

    private Map<Color, Text> island6NumStudents;

    private Map<Color, Text> island7NumStudents;

    private Map<Color, Text> island8NumStudents;

    private Map<Color, Text> island9NumStudents;

    private Map<Color, Text> island10NumStudents;

    private Map<Color, Text> island11NumStudents;

    private Map<Color, Text> island12NumStudents;

    private List<Map<Color, Text>> numStudentsOnIsland;

    private List<ImageView> towerOnIslands;

    private List<Text> towerNumOnIslands;

    private List<ImageView> motherNatureList;

    private Map<String, DashboardView> playersDashboardView = new HashMap<>();

    private List<ImageView> clouds;

    private Map<Integer,List<ImageView>> studentsOcClouds;


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
        /**font messages:*/
       // choicePhaseMessage.setFont(Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),20));
        hint.setFont(Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),14));


        /**Data player*/
        nickname.setText(client.getPlayer().getNickname().toUpperCase());
        nickname.setFont(Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),24));
        if(client.getMatchView().showAllPlayers().size()<4)
            avatarPane.setVisible(true);
        if(client.getMatchView().showAllPlayers().size()<3){
            choicePhaseMessage.setVisible(true);
            hint.setVisible(true);
            rectangleMessage.setVisible(true);
        }
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
       // choicePhaseMessage.setFont(Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),14));
        showAllowedCommandKey();
        hintBox.setVisible(false);

        /**Figure cards:*/
        fromFigureCardsToInteger= new HashMap<>(){{
            put(figureCard1,1);
            put(figureCard2,2);
            put(figureCard3,3);
        }};


        initializeIsland();

    }

    private void initializeIsland() {
        island1NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl1);
            put(Color.YELLOW,numYStudentIsl1);
            put(Color.BLUE,numBStudentIsl1);
            put(Color.PINK,numPStudentIsl1);
            put(Color.RED,numRStudentIsl1);
        }};

        island2NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl2);
            put(Color.YELLOW,numYStudentIsl2);
            put(Color.BLUE,numBStudentIsl2);
            put(Color.PINK,numPStudentIsl2);
            put(Color.RED,numRStudentIsl2);
        }};

        island3NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl3);
            put(Color.YELLOW,numYStudentIsl3);
            put(Color.BLUE,numBStudentIsl3);
            put(Color.PINK,numPStudentIsl3);
            put(Color.RED,numRStudentIsl3);
        }};

        island4NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl4);
            put(Color.YELLOW,numYStudentIsl4);
            put(Color.BLUE,numBStudentIsl4);
            put(Color.PINK,numPStudentIsl4);
            put(Color.RED,numRStudentIsl4);
        }};

        island5NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl5);
            put(Color.YELLOW,numYStudentIsl5);
            put(Color.BLUE,numBStudentIsl5);
            put(Color.PINK,numPStudentIsl5);
            put(Color.RED,numRStudentIsl5);
        }};

        island6NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl6);
            put(Color.YELLOW,numYStudentIsl6);
            put(Color.BLUE,numBStudentIsl6);
            put(Color.PINK,numPStudentIsl6);
            put(Color.RED,numRStudentIsl6);
        }};

        island7NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl7);
            put(Color.YELLOW,numYStudentIsl7);
            put(Color.BLUE,numBStudentIsl7);
            put(Color.PINK,numPStudentIsl7);
            put(Color.RED,numRStudentIsl7);
        }};

        island8NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl8);
            put(Color.YELLOW,numYStudentIsl8);
            put(Color.BLUE,numBStudentIsl8);
            put(Color.PINK,nuPStudentIsl8);
            put(Color.RED,numRStudentIsl8);
        }};

        island9NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl9);
            put(Color.YELLOW,numYStudentIsl9);
            put(Color.BLUE,numBStudentIsl9);
            put(Color.PINK,numPStudentIsl9);
            put(Color.RED,numRStudentIsl9);
        }};

        island10NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl10);
            put(Color.YELLOW,numYStudentIsl10);
            put(Color.BLUE,numBStudentIsl10);
            put(Color.PINK,numPStudentIsl10);
            put(Color.RED,numRStudentIsl10);
        }};

        island11NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl11);
            put(Color.YELLOW,numYStudentIsl11);
            put(Color.BLUE,numBStudentIsl11);
            put(Color.PINK,numPStudentIsl11);
            put(Color.RED,numRStudentIsl11);
        }};

        island12NumStudents = new HashMap<>(){{
            put(Color.GREEN,numGStudentIsl12);
            put(Color.YELLOW,numYStudentIsl12);
            put(Color.BLUE,numBStudentIsl12);
            put(Color.PINK,numPStudentIsl12);
            put(Color.RED,numRStudentIsl12);
        }};

        numStudentsOnIsland = new ArrayList<>(){{
            add(island1NumStudents);add(island2NumStudents);add(island3NumStudents);
            add(island4NumStudents);add(island5NumStudents);add(island6NumStudents);
            add(island7NumStudents);add(island8NumStudents);add(island9NumStudents);add(island10NumStudents);
            add(island11NumStudents);add(island12NumStudents);
        }};

        towerOnIslands = new ArrayList<>(){{
            add(BlackTower1);
            add(BlackTower2);
            add(BlackTower3);
            add(BlackTower4);
            add(BlackTower5);
            add(BlackTower6);
            add(BlackTower7);
            add(BlackTower8);
            add(BlackTower9);
            add(BlackTower10);
            add(BlackTower11);
            add(BlackTower12);
        }};

        towerNumOnIslands = new ArrayList<>(){{
            add(towerNum1);
            add(towerNum2);
            add(towerNum3);
            add(towerNum4);
            add(towerNum5);
            add(towerNum6);
            add(towerNum7);
            add(towerNum8);
            add(towerNum9);
            add(towerNum10);
            add(towerNum11);
            add(towerNum12);
        }};

        motherNatureList = new ArrayList<>(){{
            add(motherNature1);
            add(motherNature2);
            add(motherNature3);
            add(motherNature4);
            add(motherNature5);
            add(motherNature6);
            add(motherNature7);
            add(motherNature8);
            add(motherNature9);
            add(motherNature10);
            add(motherNature11);
            add(motherNature12);
        }};

        clouds = new ArrayList<>(){{
            add(cloud1);
            add(cloud2);
            add(cloud3);
            add(cloud4);
        }};

        studentsOcClouds = new HashMap<>(){{
            put(0,new ArrayList<>(){{add(student1OnCloud1);add(student2OnCloud1);add(student3OnCloud1);}});
            put(1,new ArrayList<>(){{add(student1OnCloud2);add(student2OnCloud2);add(student3OnCloud2);}});
            put(2,new ArrayList<>(){{add(student1OnCloud3);add(student2OnCloud3);add(student3OnCloud3);}});
            put(3,new ArrayList<>(){{add(student1OnCloud4);add(student2OnCloud4);add(student3OnCloud4);}});
        }};
    }

    public void showAllowedCommandKey(){
        hint.setText("You can press c to show/hide your cards");
        if(client.getMatchView().showAllPlayers().size()>=3)
            hint.setText("You can press c to show/hide your cards or d to show/hide the enemy dashboards");
    }

    public void addStudentsOnFigureCard(ImageView figureCard1){

    }


    /**updates*/
    public void updateFigureCards() {
        if (client.getPlayer().equals(client.getMatchView().showCurrentPlayer())) {
            CoinNumber.setText("" + client.getMatchView().showCurrentPlayerDashboard().getCoinsNumber());
            if (client.getMatchView().getChoice() instanceof FigureCardActionChoice
                    || client.getMatchView().showCurrentPlayerDashboard().hasKnightPrivilege()
                    || client.getMatchView().showCurrentPlayerDashboard().isFarmerEffect()
                    || client.getMatchView().isCentaurEffect()
                    || client.getMatchView().isPostManValue()) {
                avatarFigureCard.setVisible(true);
                client.setFigureCardNotPlayed(false);

                Media media=new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
                MediaPlayer playBeep=new MediaPlayer(media);
                playBeep.play();
            }
        }
    }

    public void updateInitialMatchView() {

        List<ImageView> studentsOnFigureCards;
        if (client.isMatchCompletelyCreated()) {
            MatchDataInterface match = client.getMatchView();

            //figure cards management and coins management:
            if(match instanceof ExpertMatch) {
                List<FigureCard> figureCards = match.showFigureCardsInGame();

                figureCard1.setImage(figureCardsMap.get(figureCards.get(0).getCardId()));
                if(figureCards.get(0) instanceof FigureCardWithStudents){
                    studentsOnFigureCards=new ArrayList<>(){{
                        add(FC1Student1);
                        add(FC1Student2);
                        add(FC1Student3);
                        add(FC1Student4);
                        add(FC1Student5);
                        add(FC1Student6);
                    }};
                    int i=0;
                    for (Student s: ((FigureCardWithStudents) figureCards.get(1)).getStudentsOnCard()) {
                        studentsOnFigureCards.get(i).setImage(studentsImage.get(s.getColor()));
                        studentsOnFigureCards.get(i).setVisible(true);
                        i++;
                    }
                }


                figureCard2.setImage(figureCardsMap.get(figureCards.get(1).getCardId()));
                if(figureCards.get(1) instanceof FigureCardWithStudents){
                    studentsOnFigureCards=new ArrayList<>(){{
                        add(FC2Student1);
                        add(FC2Student2);
                        add(FC2Student3);
                        add(FC2Student4);
                        add(FC2Student5);
                        add(FC2Student6);
                    }};
                    int i=0;
                    for (Student s: ((FigureCardWithStudents) figureCards.get(0)).getStudentsOnCard()) {
                        studentsOnFigureCards.get(i).setImage(studentsImage.get(s.getColor()));
                        studentsOnFigureCards.get(i).setVisible(true);
                        i++;
                    }
                }


                figureCard3.setImage(figureCardsMap.get(figureCards.get(2).getCardId()));
                if(figureCards.get(2) instanceof FigureCardWithStudents){
                    studentsOnFigureCards=new ArrayList<>(){{
                        add(FC3Student1);
                        add(FC3Student2);
                        add(FC3Student3);
                        add(FC3Student4);
                        add(FC3Student5);
                        add(FC3Student6);
                    }};
                    int i=0;
                    for (Student s: ((FigureCardWithStudents) figureCards.get(2)).getStudentsOnCard()) {
                        studentsOnFigureCards.get(i).setImage(studentsImage.get(s.getColor()));
                        studentsOnFigureCards.get(i).setVisible(true);
                        i++;
                    }
                }
            }else {
                expertMatchPane.setVisible(false);
            }

        }
    }

    public void updateMessagePhase(){
        if(client.getMatchView().showCurrentPlayer().equals(client.getPlayer())){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    playerTurnMessage.setText("It's your turn");
                }
            });
        }

        else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    playerTurnMessage.setText("It's not your turn, ");

                }
            });
        }
        String tmp = client.getMatchView().getChoice().whichChoicePhase();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                choicePhaseMessage.setText(tmp);
            }
        });
    }


    public void updateCardsPlayerView(){
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
        }else{
            for (ImageView c: fromCardsToImages.values()) {
                c.setVisible(false);
            }
        }
    }

    public void updateCurrentCards(){
        for (Card c: client.getMatchView().showAllCurrentCards()) {
            if(!c.equals(new Card(0,0,0))){
                playersDashboardView.get(client.getMatchView().getPlayerByCurrentCard(c).getNickname()).getCurrentCard().setImage(fromCardsToImages.get(c).getImage());
                playersDashboardView.get(client.getMatchView().getPlayerByCurrentCard(c).getNickname()).getCurrentCard().setVisible(true);
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                    if (client.getActualToDoChoice() instanceof CardChoice && client.isChoiceTime()) {
                        if (fromCardsToImages.containsValue((ImageView) event.getSource())) {
                            Media media=new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
                            MediaPlayer playBeep=new MediaPlayer(media);
                            playBeep.play();

                            client.getActualToDoChoice().setChoiceParam("" + fromImagesToCards.get(((ImageView) event.getSource())).getId());
                            cardDb1.setVisible(true);
                            cardDb1.setImage(((ImageView) event.getSource()).getImage());
                            ((ImageView) event.getSource()).setVisible(false);
                            setInvisibleCards();

                            synchronized (client.getOutputStreamLock()) {
                                client.getOutputStreamLock().notifyAll();
                            }
                        }
                    }
                    hintBox.setVisible(true);
                }

        });



    }

    public void submitFigureCardValue(Event event){
        if(client.getMatchView() instanceof ExpertMatch
                && !(client.getActualToDoChoice() instanceof FigureCardActionChoice)
                && !(client.getActualToDoChoice() instanceof CardChoice)
                && !(client.getActualToDoChoice() instanceof DataPlayerChoice) && client.isFigureCardNotPlayed()) {
            Choice figureCardChoice = new FigureCardPlayedChoice(client.getMatchView().showFigureCardsInGame());
            client.setActualToDoChoiceQueue(client.getActualToDoChoice());
            client.setActualToDoChoice(figureCardChoice);

            avatarFigureCard.setImage(((ImageView)event.getSource()).getImage());
            figureCardChoice.setChoiceParam(""+fromFigureCardsToInteger.get(((ImageView) event.getSource())));



            synchronized (client.getOutputStreamLock()) {
                client.getOutputStreamLock().notifyAll();
            }

        }
    }

    public void zoomCardOnEnter(Event event){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(client.getMatchView().showCurrentPlayer().equals(client.getPlayer())) {
                    if (client.getMatchView().getChoice().whichChoicePhase().equals("It's the card choice phase")
                            && ((ImageView) event.getSource()).getParent().equals(boxCards)) {
                        ((ImageView) event.getSource()).setFitHeight(((ImageView) event.getSource()).getFitHeight() * 1.1);
                        ((ImageView) event.getSource()).setFitWidth(((ImageView) event.getSource()).getFitWidth() * 1.1);
                        zoomCard=true;
                    }
                }
            }
        });
    }

    public void zoomFigureCardOnEnter(Event event){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(client.getMatchView().showCurrentPlayer().equals(client.getPlayer())) {
                    if (!(client.getMatchView().getChoice() instanceof FigureCardActionChoice)
                            && !(client.getMatchView().getChoice() instanceof CardChoice)) {
                        ((ImageView) event.getSource()).setFitHeight(((ImageView) event.getSource()).getFitHeight() * 1.1);
                        ((ImageView) event.getSource()).setFitWidth(((ImageView) event.getSource()).getFitWidth() * 1.1);
                        zoomFigureCard=true;
                    }
                }
            }
        });
    }


    public void zoomCardOnExit(Event event){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(zoomCard) {
                        ((ImageView) event.getSource()).setFitHeight(((ImageView) event.getSource()).getFitHeight() / 1.1);
                        ((ImageView) event.getSource()).setFitWidth(((ImageView) event.getSource()).getFitWidth() / 1.1);
                        zoomCard=false;
                }
            }
        });
    }

    public void zoomFigureCardOnExit(Event event){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(zoomFigureCard) {
                    ((ImageView) event.getSource()).setFitHeight(((ImageView) event.getSource()).getFitHeight() / 1.1);
                    ((ImageView) event.getSource()).setFitWidth(((ImageView) event.getSource()).getFitWidth() / 1.1);
                    zoomFigureCard=false;
                }
            }
        });
    }


    @FXML
    public void keyPressedManager(KeyEvent e) {
        Task task=new Task() {
            @Override
            protected Void call() throws Exception {
                switch (e.getCode()) {
                    case C: {
                        boxCards.setVisible(!boxCards.isVisible());
                        break;
                    }
                    case D: {
                        switch (client.getMatchView().showAllPlayers().size()) {
                            case (3): {
                                Dashboard3.setVisible(!Dashboard3.isVisible());
                                rectangleMessage.setVisible(!rectangleMessage.isVisible());
                                hint.setVisible(!hint.isVisible());
                                playerTurnMessage.setVisible(!playerTurnMessage.isVisible());
                                if (client.getMatchView().showAllCurrentCards().size() < 3)
                                    cardDb3.setVisible(!cardDb3.isVisible());
                                break;
                            }
                            case (4): {
                                Dashboard3.setVisible(!Dashboard3.isVisible());
                                rectangleMessage.setVisible(!rectangleMessage.isVisible());
                                hint.setVisible(!hint.isVisible());
                                playerTurnMessage.setVisible(!playerTurnMessage.isVisible());
                                Dashboard4.setVisible(!Dashboard4.isVisible());
                                avatarPane.setVisible(!avatarPane.isVisible());
                                if (client.getMatchView().showAllCurrentCards().size() < 4)
                                    cardDb4.setVisible(!cardDb4.isVisible());
                                if (client.getMatchView().showAllCurrentCards().size() < 3)
                                    cardDb3.setVisible(!cardDb3.isVisible());
                                break;

                            }
                        }
                    }

                }
                return null;
            }
        };
        new Thread(task).start();
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

    public void chooseIsland(MouseEvent event) throws NoIslandException {
        if ( event.getSource() instanceof Region ) {
            String islandID = ((Region) event.getSource()).getId();
            if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
                System.out.println("qui");
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
                synchronized ( client.getOutputStreamLock() ) {
                    client.getOutputStreamLock().notifyAll();
                }
            }
            else if ( client.getActualToDoChoice() instanceof MoveMotherNatureChoice ) {
                int chosenIsland = 0,numToMove;
                switch(islandID) {
                    case("island1"):
                        chosenIsland = 0;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                    case("island2"):
                        chosenIsland = 1;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                    case("island3"):
                        chosenIsland = 2;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                    case("island4"):
                        chosenIsland = 3;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                    case("island5"):
                        chosenIsland = 4;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                    case("island6"):
                        chosenIsland = 5;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                    case("island7"):
                        chosenIsland = 6;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                    case("island8"):
                        chosenIsland = 7;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                    case("island9"):
                        chosenIsland = 8;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                    case("island10"):
                        chosenIsland = 9;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                    case("island11"):
                        chosenIsland = 10;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                    case("island12"):
                        chosenIsland = 11;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam(""+numToMove);
                        break;
                }
                synchronized ( client.getOutputStreamLock() ) {
                    client.getOutputStreamLock().notifyAll();
                }
            }

        }else
            throw new IllegalArgumentException("chooseIsland method called by an Object that is not an ImageView");
    }


    public void chooseCloud(MouseEvent event){
        String cloudID = ((Region) event.getSource()).getId();
        System.out.println("" + cloudID);
        if(client.getActualToDoChoice() instanceof CloudChoice){
            switch(cloudID){
                case ("cloudRegion1"):
                    client.getActualToDoChoice().setChoiceParam("0");
                    break;
                case ("cloudRegion2"):
                    client.getActualToDoChoice().setChoiceParam("1");
                    break;
                case ("cloudRegion3"):
                    client.getActualToDoChoice().setChoiceParam("2");
                    break;
                case ("cloudRegion4"):
                    client.getActualToDoChoice().setChoiceParam("3");
                    break;
            }
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }



    }

    public int getNumMovementsBasedOnChosenIsland(int chosenIsland) throws NoIslandException {
        int tmp = 0,i =client.getMatchView().getCurrentIsland();
        do{
            tmp+=1;
            i = nextIsland(i);
        }while(i!= chosenIsland);
        return tmp;
    }

    public int nextIsland(int position) throws NoIslandException { //metodo che ritorna l'indice della posizione della prosiima isola
        List<Integer> islandPositions = client.getMatchView().getIslandPositions();
        int tmp = islandPositions.indexOf(position);
        if(tmp > -1){
            if(tmp + 1 == islandPositions.size())
                return islandPositions.get(0);
            return islandPositions.get(tmp + 1);}
        else throw new NoIslandException("Island not found");
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

    /**Puts all ImageView of entrance of dashboard 1 students into an ArrayList
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

    /**Puts all ImageView of DR students of dashboard 1 into an ArrayList and set them invisible
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

    /**Puts all ImageView of masters of dashboard 1 into a Map and set them invisible
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

    /**Put all towers image of dashboard 1 in an ArrayList
     */
    private ArrayList<ImageView> initializeTowersD1( TowerColor towerColor ) {
        ArrayList<ImageView> towersList = new ArrayList<>();
        towersList.add(D1BlackTower1);
        towersList.add(D1BlackTower2);
        towersList.add(D1BlackTower3);
        towersList.add(D1BlackTower4);
        towersList.add(D1BlackTower5);
        towersList.add(D1BlackTower6);
        towersList.add(D1BlackTower7);
        towersList.add(D1BlackTower8);

        for ( ImageView tower : towersList ) {
            tower.setImage(towersImage.get(towerColor));
        }

        return towersList;
    }

    public void inizializeAllDashboards(Player clientPlayer, List<Player> otherPlayers ) {


        TowerColor playerTowerColor = client.getMatchView().showAllDashboards().get(clientPlayer.getNickname()).getTowerColor();

        DashboardView clientDashboard = new DashboardView(initializeEntranceStudentsD1(), initializeDiningRoomStudentsD1(), initializeMastersD1(), initializeTowersD1(playerTowerColor));

        playersDashboardView.put(clientPlayer.getNickname(), clientDashboard);
        playersDashboardView.get(clientPlayer.getNickname()).setCurrentCard(cardDb1);

        List<ImageView> cardOthers= new ArrayList<>(){{
            add(cardDb2);
            add(cardDb3);
            add(cardDb4);
        }};

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

            //Update towers
            for ( i=0; i<8; i++) {
                if ( i<currentDashboardData.getTowersNum() )
                    currentDashboardView.setTowerVisible(i, true);
                else
                    currentDashboardView.setTowerVisible(i, false);
            }

        }

    }

    private class DashboardView {
        private ArrayList<ImageView> entranceStudents;
        private Map<Color, ArrayList<ImageView>> diningRoomStudents;
        private Map<Color, ImageView> master;
        private ArrayList<ImageView> tower;

        private ImageView currentCard;


        public DashboardView(ArrayList<ImageView> entranceStudents, Map<Color, ArrayList<ImageView>> diningRoomStudents, Map<Color, ImageView> master, ArrayList<ImageView> tower ) {
            this.entranceStudents=entranceStudents;
            this.diningRoomStudents=diningRoomStudents;
            this.master=master;
            this.tower=tower;
        }

        public void setCurrentCard(ImageView currentCard) {
            this.currentCard = currentCard;
        }

        public ImageView getCurrentCard() {
            return currentCard;
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

        public void setTowerVisible(int towerNumber, boolean visible) {
            tower.get(towerNumber).setVisible(visible);
        }

    }
    //ZAMBO END


    public void updateGameView() {
        updateDashboard();
        updateMessagePhase();
        updateIslands();
        updateClouds();
        //updateCurrentCards(); //TODO

        updateFigureCards();

    }

    private void updateClouds() {
        int i = 0,j=0;
        for(Cloud c : client.getMatchView().getClouds()){
            clouds.get(i).setVisible(true);
            for(Student s : c.getStudentsOnCloud()){
                studentsOcClouds.get(i).get(j).setVisible(true);
                studentsOcClouds.get(i).get(j).setImage(studentsImage.get(s.getColor()));
                j++;
            }
            if(c.getStudentsOnCloud().size() == 0){
                for(int z = 0; z<3; z++){
                    studentsOcClouds.get(i).get(z).setVisible(false);
                }
            }
            j=0;
            i++;
        }
        if(i!=3){
            for(int z = client.getMatchView().getClouds().size(); z < clouds.size();z++){
                clouds.get(z).setVisible(false);
                for(int p = 0; p<3; p++){
                    studentsOcClouds.get(z).get(p).setVisible(false);
                }
            }
        }
    }

    private void updateIslands() {
        for (Island island : client.getMatchView().getIslands()){
            if(island.checkIsMotherNature()){
                motherNatureList.get(island.getPosition()).setVisible(true);
                hint.setText("Mother nature on island" +island.getPosition());
            }
            else{
                motherNatureList.get(island.getPosition()).setVisible(false);
            }
            for (Color c : Color.values()){
                //System.out.println(numStudentsOnIsland.get(island.getPosition()).get(c).getText());
                numStudentsOnIsland.get(island.getPosition()).get(c).setText(""+island.getStudentsNumByColor(c));
            }
            try {
                towerOnIslands.get(island.getPosition()).setVisible(true);
                towerNumOnIslands.get(island.getPosition()).setVisible(true);
                towerOnIslands.get(island.getPosition()).setImage(towersImage.get(island.getTowerColor()));
                towerNumOnIslands.get(island.getPosition()).setText(""+island.getTowerNum());
            } catch (NoTowerException e) {
                towerOnIslands.get(island.getPosition()).setVisible(false);
                towerNumOnIslands.get(island.getPosition()).setVisible(false);
            }

        }
    }


}