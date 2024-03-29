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
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.*;
import model.exception.NoIslandException;
import model.exception.NoTowerException;
import model.exception.WrongColorException;
import model.figureCards.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControllerGUIGame extends ControllerGUIInterface implements Initializable {

    @FXML
    private Text nicknameDB2;

    @FXML
    private Text nicknameDB3;

    @FXML
    private Text nicknameDB4;



    /**choice phase:*/
    @FXML
    private Text choicePhaseMessage;

    //in zoom methods:
    private boolean zoomCard=false;
    private boolean zoomFigureCard=false;

    private boolean firstTime=false;

    /**messages:*/

    @FXML
    private Text playerTurnMessage;

    @FXML
    private Rectangle rectangleMessage;

    @FXML
    private Text hint;

    @FXML
    private GridPane messagePane;

    @FXML
    private Label messageLabel;

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



    @FXML
    private GridPane expertMatchPane;

    protected Map<ImageView, Integer> fromFigureCardsToInteger;

    @FXML
    private ImageView coinImg;
    @FXML
    private Label CoinNumber;


    //Figure cards:

    @FXML
    private ImageView figureCard1;
    @FXML
    private ImageView figureCard2;
    @FXML
    private ImageView figureCard3;

    private List<ImageView> figureCardsImageViewList;




    /**Figure card 1:*/
    @FXML
    private Text FC1coins;

        /**Students:*/
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

    private List<ImageView> studentsOnFigureCard1;

        /**Block cards:*/
    @FXML
    private ImageView FC1blockCard1;
    @FXML
    private ImageView FC1blockCard2;
    @FXML
    private ImageView FC1blockCard3;
    @FXML
    private ImageView FC1blockCard4;

    private List<ImageView> blockCardsOnFigureCard1;


    /**Figure card 2:*/
    @FXML
    private Text FC2coins;

        /**Students:*/
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

    List<ImageView> studentsOnFigureCard2;
        /**Block cards:*/

    @FXML
    private ImageView FC2blockCard1;
    @FXML
    private ImageView FC2blockCard2;
    @FXML
    private ImageView FC2blockCard3;
    @FXML
    private ImageView FC2blockCard4;

    private List<ImageView> blockCardsOnFigureCard2;

    /**Figure card 3:*/

    @FXML
    private Text FC3coins;

        /**Students:*/
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

    List<ImageView> studentsOnFigureCard3;

        /**Block cards:*/
    @FXML
    private ImageView FC3blockCard1;
    @FXML
    private ImageView FC3blockCard2;
    @FXML
    private ImageView FC3blockCard3;
    @FXML
    private ImageView FC3blockCard4;

    private List<ImageView> blockCardsOnFigureCard3;


    private List<List<ImageView>> studentsOnFigureCardsList;

    private List<List<ImageView>> blockCardsOnFigureCardsList;

    @FXML
    private ImageView blockCardIsl1;
    @FXML
    private ImageView blockCardIsl2;
    @FXML
    private ImageView blockCardIsl3;
    @FXML
    private ImageView blockCardIsl4;
    @FXML
    private ImageView blockCardIsl5;
    @FXML
    private ImageView blockCardIsl6;
    @FXML
    private ImageView blockCardIsl7;
    @FXML
    private ImageView blockCardIsl8;
    @FXML
    private ImageView blockCardIsl9;
    @FXML
    private ImageView blockCardIsl10;
    @FXML
    private ImageView blockCardIsl11;
    @FXML
    private ImageView blockCardIsl12;

    private List<ImageView> blockCardIslList;

    List<Text> coins;


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

    //Dashboard 1 Students, master and towers
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

    //Dashboard 2 Students, master and towers
    @FXML
    private ImageView D2EntranceStudent1;
    @FXML
    private ImageView D2EntranceStudent2;
    @FXML
    private ImageView D2EntranceStudent3;
    @FXML
    private ImageView D2EntranceStudent4;
    @FXML
    private ImageView D2EntranceStudent5;
    @FXML
    private ImageView D2EntranceStudent6;
    @FXML
    private ImageView D2EntranceStudent7;
    @FXML
    private ImageView D2EntranceStudent8;
    @FXML
    private ImageView D2EntranceStudent9;
    @FXML
    private ImageView D2GreenDRStudent1;
    @FXML
    private ImageView D2GreenDRStudent2;
    @FXML
    private ImageView D2GreenDRStudent3;
    @FXML
    private ImageView D2GreenDRStudent4;
    @FXML
    private ImageView D2GreenDRStudent5;
    @FXML
    private ImageView D2GreenDRStudent6;
    @FXML
    private ImageView D2GreenDRStudent7;
    @FXML
    private ImageView D2GreenDRStudent8;
    @FXML
    private ImageView D2GreenDRStudent9;
    @FXML
    private ImageView D2GreenDRStudent10;
    @FXML
    private ImageView D2RedDRStudent1;
    @FXML
    private ImageView D2RedDRStudent2;
    @FXML
    private ImageView D2RedDRStudent3;
    @FXML
    private ImageView D2RedDRStudent4;
    @FXML
    private ImageView D2RedDRStudent5;
    @FXML
    private ImageView D2RedDRStudent6;
    @FXML
    private ImageView D2RedDRStudent7;
    @FXML
    private ImageView D2RedDRStudent8;
    @FXML
    private ImageView D2RedDRStudent9;
    @FXML
    private ImageView D2RedDRStudent10;
    @FXML
    private ImageView D2YellowDRStudent1;
    @FXML
    private ImageView D2YellowDRStudent2;
    @FXML
    private ImageView D2YellowDRStudent3;
    @FXML
    private ImageView D2YellowDRStudent4;
    @FXML
    private ImageView D2YellowDRStudent5;
    @FXML
    private ImageView D2YellowDRStudent6;
    @FXML
    private ImageView D2YellowDRStudent7;
    @FXML
    private ImageView D2YellowDRStudent8;
    @FXML
    private ImageView D2YellowDRStudent9;
    @FXML
    private ImageView D2YellowDRStudent10;
    @FXML
    private ImageView D2PinkDRStudent1;
    @FXML
    private ImageView D2PinkDRStudent2;
    @FXML
    private ImageView D2PinkDRStudent3;
    @FXML
    private ImageView D2PinkDRStudent4;
    @FXML
    private ImageView D2PinkDRStudent5;
    @FXML
    private ImageView D2PinkDRStudent6;
    @FXML
    private ImageView D2PinkDRStudent7;
    @FXML
    private ImageView D2PinkDRStudent8;
    @FXML
    private ImageView D2PinkDRStudent9;
    @FXML
    private ImageView D2PinkDRStudent10;
    @FXML
    private ImageView D2BlueDRStudent1;
    @FXML
    private ImageView D2BlueDRStudent2;
    @FXML
    private ImageView D2BlueDRStudent3;
    @FXML
    private ImageView D2BlueDRStudent4;
    @FXML
    private ImageView D2BlueDRStudent5;
    @FXML
    private ImageView D2BlueDRStudent6;
    @FXML
    private ImageView D2BlueDRStudent7;
    @FXML
    private ImageView D2BlueDRStudent8;
    @FXML
    private ImageView D2BlueDRStudent9;
    @FXML
    private ImageView D2BlueDRStudent10;
    @FXML
    private ImageView D2GreenMaster;
    @FXML
    private ImageView D2RedMaster;
    @FXML
    private ImageView D2YellowMaster;
    @FXML
    private ImageView D2PinkMaster;
    @FXML
    private ImageView D2BlueMaster;
    @FXML
    private ImageView D2BlackTower1;
    @FXML
    private ImageView D2BlackTower2;
    @FXML
    private ImageView D2BlackTower3;
    @FXML
    private ImageView D2BlackTower4;
    @FXML
    private ImageView D2BlackTower5;
    @FXML
    private ImageView D2BlackTower6;
    @FXML
    private ImageView D2BlackTower7;
    @FXML
    private ImageView D2BlackTower8;

    //Dashboard 3 Students, master and towers
    @FXML
    private ImageView D3EntranceStudent1;
    @FXML
    private ImageView D3EntranceStudent2;
    @FXML
    private ImageView D3EntranceStudent3;
    @FXML
    private ImageView D3EntranceStudent4;
    @FXML
    private ImageView D3EntranceStudent5;
    @FXML
    private ImageView D3EntranceStudent6;
    @FXML
    private ImageView D3EntranceStudent7;
    @FXML
    private ImageView D3EntranceStudent8;
    @FXML
    private ImageView D3EntranceStudent9;
    @FXML
    private ImageView D3GreenDRStudent1;
    @FXML
    private ImageView D3GreenDRStudent2;
    @FXML
    private ImageView D3GreenDRStudent3;
    @FXML
    private ImageView D3GreenDRStudent4;
    @FXML
    private ImageView D3GreenDRStudent5;
    @FXML
    private ImageView D3GreenDRStudent6;
    @FXML
    private ImageView D3GreenDRStudent7;
    @FXML
    private ImageView D3GreenDRStudent8;
    @FXML
    private ImageView D3GreenDRStudent9;
    @FXML
    private ImageView D3GreenDRStudent10;
    @FXML
    private ImageView D3RedDRStudent1;
    @FXML
    private ImageView D3RedDRStudent2;
    @FXML
    private ImageView D3RedDRStudent3;
    @FXML
    private ImageView D3RedDRStudent4;
    @FXML
    private ImageView D3RedDRStudent5;
    @FXML
    private ImageView D3RedDRStudent6;
    @FXML
    private ImageView D3RedDRStudent7;
    @FXML
    private ImageView D3RedDRStudent8;
    @FXML
    private ImageView D3RedDRStudent9;
    @FXML
    private ImageView D3RedDRStudent10;
    @FXML
    private ImageView D3YellowDRStudent1;
    @FXML
    private ImageView D3YellowDRStudent2;
    @FXML
    private ImageView D3YellowDRStudent3;
    @FXML
    private ImageView D3YellowDRStudent4;
    @FXML
    private ImageView D3YellowDRStudent5;
    @FXML
    private ImageView D3YellowDRStudent6;
    @FXML
    private ImageView D3YellowDRStudent7;
    @FXML
    private ImageView D3YellowDRStudent8;
    @FXML
    private ImageView D3YellowDRStudent9;
    @FXML
    private ImageView D3YellowDRStudent10;
    @FXML
    private ImageView D3PinkDRStudent1;
    @FXML
    private ImageView D3PinkDRStudent2;
    @FXML
    private ImageView D3PinkDRStudent3;
    @FXML
    private ImageView D3PinkDRStudent4;
    @FXML
    private ImageView D3PinkDRStudent5;
    @FXML
    private ImageView D3PinkDRStudent6;
    @FXML
    private ImageView D3PinkDRStudent7;
    @FXML
    private ImageView D3PinkDRStudent8;
    @FXML
    private ImageView D3PinkDRStudent9;
    @FXML
    private ImageView D3PinkDRStudent10;
    @FXML
    private ImageView D3BlueDRStudent1;
    @FXML
    private ImageView D3BlueDRStudent2;
    @FXML
    private ImageView D3BlueDRStudent3;
    @FXML
    private ImageView D3BlueDRStudent4;
    @FXML
    private ImageView D3BlueDRStudent5;
    @FXML
    private ImageView D3BlueDRStudent6;
    @FXML
    private ImageView D3BlueDRStudent7;
    @FXML
    private ImageView D3BlueDRStudent8;
    @FXML
    private ImageView D3BlueDRStudent9;
    @FXML
    private ImageView D3BlueDRStudent10;
    @FXML
    private ImageView D3GreenMaster;
    @FXML
    private ImageView D3RedMaster;
    @FXML
    private ImageView D3YellowMaster;
    @FXML
    private ImageView D3PinkMaster;
    @FXML
    private ImageView D3BlueMaster;
    @FXML
    private ImageView D3BlackTower1;
    @FXML
    private ImageView D3BlackTower2;
    @FXML
    private ImageView D3BlackTower3;
    @FXML
    private ImageView D3BlackTower4;
    @FXML
    private ImageView D3BlackTower5;
    @FXML
    private ImageView D3BlackTower6;
    @FXML
    private ImageView D3BlackTower7;
    @FXML
    private ImageView D3BlackTower8;

    //Dashboard 4 Students, master and towers
    @FXML
    private ImageView D4EntranceStudent1;
    @FXML
    private ImageView D4EntranceStudent2;
    @FXML
    private ImageView D4EntranceStudent3;
    @FXML
    private ImageView D4EntranceStudent4;
    @FXML
    private ImageView D4EntranceStudent5;
    @FXML
    private ImageView D4EntranceStudent6;
    @FXML
    private ImageView D4EntranceStudent7;
    @FXML
    private ImageView D4EntranceStudent8;
    @FXML
    private ImageView D4EntranceStudent9;
    @FXML
    private ImageView D4GreenDRStudent1;
    @FXML
    private ImageView D4GreenDRStudent2;
    @FXML
    private ImageView D4GreenDRStudent3;
    @FXML
    private ImageView D4GreenDRStudent4;
    @FXML
    private ImageView D4GreenDRStudent5;
    @FXML
    private ImageView D4GreenDRStudent6;
    @FXML
    private ImageView D4GreenDRStudent7;
    @FXML
    private ImageView D4GreenDRStudent8;
    @FXML
    private ImageView D4GreenDRStudent9;
    @FXML
    private ImageView D4GreenDRStudent10;
    @FXML
    private ImageView D4RedDRStudent1;
    @FXML
    private ImageView D4RedDRStudent2;
    @FXML
    private ImageView D4RedDRStudent3;
    @FXML
    private ImageView D4RedDRStudent4;
    @FXML
    private ImageView D4RedDRStudent5;
    @FXML
    private ImageView D4RedDRStudent6;
    @FXML
    private ImageView D4RedDRStudent7;
    @FXML
    private ImageView D4RedDRStudent8;
    @FXML
    private ImageView D4RedDRStudent9;
    @FXML
    private ImageView D4RedDRStudent10;
    @FXML
    private ImageView D4YellowDRStudent1;
    @FXML
    private ImageView D4YellowDRStudent2;
    @FXML
    private ImageView D4YellowDRStudent3;
    @FXML
    private ImageView D4YellowDRStudent4;
    @FXML
    private ImageView D4YellowDRStudent5;
    @FXML
    private ImageView D4YellowDRStudent6;
    @FXML
    private ImageView D4YellowDRStudent7;
    @FXML
    private ImageView D4YellowDRStudent8;
    @FXML
    private ImageView D4YellowDRStudent9;
    @FXML
    private ImageView D4YellowDRStudent10;
    @FXML
    private ImageView D4PinkDRStudent1;
    @FXML
    private ImageView D4PinkDRStudent2;
    @FXML
    private ImageView D4PinkDRStudent3;
    @FXML
    private ImageView D4PinkDRStudent4;
    @FXML
    private ImageView D4PinkDRStudent5;
    @FXML
    private ImageView D4PinkDRStudent6;
    @FXML
    private ImageView D4PinkDRStudent7;
    @FXML
    private ImageView D4PinkDRStudent8;
    @FXML
    private ImageView D4PinkDRStudent9;
    @FXML
    private ImageView D4PinkDRStudent10;
    @FXML
    private ImageView D4BlueDRStudent1;
    @FXML
    private ImageView D4BlueDRStudent2;
    @FXML
    private ImageView D4BlueDRStudent3;
    @FXML
    private ImageView D4BlueDRStudent4;
    @FXML
    private ImageView D4BlueDRStudent5;
    @FXML
    private ImageView D4BlueDRStudent6;
    @FXML
    private ImageView D4BlueDRStudent7;
    @FXML
    private ImageView D4BlueDRStudent8;
    @FXML
    private ImageView D4BlueDRStudent9;
    @FXML
    private ImageView D4BlueDRStudent10;
    @FXML
    private ImageView D4GreenMaster;
    @FXML
    private ImageView D4RedMaster;
    @FXML
    private ImageView D4YellowMaster;
    @FXML
    private ImageView D4PinkMaster;
    @FXML
    private ImageView D4BlueMaster;
    @FXML
    private ImageView D4BlackTower1;
    @FXML
    private ImageView D4BlackTower2;
    @FXML
    private ImageView D4BlackTower3;
    @FXML
    private ImageView D4BlackTower4;
    @FXML
    private ImageView D4BlackTower5;
    @FXML
    private ImageView D4BlackTower6;
    @FXML
    private ImageView D4BlackTower7;
    @FXML
    private ImageView D4BlackTower8;


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
    private ImageView student4OnCloud2;

    @FXML
    private ImageView student4OnCloud1;

    @FXML
    private ImageView student4OnCloud3;

    @FXML
    private ImageView student4OnCloud4;

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

    //islands

    @FXML
    private ImageView islandImage1;

    @FXML
    private ImageView islandImage10;

    @FXML
    private ImageView islandImage11;

    @FXML
    private ImageView islandImage12;

    @FXML
    private ImageView islandImage2;

    @FXML
    private ImageView islandImage3;

    @FXML
    private ImageView islandImage4;

    @FXML
    private ImageView islandImage5;

    @FXML
    private ImageView islandImage6;

    @FXML
    private ImageView islandImage7;

    @FXML
    private ImageView islandImage8;

    @FXML
    private ImageView islandImage9;

    //students on islands

    @FXML
    private ImageView pinkStudentIsl10;

    @FXML
    private ImageView pinkStudentIsl11;

    @FXML
    private ImageView pinkStudentIsl12;

    @FXML
    private ImageView pinkStudentIsl2;

    @FXML
    private ImageView pinkStudentIsl3;

    @FXML
    private ImageView pinkStudentIsl4;

    @FXML
    private ImageView pinkStudentIsl5;

    @FXML
    private ImageView pinkStudentIsl6;

    @FXML
    private ImageView pinkStudentIsl7;

    @FXML
    private ImageView pinkStudentIsl8;

    @FXML
    private ImageView pinkStudentIsl9;

    @FXML
    private ImageView redStudentIsl1;

    @FXML
    private ImageView redStudentIsl10;

    @FXML
    private ImageView redStudentIsl11;

    @FXML
    private ImageView redStudentIsl12;

    @FXML
    private ImageView redStudentIsl2;

    @FXML
    private ImageView redStudentIsl3;

    @FXML
    private ImageView redStudentIsl4;

    @FXML
    private ImageView redStudentIsl5;

    @FXML
    private ImageView redStudentIsl6;

    @FXML
    private ImageView redStudentIsl7;

    @FXML
    private ImageView redStudentIsl8;

    @FXML
    private ImageView redStudentIsl9;

    @FXML
    private ImageView yellowStudentIsl1;

    @FXML
    private ImageView yellowStudentIsl10;

    @FXML
    private ImageView yellowStudentIsl11;

    @FXML
    private ImageView yellowStudentIsl12;

    @FXML
    private ImageView yellowStudentIsl2;

    @FXML
    private ImageView yellowStudentIsl3;

    @FXML
    private ImageView yellowStudentIsl4;

    @FXML
    private ImageView yellowStudentIsl5;

    @FXML
    private ImageView yellowStudentIsl6;

    @FXML
    private ImageView yellowStudentIsl7;

    @FXML
    private ImageView yellowStudentIsl8;

    @FXML
    private ImageView yellowStudentIsl9;

    @FXML
    private ImageView blueStudentIsl1;

    @FXML
    private ImageView blueStudentIsl10;

    @FXML
    private ImageView blueStudentIsl11;

    @FXML
    private ImageView blueStudentIsl12;

    @FXML
    private ImageView blueStudentIsl2;

    @FXML
    private ImageView blueStudentIsl3;

    @FXML
    private ImageView blueStudentIsl4;

    @FXML
    private ImageView blueStudentIsl5;

    @FXML
    private ImageView blueStudentIsl6;

    @FXML
    private ImageView blueStudentIsl7;

    @FXML
    private ImageView blueStudentIsl8;

    @FXML
    private ImageView blueStudentIsl9;

    @FXML
    private ImageView greenStudentIsl1;

    @FXML
    private ImageView greenStudentIsl10;

    @FXML
    private ImageView greenStudentIsl11;

    @FXML
    private ImageView greenStudentIsl12;

    @FXML
    private ImageView greenStudentIsl2;

    @FXML
    private ImageView greenStudentIsl3;

    @FXML
    private ImageView greenStudentIsl4;

    @FXML
    private ImageView greenStudentIsl5;

    @FXML
    private ImageView greenStudentIsl6;

    @FXML
    private ImageView greenStudentIsl7;

    @FXML
    private ImageView greenStudentIsl8;

    @FXML
    private ImageView greenStudentIsl9;

    @FXML
    private ImageView pinkStudentIsl1;

    private int numChoice = 0;

    private List<Integer> studentsIdsToMoveFromCard = new ArrayList<>();

    private List<Integer> studentsIdsToMoveFromEntrance = new ArrayList<>();

    private List<Integer> studentsIdsToMoveFromDiningRoom = new ArrayList<>();

    private int jesterCounter = 0,maxNumMinistrelStudents = 0,chosenStudentFromEntrance = -1;

    private List<Map<Color, Text>> numStudentsOnIsland;

    private List<ImageView> towerOnIslands;

    private List<Text> towerNumOnIslands;

    private List<ImageView> motherNatureList;

    private Map<String, DashboardView> playersDashboardView = new HashMap<>();

    private List<ImageView> clouds;

    private Map<Integer,List<ImageView>> studentsOcClouds;

    private Map<Integer,Map<Color,ImageView>> studentImagesOnIslands;

    private List<ImageView> islandsImages;

    private Map<Color,ImageView> studentsImagesIsland1;

    private Map<Color,ImageView> studentsImagesIsland2;

    private Map<Color,ImageView> studentsImagesIsland3;

    private Map<Color,ImageView> studentsImagesIsland4;

    private Map<Color,ImageView> studentsImagesIsland5;

    private Map<Color,ImageView> studentsImagesIsland6;

    private Map<Color,ImageView> studentsImagesIsland7;

    private Map<Color,ImageView> studentsImagesIsland8;

    private Map<Color,ImageView> studentsImagesIsland9;

    private Map<Color,ImageView> studentsImagesIsland10;

    private Map<Color,ImageView> studentsImagesIsland11;

    private Map<Color,ImageView> studentsImagesIsland12;

    private Map<String, Integer> fromIslandToInt=new HashMap<>(){{
        put("island1",0);
        put("island2",1);
        put("island3",2);
        put("island4",3);
        put("island5",4);
        put("island6",5);
        put("island7",6);
        put("island8",7);
        put("island9",8);
        put("island10",9);
        put("island11",10);
        put("island12",11);


    }};


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

        figureCardsImageViewList=new ArrayList<>(){{
            add(figureCard1);
            add(figureCard2);
            add(figureCard3);
        }};





        studentsOnFigureCard3=new ArrayList<>(){{
            add(FC3Student1);
            add(FC3Student2);
            add(FC3Student3);
            add(FC3Student4);
            add(FC3Student5);
            add(FC3Student6);
        }};

        studentsOnFigureCard1=new ArrayList<>(){{
            add(FC1Student1);
            add(FC1Student2);
            add(FC1Student3);
            add(FC1Student4);
            add(FC1Student5);
            add(FC1Student6);
        }};

        blockCardsOnFigureCard1=new ArrayList<>(){{
            add(FC1blockCard1);
            add(FC1blockCard2);
            add(FC1blockCard3);
            add(FC1blockCard4);
        }};

        studentsOnFigureCard2=new ArrayList<>(){{
            add(FC2Student1);
            add(FC2Student2);
            add(FC2Student3);
            add(FC2Student4);
            add(FC2Student5);
            add(FC2Student6);
        }};

        blockCardsOnFigureCard2=new ArrayList<>(){{
            add(FC2blockCard1);
            add(FC2blockCard2);
            add(FC2blockCard3);
            add(FC2blockCard4);
        }};

        blockCardsOnFigureCard3=new ArrayList<>(){{
            add(FC3blockCard1);
            add(FC3blockCard2);
            add(FC3blockCard3);
            add(FC3blockCard4);
        }};

        blockCardsOnFigureCardsList=new ArrayList<>(){{
            add(blockCardsOnFigureCard1);
            add(blockCardsOnFigureCard2);
            add(blockCardsOnFigureCard3);
        }};

        blockCardIslList=new ArrayList<>(){{
            add(blockCardIsl1);
            add(blockCardIsl2);
            add(blockCardIsl3);
            add(blockCardIsl4);
            add(blockCardIsl5);
            add(blockCardIsl6);
            add(blockCardIsl7);
            add(blockCardIsl8);
            add(blockCardIsl9);
            add(blockCardIsl10);
            add(blockCardIsl11);
            add(blockCardIsl12);
        }};

        coins=new ArrayList<>(){{
            add(FC1coins);
            add(FC2coins);
            add(FC3coins);
        }};

        for (Text t:coins) {
            t.setFont(Font.loadFont(getClass().getResourceAsStream("/Supercell.ttf"),24));
        }

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

        islandsImages = new ArrayList<>(){{
            add(islandImage1);
            add(islandImage2);
            add(islandImage3);
            add(islandImage4);
            add(islandImage5);
            add(islandImage6);
            add(islandImage7);
            add(islandImage8);
            add(islandImage9);
            add(islandImage10);
            add(islandImage11);
            add(islandImage12);
        }};

        studentsImagesIsland1 = new HashMap<>(){{
            put(Color.RED,redStudentIsl1);
            put(Color.BLUE,blueStudentIsl1);
            put(Color.YELLOW,yellowStudentIsl1);
            put(Color.GREEN,greenStudentIsl1);
            put(Color.PINK,pinkStudentIsl1);
        }};

        studentsImagesIsland2 = new HashMap<>(){{
            put(Color.RED,redStudentIsl2);
            put(Color.BLUE,blueStudentIsl2);
            put(Color.YELLOW,yellowStudentIsl2);
            put(Color.GREEN,greenStudentIsl2);
            put(Color.PINK,pinkStudentIsl2);
        }};

        studentsImagesIsland3 = new HashMap<>(){{
            put(Color.RED,redStudentIsl3);
            put(Color.BLUE,blueStudentIsl3);
            put(Color.YELLOW,yellowStudentIsl3);
            put(Color.GREEN,greenStudentIsl3);
            put(Color.PINK,pinkStudentIsl3 );
        }};

        studentsImagesIsland4 = new HashMap<>(){{
            put(Color.RED,redStudentIsl4);
            put(Color.BLUE,blueStudentIsl4);
            put(Color.YELLOW,yellowStudentIsl4);
            put(Color.GREEN,greenStudentIsl4);
            put(Color.PINK,pinkStudentIsl4);
        }};

        studentsImagesIsland5 = new HashMap<>(){{
            put(Color.RED,redStudentIsl5);
            put(Color.BLUE,blueStudentIsl5);
            put(Color.YELLOW,yellowStudentIsl5);
            put(Color.GREEN,greenStudentIsl5);
            put(Color.PINK,pinkStudentIsl5);
        }};

        studentsImagesIsland6 = new HashMap<>(){{
            put(Color.RED,redStudentIsl6);
            put(Color.BLUE,blueStudentIsl6);
            put(Color.YELLOW,yellowStudentIsl6);
            put(Color.GREEN,greenStudentIsl6);
            put(Color.PINK,pinkStudentIsl6);
        }};

        studentsImagesIsland7 = new HashMap<>(){{
            put(Color.RED,redStudentIsl7);
            put(Color.BLUE,blueStudentIsl7);
            put(Color.YELLOW,yellowStudentIsl7);
            put(Color.GREEN,greenStudentIsl7);
            put(Color.PINK,pinkStudentIsl7);
        }};

        studentsImagesIsland8 = new HashMap<>(){{
            put(Color.RED,redStudentIsl8);
            put(Color.BLUE,blueStudentIsl8);
            put(Color.YELLOW,yellowStudentIsl8);
            put(Color.GREEN,greenStudentIsl8);
            put(Color.PINK,pinkStudentIsl8);
        }};

        studentsImagesIsland9 = new HashMap<>(){{
            put(Color.RED,redStudentIsl9);
            put(Color.BLUE,blueStudentIsl9);
            put(Color.YELLOW,yellowStudentIsl9);
            put(Color.GREEN,greenStudentIsl9);
            put(Color.PINK,pinkStudentIsl9);
        }};

        studentsImagesIsland10 = new HashMap<>(){{
            put(Color.RED,redStudentIsl10);
            put(Color.BLUE,blueStudentIsl10);
            put(Color.YELLOW,yellowStudentIsl10);
            put(Color.GREEN,greenStudentIsl10);
            put(Color.PINK,pinkStudentIsl10);
        }};

        studentsImagesIsland11 = new HashMap<>(){{
            put(Color.RED,redStudentIsl11);
            put(Color.BLUE,blueStudentIsl11);
            put(Color.YELLOW,yellowStudentIsl11);
            put(Color.GREEN,greenStudentIsl11);
            put(Color.PINK,pinkStudentIsl11);
        }};

        studentsImagesIsland12 = new HashMap<>(){{
            put(Color.RED,redStudentIsl12);
            put(Color.BLUE,blueStudentIsl12);
            put(Color.YELLOW,yellowStudentIsl12);
            put(Color.GREEN,greenStudentIsl12);
            put(Color.PINK,pinkStudentIsl12);
        }};

        studentImagesOnIslands = new HashMap<>(){{
            put(0,studentsImagesIsland1);
            put(1,studentsImagesIsland2);
            put(2,studentsImagesIsland3);
            put(3,studentsImagesIsland4);
            put(4,studentsImagesIsland5);
            put(5,studentsImagesIsland6);
            put(6,studentsImagesIsland7);
            put(7,studentsImagesIsland8);
            put(8,studentsImagesIsland9);
            put(9,studentsImagesIsland10);
            put(10,studentsImagesIsland11);
            put(11,studentsImagesIsland12);
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
            put(0,new ArrayList<>(){{add(student1OnCloud1);add(student2OnCloud1);add(student3OnCloud1);add(student4OnCloud1);}});
            put(1,new ArrayList<>(){{add(student1OnCloud2);add(student2OnCloud2);add(student3OnCloud2);add(student4OnCloud2);}});
            put(2,new ArrayList<>(){{add(student1OnCloud3);add(student2OnCloud3);add(student3OnCloud3);add(student4OnCloud3);}});
            put(3,new ArrayList<>(){{add(student1OnCloud4);add(student2OnCloud4);add(student3OnCloud4);add(student4OnCloud4);}});
        }};
    }

    public void showAllowedCommandKey(){
        hint.setText("You can press c to show/hide your cards");
        if(client.getMatchView().showAllPlayers().size()>=3)
            hint.setText("You can press c to show/hide your cards or d to show/hide the enemy dashboards");
    }

    public void submitBlockOnIsland(Event event){
        ((GrannyGrassChoice)client.getActualToDoChoice()).setIslandPositionTmp(client.getMatchView().getIslandPositions(), client.getMatchView());
        String islandID = ((Region) event.getSource()).getId();

        client.getActualToDoChoice().setChoiceParam(""+fromIslandToInt.get(islandID));

        if(client.getActualToDoChoice().completed) {
            synchronized (client.getOutputStreamLock()) {
                client.getOutputStreamLock().notifyAll();
            }
        }
    }
    public void submitStudentsOnFigureCard(Event event){
        switch (client.getMatchView().getChoice().whichChoicePhase()){
            case("Merchant played from the current player"):{
                submitStudentOnMerchant(event);
                break;
            }
            case("Princess played from the current player"):{
                submitStudentOnPrincess(event);
                break;
            }
            case ("Jester played from the current player"):
                submitStudentOnJester(event);
                break;
        }
    }

    public void submitStudentOnPrincess(Event event){
        if(studentsOnFigureCard1.contains((ImageView)event.getSource()) && figureCardsMap.get(7).equals(figureCard1.getImage())){
            client.getMatchView().getChoice().setChoiceParam(""+(studentsOnFigureCard1.indexOf(((ImageView) event.getSource()))+1));
        }
        else if(studentsOnFigureCard2.contains((ImageView)event.getSource()) && figureCardsMap.get(7).equals(figureCard2.getImage())){
            client.getMatchView().getChoice().setChoiceParam(""+(studentsOnFigureCard2.indexOf(((ImageView) event.getSource()))+1));
        }
        else if(studentsOnFigureCard3.contains((ImageView)event.getSource()) && figureCardsMap.get(7).equals(figureCard3.getImage())){
            client.getMatchView().getChoice().setChoiceParam(""+(studentsOnFigureCard3.indexOf((ImageView) event.getSource())+1));
        }
        synchronized (client.getOutputStreamLock()) {
            client.getOutputStreamLock().notifyAll();
        }
    }



    public void submitStudentOnMerchant(Event event){


        if(studentsOnFigureCard1.contains((ImageView)event.getSource()) && figureCardsMap.get(4).equals(figureCard1.getImage())
                && ((MerchantChoice) client.getMatchView().getChoice()).getNumChoice()==0){
            client.getMatchView().getChoice().setChoiceParam(""+(studentsOnFigureCard1.indexOf(((ImageView) event.getSource()))+1));
            ((ImageView)event.getSource()).setOpacity(0.5);
        }
        else if(studentsOnFigureCard2.contains((ImageView)event.getSource()) && figureCardsMap.get(4).equals(figureCard2.getImage())
                && ((MerchantChoice) client.getMatchView().getChoice()).getNumChoice()==0){
            client.getMatchView().getChoice().setChoiceParam(""+(studentsOnFigureCard2.indexOf(((ImageView) event.getSource()))+1));
            ((ImageView)event.getSource()).setOpacity(0.5);
        }
        else if(studentsOnFigureCard3.contains((ImageView)event.getSource()) && figureCardsMap.get(4).equals(figureCard3.getImage())
                && ((MerchantChoice) client.getMatchView().getChoice()).getNumChoice()==0){
            client.getMatchView().getChoice().setChoiceParam(""+(studentsOnFigureCard3.indexOf(((ImageView) event.getSource()))+1));
            ((ImageView)event.getSource()).setOpacity(0.5);
        }
    }

    public void submitStudentOnJester(Event event){
        if(numChoice == 0){
    if(studentsOnFigureCard1.contains((ImageView)event.getSource()) && figureCardsMap.get(2).equals(figureCard1.getImage())){
        // client.getMatchView().getChoice().setChoiceParam(""+(studentsOnFigureCard1.indexOf(((ImageView) event.getSource()))+1));
        if(((ImageView)event.getSource()).getOpacity() != 0.5 && studentsIdsToMoveFromCard.size()!=3){
            ((ImageView)event.getSource()).setOpacity(0.5);
            studentsIdsToMoveFromCard.add(studentsOnFigureCard1.indexOf(((ImageView) event.getSource()))+1);
        }
        else {
            ((ImageView)event.getSource()).setOpacity(1.0);
            studentsIdsToMoveFromCard.remove(Integer.valueOf(studentsOnFigureCard1.indexOf(((ImageView) event.getSource()))+1));
        }

    }
    else if(studentsOnFigureCard2.contains((ImageView)event.getSource()) && figureCardsMap.get(2).equals(figureCard2.getImage())){
        if(((ImageView)event.getSource()).getOpacity() != 0.5 && studentsIdsToMoveFromCard.size()!=3){
            ((ImageView)event.getSource()).setOpacity(0.5);
            studentsIdsToMoveFromCard.add(studentsOnFigureCard2.indexOf(((ImageView) event.getSource()))+1);
        }
        else {
            ((ImageView)event.getSource()).setOpacity(1.0);
            studentsIdsToMoveFromCard.remove(Integer.valueOf(studentsOnFigureCard2.indexOf(((ImageView) event.getSource()))+1));
        }

    }
    else if(studentsOnFigureCard3.contains((ImageView)event.getSource()) && figureCardsMap.get(2).equals(figureCard3.getImage())){
        if(((ImageView)event.getSource()).getOpacity() != 0.5 && studentsIdsToMoveFromCard.size()!=3){
            ((ImageView)event.getSource()).setOpacity(0.5);
            studentsIdsToMoveFromCard.add(studentsOnFigureCard3.indexOf(((ImageView) event.getSource()))+1);
        }
        else {
            ((ImageView)event.getSource()).setOpacity(1.0);
            studentsIdsToMoveFromCard.remove(Integer.valueOf(studentsOnFigureCard3.indexOf(((ImageView) event.getSource()))+1));
        }

    }
}

    }

    public void submitIslandForMerchant(Event event){
        ((MerchantChoice)client.getActualToDoChoice()).setIslandPositionSize(client.getMatchView().getIslandPositions().size());
        if(client.getMatchView().getChoice() instanceof MerchantChoice
                && ((MerchantChoice) client.getMatchView().getChoice()).getNumChoice()==1){
            String islandID = ((Region) event.getSource()).getId();

            client.getActualToDoChoice().setChoiceParam(""+fromIslandToInt.get(islandID));
            synchronized (client.getOutputStreamLock()) {
                client.getOutputStreamLock().notifyAll();
            }
        }
    }


    /**updates*/
    public void updateFigureCards() {
        List<FigureCard> figureCards=client.getMatchView().showFigureCardsInGame();


        for(int i=0; i<coins.size();i++){
            int numberMoreCoins=((ExpertMatch)client.getMatchView()).showFigureCardsInGame().get(i).getPrice()
                    -((ExpertMatch)client.getMatchView()).showFigureCardsInGame().get(i).getPRICECARD();
            coins.get(i).setText("+"+numberMoreCoins);
        }

        int j=0;
        for(FigureCard f: figureCards) {
            if(f instanceof FigureCardWithStudents) {
                int i = 0;
                for (Student s : ((FigureCardWithStudents) f).getStudentsOnCard()) {
                    studentsOnFigureCardsList.get(j).get(i).setImage(studentsImage.get(s.getColor()));
                    studentsOnFigureCardsList.get(j).get(i).setVisible(true);
                    studentsOnFigureCardsList.get(j).get(i).setOpacity(1);
                    studentsOnFigureCardsList.get(j).get(i).setDisable(true);
                    i++;
                }
            }else if(f instanceof GrannyGrass){
                int k=0;
                for(ImageView i: blockCardsOnFigureCardsList.get(figureCards.indexOf(f))){
                    if(k>=client.getMatchView().getRemainingBlockCards())
                        i.setVisible(false);
                    else
                        i.setVisible(true);
                    k++;
                }
            }
            j++;
        }

        if (client.getPlayer().equals(client.getMatchView().showCurrentPlayer())) {
            CoinNumber.setText("" + client.getMatchView().showCurrentPlayerDashboard().getCoinsNumber());

            if (client.getMatchView().getChoice() instanceof FigureCardActionChoice
                    || client.getMatchView().showCurrentPlayerDashboard().hasKnightPrivilege()
                    || client.getMatchView().showCurrentPlayerDashboard().isFarmerEffect()
                    || client.getMatchView().isCentaurEffect()
                    || client.getMatchView().isPostManValue()) {

                if(client.getMatchView().showCurrentPlayerDashboard().hasKnightPrivilege())
                    Knight.hintMessage(hint, true);
                else if(client.getMatchView().showCurrentPlayerDashboard().isFarmerEffect())
                    Farmer.hintMessage(hint, true);
                else if(client.getMatchView().isCentaurEffect())
                    Centaur.hintMessage(hint, true);
                else if (client.getMatchView().isPostManValue())
                    Postman.hintMessage(hint, true);

                if(client.isFigureCardNotPlayed()){
                    Media media=new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
                    AudioClip playBeep=new AudioClip(media.getSource());
                    playBeep.play();
                }

                avatarFigureCard.setVisible(true);
                client.setFigureCardNotPlayed(false);


                for(ImageView i:studentsOnFigureCard1) {
                    i.setDisable(false);
                }
                for(ImageView i:studentsOnFigureCard2) {
                    i.setDisable(false);
                }
                for(ImageView i:studentsOnFigureCard3) {
                    i.setDisable(false);
                }

            }
        }else {
            if (firstTime) {
                if (client.getMatchView().getChoice() instanceof FigureCardActionChoice
                        || client.getMatchView().showCurrentPlayerDashboard().hasKnightPrivilege()
                        || client.getMatchView().showCurrentPlayerDashboard().isFarmerEffect()
                        || client.getMatchView().isCentaurEffect()
                        || client.getMatchView().isPostManValue()) {


                    hint.setText(client.getMatchView().getChoice().whichChoicePhase());

                    if(client.getMatchView().showCurrentPlayerDashboard().hasKnightPrivilege())
                        Knight.hintMessage(hint, false);
                    else if(client.getMatchView().showCurrentPlayerDashboard().isFarmerEffect())
                        Farmer.hintMessage(hint, false);
                    else if(client.getMatchView().isCentaurEffect())
                        Centaur.hintMessage(hint, false);
                    else if (client.getMatchView().isPostManValue())
                        Postman.hintMessage(hint, false);

                    Media media = new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
                    MediaPlayer playBeep = new MediaPlayer(media);
                    playBeep.play();

                }
            }

        }
        for (int i=0;i<client.getMatchView().getIslands().size(); i++) {
            if(client.getMatchView().getIslands().get(i).checkForbidden()){
                blockCardIslList.get(i).setVisible(true);
            }else
                blockCardIslList.get(i).setVisible(false);

        }
    }

    public void updateInitialMatchView() {


        if (client.isMatchCompletelyCreated()) {
            MatchDataInterface match = client.getMatchView();

            List<Text> nicknames=new ArrayList<>(){{
                add(nicknameDB2);
                add(nicknameDB3);
                add(nicknameDB4);
            }};

            /*int k=1;
            for (int i=1; i< match.showAllPlayers().size(); i++) {
                if(match.showAllPlayers().get(i).equals(client.getPlayer()))
                    k=i+1;
                else
                    nicknames.get(i).setText(match.showAllPlayers().get(k).getNickname());
            }*/


            //figure cards management and coins management:
            if(match instanceof ExpertMatch) {
                List<FigureCard> figureCards = match.showFigureCardsInGame();
                studentsOnFigureCardsList=new ArrayList<>(){{
                    add(studentsOnFigureCard1);
                    add(studentsOnFigureCard2);
                    add(studentsOnFigureCard3);
                }};

                for(int i=0; i< figureCards.size();i++) {
                    figureCardsImageViewList.get(i).setImage(figureCardsMap.get(figureCards.get(i).getCardId()));
                    if (figureCards.get(i) instanceof FigureCardWithStudents) {

                        int j = 0;
                        for (Student s : ((FigureCardWithStudents) figureCards.get(i)).getStudentsOnCard()) {
                            studentsOnFigureCardsList.get(i).get(j).setImage(studentsImage.get(s.getColor()));
                            studentsOnFigureCardsList.get(i).get(j).setVisible(true);
                            j++;
                        }
                    } else if (figureCards.get(i) instanceof GrannyGrass) {
                        for (int j = 0; j < blockCardsOnFigureCardsList.get(i).size(); j++) {
                            blockCardsOnFigureCardsList.get(i).get(j).setVisible(true);
                            blockCardsOnFigureCardsList.get(i).get(j).setDisable(true);
                        }
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
                    playerTurnMessage.setText("It's "+ client.getMatchView().showCurrentPlayer().getNickname()+" turn");

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
            firstTime=true;
            avatarFigureCard.setVisible(false);
            client.setFigureCardNotPlayed(true);
            showAllowedCommandKey();
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
                if(playersDashboardView.get(client.getMatchView().getPlayerByCurrentCard(c).getNickname()).getCurrentCard().equals(cardDb2)
                        || playersDashboardView.get(client.getMatchView().getPlayerByCurrentCard(c).getNickname()).getCurrentCard().equals(cardDb1))
                    playersDashboardView.get(client.getMatchView().getPlayerByCurrentCard(c).getNickname()).getCurrentCard().setVisible(true);
                else
                    playersDashboardView.get(client.getMatchView().getPlayerByCurrentCard(c).getNickname()).getCurrentCard().setVisible(Dashboard3.isVisible());
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (client.getMatchView() instanceof ExpertMatch
                        && !(client.getActualToDoChoice() instanceof FigureCardActionChoice)
                        && !(client.getActualToDoChoice() instanceof CardChoice)
                        && !(client.getActualToDoChoice() instanceof DataPlayerChoice) && client.isFigureCardNotPlayed()) {
                    Choice figureCardChoice = new FigureCardPlayedChoice(client.getMatchView().showFigureCardsInGame());
                    client.setActualToDoChoiceQueue(client.getActualToDoChoice());
                    client.setActualToDoChoice(figureCardChoice);
                    client.getActualToDoChoiceQueue().setSendingPlayer(client.getPlayer());

                    avatarFigureCard.setImage(((ImageView) event.getSource()).getImage());
                    figureCardChoice.setChoiceParam("" + fromFigureCardsToInteger.get(((ImageView) event.getSource())));

                    synchronized (client.getOutputStreamLock()) {
                        client.getOutputStreamLock().notifyAll();
                    }



                }
            }
        });
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
                                if (client.getMatchView().getSizeCurrentCards() >= 3)
                                    cardDb3.setVisible(Dashboard3.isVisible());
                                break;
                            }
                            case (4): {
                                Dashboard3.setVisible(!Dashboard3.isVisible());
                                rectangleMessage.setVisible(!rectangleMessage.isVisible());
                                hint.setVisible(!hint.isVisible());
                                playerTurnMessage.setVisible(!playerTurnMessage.isVisible());
                                Dashboard4.setVisible(!Dashboard4.isVisible());
                                avatarPane.setVisible(!avatarPane.isVisible());
                                if (client.getMatchView().getSizeCurrentCards()>= 4)
                                    cardDb4.setVisible(Dashboard4.isVisible());
                                if (client.getMatchView().getSizeCurrentCards()>= 3)
                                    cardDb3.setVisible(Dashboard3.isVisible());
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
                ((MoveStudentChoice) client.getActualToDoChoice()).setStudentsOnEntrance(client.getMatchView().showCurrentPlayerDashboard().showEntrance());
                if(chosenStudentFromEntrance != -1)
                    playersDashboardView.get(client.getPlayer().getNickname()).entranceStudents.get(chosenStudentFromEntrance).setOpacity(1.0);
                chosenStudentFromEntrance = playersDashboardView.get(client.getPlayer().getNickname()).entranceStudents.indexOf(chosenStudent)+1;
                //client.getActualToDoChoice().setChoiceParam(String.valueOf(playersDashboardView.get(client.getPlayer().getNickname()).entranceStudents.indexOf(chosenStudent)+1));

                for(ImageView i : playersDashboardView.get(client.getPlayer().getNickname()).entranceStudents){
                        i.setOpacity(1);
                }
                if(((ImageView)event.getSource()).getOpacity() == 1)
                    ((ImageView)event.getSource()).setOpacity(0.5);
                else{
                    ((ImageView)event.getSource()).setOpacity(1);
                    chosenStudentFromEntrance = -1;
                }

            }
            else if(client.getActualToDoChoice() instanceof JesterChoice){
                if(numChoice == 0 && studentsIdsToMoveFromCard.size()!=0){
                    numChoice=1;}
                if(numChoice == 1){
                    if(((ImageView)event.getSource()).getOpacity() == 1.0){
                        studentsIdsToMoveFromEntrance.add(playersDashboardView.get(client.getPlayer().getNickname()).entranceStudents.indexOf(chosenStudent)+1);
                        ((ImageView)event.getSource()).setOpacity(0.5);
                    }
                    else{
                        ((ImageView)event.getSource()).setOpacity(1.0);
                        studentsIdsToMoveFromEntrance.remove(Integer.valueOf(playersDashboardView.get(client.getPlayer().getNickname()).entranceStudents.indexOf(chosenStudent)+1));
                    }
                    if(studentsIdsToMoveFromEntrance.size() == studentsIdsToMoveFromCard.size()){
                        numChoice = 0;
                        client.getActualToDoChoice().setChoiceParam(""+studentsIdsToMoveFromEntrance.size());
                        for(Integer i : studentsIdsToMoveFromCard){
                            client.getActualToDoChoice().setChoiceParam(""+i);
                        }
                        ((JesterChoice)client.getActualToDoChoice()).setStudentsInEntrance(client.getMatchView().showCurrentPlayerDashboard().showEntrance().stream().toList());
                        for(Integer i : studentsIdsToMoveFromEntrance){
                            client.getActualToDoChoice().setChoiceParam(""+i);
                        }
                        synchronized ( client.getOutputStreamLock() ) {
                            client.getOutputStreamLock().notifyAll();
                        }
                        studentsIdsToMoveFromCard = new ArrayList<>();
                        studentsIdsToMoveFromEntrance = new ArrayList<>();
                    }
                }

            }
            else if(client.getActualToDoChoice() instanceof MinstrelChoice){
                if(numChoice == 0){
                    if(((ImageView)event.getSource()).getOpacity() == 1.0 && studentsIdsToMoveFromEntrance.size() < maxNumMinistrelStudents){
                        studentsIdsToMoveFromEntrance.add(playersDashboardView.get(client.getPlayer().getNickname()).entranceStudents.indexOf(chosenStudent));
                        ((ImageView)event.getSource()).setOpacity(0.5);
                    }
                    else{
                        ((ImageView)event.getSource()).setOpacity(1.0);
                        studentsIdsToMoveFromEntrance.remove(Integer.valueOf(playersDashboardView.get(client.getPlayer().getNickname()).entranceStudents.indexOf(chosenStudent)));
                    }
                }

            }
        }else
            throw new IllegalArgumentException("chooseStudent method called by an Object that is not an ImageView");
    }

    public void chooseIsland(MouseEvent event) throws NoIslandException {
        if ( event.getSource() instanceof Region ) {
            String islandID = ((Region) event.getSource()).getId();
            if(chosenStudentFromEntrance != -1){
                client.getActualToDoChoice().setChoiceParam(String.valueOf(chosenStudentFromEntrance));
                chosenStudentFromEntrance = -1;
            }
            if ( client.getActualToDoChoice() instanceof MoveStudentChoice
                    && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
                client.getActualToDoChoice().setChoiceParam("2");
                client.getActualToDoChoice().setChoiceParam(""+fromIslandToInt.get(islandID));
                Media media=new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
                MediaPlayer playBeep=new MediaPlayer(media);
                playBeep.play();
                synchronized ( client.getOutputStreamLock() ) {
                    client.getOutputStreamLock().notifyAll();
                }
            }
            else if ( client.getActualToDoChoice() instanceof MoveMotherNatureChoice ) {
                int chosenIsland = 0, numToMove;
                switch (islandID) {
                    case ("island1"):
                        chosenIsland = 0;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                    case ("island2"):
                        chosenIsland = 1;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                    case ("island3"):
                        chosenIsland = 2;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                    case ("island4"):
                        chosenIsland = 3;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                    case ("island5"):
                        chosenIsland = 4;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                    case ("island6"):
                        chosenIsland = 5;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                    case ("island7"):
                        chosenIsland = 6;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                    case ("island8"):
                        chosenIsland = 7;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                    case ("island9"):
                        chosenIsland = 8;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                    case ("island10"):
                        chosenIsland = 9;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                    case ("island11"):
                        chosenIsland = 10;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                    case ("island12"):
                        chosenIsland = 11;
                        numToMove = getNumMovementsBasedOnChosenIsland(chosenIsland);
                        client.getActualToDoChoice().setChoiceParam("" + numToMove);
                        break;
                }
                if(client.getMatchView().showCurrentPlayerDashboard().getCurrentCard().getMovementValue()
                        >=getNumMovementsBasedOnChosenIsland(chosenIsland)) {
                    Media media = new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
                    MediaPlayer playBeep = new MediaPlayer(media);
                    playBeep.play();
                }
                synchronized (client.getOutputStreamLock()) {
                    client.getOutputStreamLock().notifyAll();
                }
            }else if(client.getActualToDoChoice() instanceof GrannyGrassChoice){
                submitBlockOnIsland(event);
            }else if(client.getActualToDoChoice() instanceof MerchantChoice)
            {
                submitIslandForMerchant(event);
            }
            else if(client.getActualToDoChoice() instanceof HeraldChoice){
                ((HeraldChoice) client.getActualToDoChoice()).setIslandPositionTmp(client.getMatchView().getIslandPositions());
                ((HeraldChoice) client.getActualToDoChoice()).setChoiceParam(String.valueOf(fromIslandToInt.get(islandID)));
                synchronized (client.getOutputStreamLock()) {
                    client.getOutputStreamLock().notifyAll();
                }
            }
        }else
            throw new IllegalArgumentException("chooseIsland method called by an Object that is not an ImageView");
    }


    public void chooseCloud(MouseEvent event){
        String cloudID = ((Region) event.getSource()).getId();
        Map<String, Integer> fromCloudToInteger=new HashMap<>(){{
            put("cloudRegion1", 0);
            put("cloudRegion2", 1);
            put("cloudRegion3", 2);
            put("cloudRegion4", 3);
        }};
        if(client.getActualToDoChoice() instanceof CloudChoice){
            client.getActualToDoChoice().setChoiceParam(""+fromCloudToInteger.get(cloudID));

            if(client.getMatchView().getClouds().get(fromCloudToInteger.get(cloudID)).getStudentsOnCloud().size()>0){
                Media media = new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
                MediaPlayer playBeep = new MediaPlayer(media);
                playBeep.play();
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
        if(client.getActualToDoChoice() instanceof MoveStudentChoice){
            if(chosenStudentFromEntrance != -1){
                client.getActualToDoChoice().setChoiceParam(String.valueOf(chosenStudentFromEntrance));
                chosenStudentFromEntrance = -1;
            }
        }
        if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
            client.getActualToDoChoice().setChoiceParam(String.valueOf(chosenStudentFromEntrance));
            client.getActualToDoChoice().setChoiceParam("1");
            Media media=new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
            MediaPlayer playBeep=new MediaPlayer(media);
            playBeep.play();
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }
        else if(client.getActualToDoChoice() instanceof ThiefChoice || client.getActualToDoChoice() instanceof MushroomCollectorChoice){
            client.getActualToDoChoice().setChoiceParam(String.valueOf(Color.GREEN.ordinal()+1));
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }
        else if (client.getActualToDoChoice() instanceof MinstrelChoice) {
            submitMinistrelParams(Color.GREEN);
        }

    }

    public void chooseRedDR(MouseEvent event) {
        if(client.getActualToDoChoice() instanceof MoveStudentChoice){
            if(chosenStudentFromEntrance != -1){
                client.getActualToDoChoice().setChoiceParam(String.valueOf(chosenStudentFromEntrance));
                chosenStudentFromEntrance = -1;
            }
        }
        if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
            client.getActualToDoChoice().setChoiceParam(String.valueOf(chosenStudentFromEntrance));
            client.getActualToDoChoice().setChoiceParam("1");
            Media media=new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
            MediaPlayer playBeep=new MediaPlayer(media);
            playBeep.play();
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }
        else if(client.getActualToDoChoice() instanceof ThiefChoice || client.getActualToDoChoice() instanceof MushroomCollectorChoice){
            client.getActualToDoChoice().setChoiceParam(String.valueOf(Color.RED.ordinal()+1));
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }
        else if (client.getActualToDoChoice() instanceof MinstrelChoice) {
            submitMinistrelParams(Color.RED);
        }

    }

    public void chooseYellowDR(MouseEvent event) {
        if(client.getActualToDoChoice() instanceof MoveStudentChoice){
            if(chosenStudentFromEntrance != -1){
                client.getActualToDoChoice().setChoiceParam(String.valueOf(chosenStudentFromEntrance));
                chosenStudentFromEntrance = -1;
            }
        }
        if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
            client.getActualToDoChoice().setChoiceParam(String.valueOf(chosenStudentFromEntrance));
            client.getActualToDoChoice().setChoiceParam("1");
            Media media=new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
            MediaPlayer playBeep=new MediaPlayer(media);
            playBeep.play();
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }
        else if(client.getActualToDoChoice() instanceof ThiefChoice || client.getActualToDoChoice() instanceof MushroomCollectorChoice){
            client.getActualToDoChoice().setChoiceParam(String.valueOf(Color.YELLOW.ordinal()+1));
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }
        else if (client.getActualToDoChoice() instanceof MinstrelChoice) {
            submitMinistrelParams(Color.YELLOW);
        }

    }

    public void choosePinkDR(MouseEvent event) {
        if(client.getActualToDoChoice() instanceof MoveStudentChoice){
            if(chosenStudentFromEntrance != -1){
                client.getActualToDoChoice().setChoiceParam(String.valueOf(chosenStudentFromEntrance));
                chosenStudentFromEntrance = -1;
            }
        }
        if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
            client.getActualToDoChoice().setChoiceParam(String.valueOf(chosenStudentFromEntrance));
            client.getActualToDoChoice().setChoiceParam("1");
            Media media=new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
            MediaPlayer playBeep=new MediaPlayer(media);
            playBeep.play();
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }
        else if(client.getActualToDoChoice() instanceof ThiefChoice || client.getActualToDoChoice() instanceof MushroomCollectorChoice){
            client.getActualToDoChoice().setChoiceParam(String.valueOf(Color.PINK.ordinal()+1));
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }
        else if (client.getActualToDoChoice() instanceof MinstrelChoice) {
            submitMinistrelParams(Color.PINK);
        }

    }

    /** Method called when the BlueDR has been clicked.
     *  The first statement of the "if" it's called when the player move a student from the entrance.
     *  The second one when a figureCard is played (DA FARE)
     *
     * @param event
     */
    public void chooseBlueDR(MouseEvent event){
        if(client.getActualToDoChoice() instanceof MoveStudentChoice){
            if(chosenStudentFromEntrance != -1){
                client.getActualToDoChoice().setChoiceParam(String.valueOf(chosenStudentFromEntrance));
                chosenStudentFromEntrance = -1;
            }
        }
        if ( client.getActualToDoChoice() instanceof MoveStudentChoice && ((MoveStudentChoice) client.getActualToDoChoice()).getChoisePhase() == 1 ) {
            client.getActualToDoChoice().setChoiceParam("1");
            Media media=new Media(getClass().getResource("/client/beep.mp3").toExternalForm());
            MediaPlayer playBeep=new MediaPlayer(media);
            playBeep.play();
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }
        else if(client.getActualToDoChoice() instanceof ThiefChoice || client.getActualToDoChoice() instanceof MushroomCollectorChoice){
            client.getActualToDoChoice().setChoiceParam(String.valueOf(Color.BLUE.ordinal()+1));
            synchronized ( client.getOutputStreamLock() ) {
                client.getOutputStreamLock().notifyAll();
            }
        }

        else if (client.getActualToDoChoice() instanceof MinstrelChoice) {
            submitMinistrelParams(Color.BLUE);
        }

    }

    public void submitMinistrelParams(Color color) {
        int counter = 0;
        if(studentsIdsToMoveFromEntrance.size() != 0){
            if(numChoice == 0)
                numChoice =1;
        }
        if(numChoice == 1){
            int tmp = 0;
            boolean flag = false;
            for(Student s : client.getMatchView().showCurrentPlayerDashboard().showDiningRoom()){
                if(s.getColor() == color){
                    if(!studentsIdsToMoveFromDiningRoom.contains(tmp)){
                        studentsIdsToMoveFromDiningRoom.add(tmp);
                        if(playersDashboardView.get(client.getPlayer().getNickname()).diningRoomStudents.get(color).get(0).getOpacity() == 1.0){
                            playersDashboardView.get(client.getPlayer().getNickname()).diningRoomStudents.get(color).get(0).setOpacity(0.5);
                        }
                        else if (playersDashboardView.get(client.getPlayer().getNickname()).diningRoomStudents.get(color).get(1).getOpacity() == 1.0){
                            playersDashboardView.get(client.getPlayer().getNickname()).diningRoomStudents.get(color).get(1).setOpacity(0.5);
                        }
                        flag = true;
                        break;
                    }
                }
                tmp++;
            }
            if(flag == false){
                try {
                    if(client.getMatchView().showCurrentPlayerDashboard().getStudentsNumInDR(color) > 0){
                        tmp = 0;
                        for(Student s : client.getMatchView().showCurrentPlayerDashboard().showDiningRoom()){
                            if(s.getColor() == color){
                                if(studentsIdsToMoveFromDiningRoom.contains(tmp)){
                                    studentsIdsToMoveFromDiningRoom.remove(Integer.valueOf(tmp));
                                    if(playersDashboardView.get(client.getPlayer().getNickname()).diningRoomStudents.get(color).get(1).getOpacity() == 0.5){
                                        playersDashboardView.get(client.getPlayer().getNickname()).diningRoomStudents.get(color).get(1).setOpacity(1.0);
                                    }
                                    else if(playersDashboardView.get(client.getPlayer().getNickname()).diningRoomStudents.get(color).get(0).getOpacity() == 0.5){
                                        playersDashboardView.get(client.getPlayer().getNickname()).diningRoomStudents.get(color).get(0).setOpacity(1.0);
                                    }
                                    flag = true;
                                    break;
                                }
                            }
                            tmp++;
                        }
                    }
                } catch (WrongColorException e) {
                    throw new RuntimeException(e);
                }
            }
            if(studentsIdsToMoveFromDiningRoom.size() == studentsIdsToMoveFromEntrance.size()){
                List<Student> tmpList = new ArrayList<>();
                for(int i = 0; i < studentsIdsToMoveFromDiningRoom.size();i++){
                    tmpList.add(client.getMatchView().showCurrentPlayerDashboard().showDiningRoom().get(studentsIdsToMoveFromDiningRoom.get(i)));
                }
                ((MinstrelChoice) client.getActualToDoChoice()).setStudentsFromDr(tmpList);
                tmpList = new ArrayList<>();
                for(int i = 0; i < studentsIdsToMoveFromDiningRoom.size();i++){
                    tmpList.add(client.getMatchView().showCurrentPlayerDashboard().showEntrance().stream().toList().get(studentsIdsToMoveFromEntrance.get(i)));
                }
                ((MinstrelChoice) client.getActualToDoChoice()).setStudentsFromEntrance(tmpList);
                numChoice = 0;
                synchronized ( client.getOutputStreamLock() ) {
                    client.getOutputStreamLock().notifyAll();
                }
                studentsIdsToMoveFromDiningRoom = new ArrayList<>();
                studentsIdsToMoveFromEntrance = new ArrayList<>();
            }
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

    /**Puts all ImageView of entrance of dashboard 2 students into an ArrayList
     */
    private ArrayList<ImageView> initializeEntranceStudentsD2() {
        ArrayList<ImageView> entranceStudentsD2;
        entranceStudentsD2 = new ArrayList<>();
        entranceStudentsD2.add(D2EntranceStudent1);
        entranceStudentsD2.add(D2EntranceStudent2);
        entranceStudentsD2.add(D2EntranceStudent3);
        entranceStudentsD2.add(D2EntranceStudent4);
        entranceStudentsD2.add(D2EntranceStudent5);
        entranceStudentsD2.add(D2EntranceStudent6);
        entranceStudentsD2.add(D2EntranceStudent7);
        entranceStudentsD2.add(D2EntranceStudent8);
        entranceStudentsD2.add(D2EntranceStudent9);
        return entranceStudentsD2;
    }

    /**Puts all ImageView of DR students of dashboard 2 into an ArrayList and set them invisible
     */
    private Map<Color, ArrayList<ImageView>> initializeDiningRoomStudentsD2() {
        Map<Color, ArrayList<ImageView>> diningRoomStudentsD2;
        diningRoomStudentsD2 = new HashMap<>();
        //Green students
        diningRoomStudentsD2.put(Color.GREEN, new ArrayList<>());
        diningRoomStudentsD2.get(Color.GREEN).add(D2GreenDRStudent1);
        diningRoomStudentsD2.get(Color.GREEN).add(D2GreenDRStudent2);
        diningRoomStudentsD2.get(Color.GREEN).add(D2GreenDRStudent3);
        diningRoomStudentsD2.get(Color.GREEN).add(D2GreenDRStudent4);
        diningRoomStudentsD2.get(Color.GREEN).add(D2GreenDRStudent5);
        diningRoomStudentsD2.get(Color.GREEN).add(D2GreenDRStudent6);
        diningRoomStudentsD2.get(Color.GREEN).add(D2GreenDRStudent7);
        diningRoomStudentsD2.get(Color.GREEN).add(D2GreenDRStudent8);
        diningRoomStudentsD2.get(Color.GREEN).add(D2GreenDRStudent9);
        diningRoomStudentsD2.get(Color.GREEN).add(D2GreenDRStudent10);
        //Red students
        diningRoomStudentsD2.put(Color.RED, new ArrayList<>());
        diningRoomStudentsD2.get(Color.RED).add(D2RedDRStudent1);
        diningRoomStudentsD2.get(Color.RED).add(D2RedDRStudent2);
        diningRoomStudentsD2.get(Color.RED).add(D2RedDRStudent3);
        diningRoomStudentsD2.get(Color.RED).add(D2RedDRStudent4);
        diningRoomStudentsD2.get(Color.RED).add(D2RedDRStudent5);
        diningRoomStudentsD2.get(Color.RED).add(D2RedDRStudent6);
        diningRoomStudentsD2.get(Color.RED).add(D2RedDRStudent7);
        diningRoomStudentsD2.get(Color.RED).add(D2RedDRStudent8);
        diningRoomStudentsD2.get(Color.RED).add(D2RedDRStudent9);
        diningRoomStudentsD2.get(Color.RED).add(D2RedDRStudent10);
        //Yellow students
        diningRoomStudentsD2.put(Color.YELLOW, new ArrayList<>());
        diningRoomStudentsD2.get(Color.YELLOW).add(D2YellowDRStudent1);
        diningRoomStudentsD2.get(Color.YELLOW).add(D2YellowDRStudent2);
        diningRoomStudentsD2.get(Color.YELLOW).add(D2YellowDRStudent3);
        diningRoomStudentsD2.get(Color.YELLOW).add(D2YellowDRStudent4);
        diningRoomStudentsD2.get(Color.YELLOW).add(D2YellowDRStudent5);
        diningRoomStudentsD2.get(Color.YELLOW).add(D2YellowDRStudent6);
        diningRoomStudentsD2.get(Color.YELLOW).add(D2YellowDRStudent7);
        diningRoomStudentsD2.get(Color.YELLOW).add(D2YellowDRStudent8);
        diningRoomStudentsD2.get(Color.YELLOW).add(D2YellowDRStudent9);
        diningRoomStudentsD2.get(Color.YELLOW).add(D2YellowDRStudent10);
        //Pink students
        diningRoomStudentsD2.put(Color.PINK, new ArrayList<>());
        diningRoomStudentsD2.get(Color.PINK).add(D2PinkDRStudent1);
        diningRoomStudentsD2.get(Color.PINK).add(D2PinkDRStudent2);
        diningRoomStudentsD2.get(Color.PINK).add(D2PinkDRStudent3);
        diningRoomStudentsD2.get(Color.PINK).add(D2PinkDRStudent4);
        diningRoomStudentsD2.get(Color.PINK).add(D2PinkDRStudent5);
        diningRoomStudentsD2.get(Color.PINK).add(D2PinkDRStudent6);
        diningRoomStudentsD2.get(Color.PINK).add(D2PinkDRStudent7);
        diningRoomStudentsD2.get(Color.PINK).add(D2PinkDRStudent8);
        diningRoomStudentsD2.get(Color.PINK).add(D2PinkDRStudent9);
        diningRoomStudentsD2.get(Color.PINK).add(D2PinkDRStudent10);
        //Blue students
        diningRoomStudentsD2.put(Color.BLUE, new ArrayList<>());
        diningRoomStudentsD2.get(Color.BLUE).add(D2BlueDRStudent1);
        diningRoomStudentsD2.get(Color.BLUE).add(D2BlueDRStudent2);
        diningRoomStudentsD2.get(Color.BLUE).add(D2BlueDRStudent3);
        diningRoomStudentsD2.get(Color.BLUE).add(D2BlueDRStudent4);
        diningRoomStudentsD2.get(Color.BLUE).add(D2BlueDRStudent5);
        diningRoomStudentsD2.get(Color.BLUE).add(D2BlueDRStudent6);
        diningRoomStudentsD2.get(Color.BLUE).add(D2BlueDRStudent7);
        diningRoomStudentsD2.get(Color.BLUE).add(D2BlueDRStudent8);
        diningRoomStudentsD2.get(Color.BLUE).add(D2BlueDRStudent9);
        diningRoomStudentsD2.get(Color.BLUE).add(D2BlueDRStudent10);

        for ( Color c: Color.values() ) {
            for ( ImageView i : diningRoomStudentsD2.get(c) )
                i.setVisible(false);
        }

        return diningRoomStudentsD2;
    }

    /**Puts all ImageView of masters of dashboard 2 into a Map and set them invisible
     */
    private Map<Color, ImageView> initializeMastersD2() {
        Map<Color, ImageView> masters = new HashMap<>();

        masters.put(Color.GREEN, D2GreenMaster);
        masters.put(Color.RED, D2RedMaster);
        masters.put(Color.YELLOW, D2YellowMaster);
        masters.put(Color.PINK, D2PinkMaster);
        masters.put(Color.BLUE, D2BlueMaster);

        D2GreenMaster.setVisible(false);
        D2RedMaster.setVisible(false);
        D2YellowMaster.setVisible(false);
        D2PinkMaster.setVisible(false);
        D2BlueMaster.setVisible(false);

        return masters;
    }

    /**Put all towers image of dashboard 2 in an ArrayList
     */
    private ArrayList<ImageView> initializeTowersD2( TowerColor towerColor ) {
        ArrayList<ImageView> towersList = new ArrayList<>();
        towersList.add(D2BlackTower1);
        towersList.add(D2BlackTower2);
        towersList.add(D2BlackTower3);
        towersList.add(D2BlackTower4);
        towersList.add(D2BlackTower5);
        towersList.add(D2BlackTower6);
        towersList.add(D2BlackTower7);
        towersList.add(D2BlackTower8);

        for ( ImageView tower : towersList ) {
            tower.setImage(towersImage.get(towerColor));
        }

        return towersList;
    }

    /**Puts all ImageView of entrance of dashboard 3 students into an ArrayList
     */
    private ArrayList<ImageView> initializeEntranceStudentsD3() {
        ArrayList<ImageView> entranceStudentsD3;
        entranceStudentsD3 = new ArrayList<>();
        entranceStudentsD3.add(D3EntranceStudent1);
        entranceStudentsD3.add(D3EntranceStudent2);
        entranceStudentsD3.add(D3EntranceStudent3);
        entranceStudentsD3.add(D3EntranceStudent4);
        entranceStudentsD3.add(D3EntranceStudent5);
        entranceStudentsD3.add(D3EntranceStudent6);
        entranceStudentsD3.add(D3EntranceStudent7);
        entranceStudentsD3.add(D3EntranceStudent8);
        entranceStudentsD3.add(D3EntranceStudent9);
        return entranceStudentsD3;
    }

    /**Puts all ImageView of DR students of dashboard 3 into an ArrayList and set them invisible
     */
    private Map<Color, ArrayList<ImageView>> initializeDiningRoomStudentsD3() {
        Map<Color, ArrayList<ImageView>> diningRoomStudentsD3;
        diningRoomStudentsD3 = new HashMap<>();
        //Green students
        diningRoomStudentsD3.put(Color.GREEN, new ArrayList<>());
        diningRoomStudentsD3.get(Color.GREEN).add(D3GreenDRStudent1);
        diningRoomStudentsD3.get(Color.GREEN).add(D3GreenDRStudent2);
        diningRoomStudentsD3.get(Color.GREEN).add(D3GreenDRStudent3);
        diningRoomStudentsD3.get(Color.GREEN).add(D3GreenDRStudent4);
        diningRoomStudentsD3.get(Color.GREEN).add(D3GreenDRStudent5);
        diningRoomStudentsD3.get(Color.GREEN).add(D3GreenDRStudent6);
        diningRoomStudentsD3.get(Color.GREEN).add(D3GreenDRStudent7);
        diningRoomStudentsD3.get(Color.GREEN).add(D3GreenDRStudent8);
        diningRoomStudentsD3.get(Color.GREEN).add(D3GreenDRStudent9);
        diningRoomStudentsD3.get(Color.GREEN).add(D3GreenDRStudent10);
        //Red students
        diningRoomStudentsD3.put(Color.RED, new ArrayList<>());
        diningRoomStudentsD3.get(Color.RED).add(D3RedDRStudent1);
        diningRoomStudentsD3.get(Color.RED).add(D3RedDRStudent2);
        diningRoomStudentsD3.get(Color.RED).add(D3RedDRStudent3);
        diningRoomStudentsD3.get(Color.RED).add(D3RedDRStudent4);
        diningRoomStudentsD3.get(Color.RED).add(D3RedDRStudent5);
        diningRoomStudentsD3.get(Color.RED).add(D3RedDRStudent6);
        diningRoomStudentsD3.get(Color.RED).add(D3RedDRStudent7);
        diningRoomStudentsD3.get(Color.RED).add(D3RedDRStudent8);
        diningRoomStudentsD3.get(Color.RED).add(D3RedDRStudent9);
        diningRoomStudentsD3.get(Color.RED).add(D3RedDRStudent10);
        //Yellow students
        diningRoomStudentsD3.put(Color.YELLOW, new ArrayList<>());
        diningRoomStudentsD3.get(Color.YELLOW).add(D3YellowDRStudent1);
        diningRoomStudentsD3.get(Color.YELLOW).add(D3YellowDRStudent2);
        diningRoomStudentsD3.get(Color.YELLOW).add(D3YellowDRStudent3);
        diningRoomStudentsD3.get(Color.YELLOW).add(D3YellowDRStudent4);
        diningRoomStudentsD3.get(Color.YELLOW).add(D3YellowDRStudent5);
        diningRoomStudentsD3.get(Color.YELLOW).add(D3YellowDRStudent6);
        diningRoomStudentsD3.get(Color.YELLOW).add(D3YellowDRStudent7);
        diningRoomStudentsD3.get(Color.YELLOW).add(D3YellowDRStudent8);
        diningRoomStudentsD3.get(Color.YELLOW).add(D3YellowDRStudent9);
        diningRoomStudentsD3.get(Color.YELLOW).add(D3YellowDRStudent10);
        //Pink students
        diningRoomStudentsD3.put(Color.PINK, new ArrayList<>());
        diningRoomStudentsD3.get(Color.PINK).add(D3PinkDRStudent1);
        diningRoomStudentsD3.get(Color.PINK).add(D3PinkDRStudent2);
        diningRoomStudentsD3.get(Color.PINK).add(D3PinkDRStudent3);
        diningRoomStudentsD3.get(Color.PINK).add(D3PinkDRStudent4);
        diningRoomStudentsD3.get(Color.PINK).add(D3PinkDRStudent5);
        diningRoomStudentsD3.get(Color.PINK).add(D3PinkDRStudent6);
        diningRoomStudentsD3.get(Color.PINK).add(D3PinkDRStudent7);
        diningRoomStudentsD3.get(Color.PINK).add(D3PinkDRStudent8);
        diningRoomStudentsD3.get(Color.PINK).add(D3PinkDRStudent9);
        diningRoomStudentsD3.get(Color.PINK).add(D3PinkDRStudent10);
        //Blue students
        diningRoomStudentsD3.put(Color.BLUE, new ArrayList<>());
        diningRoomStudentsD3.get(Color.BLUE).add(D3BlueDRStudent1);
        diningRoomStudentsD3.get(Color.BLUE).add(D3BlueDRStudent2);
        diningRoomStudentsD3.get(Color.BLUE).add(D3BlueDRStudent3);
        diningRoomStudentsD3.get(Color.BLUE).add(D3BlueDRStudent4);
        diningRoomStudentsD3.get(Color.BLUE).add(D3BlueDRStudent5);
        diningRoomStudentsD3.get(Color.BLUE).add(D3BlueDRStudent6);
        diningRoomStudentsD3.get(Color.BLUE).add(D3BlueDRStudent7);
        diningRoomStudentsD3.get(Color.BLUE).add(D3BlueDRStudent8);
        diningRoomStudentsD3.get(Color.BLUE).add(D3BlueDRStudent9);
        diningRoomStudentsD3.get(Color.BLUE).add(D3BlueDRStudent10);

        for ( Color c: Color.values() ) {
            for ( ImageView i : diningRoomStudentsD3.get(c) )
                i.setVisible(false);
        }

        return diningRoomStudentsD3;
    }

    /**Puts all ImageView of masters of dashboard 3 into a Map and set them invisible
     */
    private Map<Color, ImageView> initializeMastersD3() {
        Map<Color, ImageView> masters = new HashMap<>();

        masters.put(Color.GREEN, D3GreenMaster);
        masters.put(Color.RED, D3RedMaster);
        masters.put(Color.YELLOW, D3YellowMaster);
        masters.put(Color.PINK, D3PinkMaster);
        masters.put(Color.BLUE, D3BlueMaster);

        D3GreenMaster.setVisible(false);
        D3RedMaster.setVisible(false);
        D3YellowMaster.setVisible(false);
        D3PinkMaster.setVisible(false);
        D3BlueMaster.setVisible(false);

        return masters;
    }

    /**Put all towers image of dashboard 3 in an ArrayList
     */
    private ArrayList<ImageView> initializeTowersD3( TowerColor towerColor ) {
        ArrayList<ImageView> towersList = new ArrayList<>();
        towersList.add(D3BlackTower1);
        towersList.add(D3BlackTower2);
        towersList.add(D3BlackTower3);
        towersList.add(D3BlackTower4);
        towersList.add(D3BlackTower5);
        towersList.add(D3BlackTower6);
        towersList.add(D3BlackTower7);
        towersList.add(D3BlackTower8);

        for ( ImageView tower : towersList ) {
            tower.setImage(towersImage.get(towerColor));
        }

        return towersList;
    }

    /**Puts all ImageView of entrance of dashboard 4 students into an ArrayList
     */
    private ArrayList<ImageView> initializeEntranceStudentsD4() {
        ArrayList<ImageView> entranceStudentsD4;
        entranceStudentsD4 = new ArrayList<>();
        entranceStudentsD4.add(D4EntranceStudent1);
        entranceStudentsD4.add(D4EntranceStudent2);
        entranceStudentsD4.add(D4EntranceStudent3);
        entranceStudentsD4.add(D4EntranceStudent4);
        entranceStudentsD4.add(D4EntranceStudent5);
        entranceStudentsD4.add(D4EntranceStudent6);
        entranceStudentsD4.add(D4EntranceStudent7);
        entranceStudentsD4.add(D4EntranceStudent8);
        entranceStudentsD4.add(D4EntranceStudent9);
        return entranceStudentsD4;
    }

    /**Puts all ImageView of DR students of dashboard 4 into an ArrayList and set them invisible
     */
    private Map<Color, ArrayList<ImageView>> initializeDiningRoomStudentsD4() {
        Map<Color, ArrayList<ImageView>> diningRoomStudentsD4;
        diningRoomStudentsD4 = new HashMap<>();
        //Green students
        diningRoomStudentsD4.put(Color.GREEN, new ArrayList<>());
        diningRoomStudentsD4.get(Color.GREEN).add(D4GreenDRStudent1);
        diningRoomStudentsD4.get(Color.GREEN).add(D4GreenDRStudent2);
        diningRoomStudentsD4.get(Color.GREEN).add(D4GreenDRStudent3);
        diningRoomStudentsD4.get(Color.GREEN).add(D4GreenDRStudent4);
        diningRoomStudentsD4.get(Color.GREEN).add(D4GreenDRStudent5);
        diningRoomStudentsD4.get(Color.GREEN).add(D4GreenDRStudent6);
        diningRoomStudentsD4.get(Color.GREEN).add(D4GreenDRStudent7);
        diningRoomStudentsD4.get(Color.GREEN).add(D4GreenDRStudent8);
        diningRoomStudentsD4.get(Color.GREEN).add(D4GreenDRStudent9);
        diningRoomStudentsD4.get(Color.GREEN).add(D4GreenDRStudent10);
        //Red students
        diningRoomStudentsD4.put(Color.RED, new ArrayList<>());
        diningRoomStudentsD4.get(Color.RED).add(D4RedDRStudent1);
        diningRoomStudentsD4.get(Color.RED).add(D4RedDRStudent2);
        diningRoomStudentsD4.get(Color.RED).add(D4RedDRStudent3);
        diningRoomStudentsD4.get(Color.RED).add(D4RedDRStudent4);
        diningRoomStudentsD4.get(Color.RED).add(D4RedDRStudent5);
        diningRoomStudentsD4.get(Color.RED).add(D4RedDRStudent6);
        diningRoomStudentsD4.get(Color.RED).add(D4RedDRStudent7);
        diningRoomStudentsD4.get(Color.RED).add(D4RedDRStudent8);
        diningRoomStudentsD4.get(Color.RED).add(D4RedDRStudent9);
        diningRoomStudentsD4.get(Color.RED).add(D4RedDRStudent10);
        //Yellow students
        diningRoomStudentsD4.put(Color.YELLOW, new ArrayList<>());
        diningRoomStudentsD4.get(Color.YELLOW).add(D4YellowDRStudent1);
        diningRoomStudentsD4.get(Color.YELLOW).add(D4YellowDRStudent2);
        diningRoomStudentsD4.get(Color.YELLOW).add(D4YellowDRStudent3);
        diningRoomStudentsD4.get(Color.YELLOW).add(D4YellowDRStudent4);
        diningRoomStudentsD4.get(Color.YELLOW).add(D4YellowDRStudent5);
        diningRoomStudentsD4.get(Color.YELLOW).add(D4YellowDRStudent6);
        diningRoomStudentsD4.get(Color.YELLOW).add(D4YellowDRStudent7);
        diningRoomStudentsD4.get(Color.YELLOW).add(D4YellowDRStudent8);
        diningRoomStudentsD4.get(Color.YELLOW).add(D4YellowDRStudent9);
        diningRoomStudentsD4.get(Color.YELLOW).add(D4YellowDRStudent10);
        //Pink students
        diningRoomStudentsD4.put(Color.PINK, new ArrayList<>());
        diningRoomStudentsD4.get(Color.PINK).add(D4PinkDRStudent1);
        diningRoomStudentsD4.get(Color.PINK).add(D4PinkDRStudent2);
        diningRoomStudentsD4.get(Color.PINK).add(D4PinkDRStudent3);
        diningRoomStudentsD4.get(Color.PINK).add(D4PinkDRStudent4);
        diningRoomStudentsD4.get(Color.PINK).add(D4PinkDRStudent5);
        diningRoomStudentsD4.get(Color.PINK).add(D4PinkDRStudent6);
        diningRoomStudentsD4.get(Color.PINK).add(D4PinkDRStudent7);
        diningRoomStudentsD4.get(Color.PINK).add(D4PinkDRStudent8);
        diningRoomStudentsD4.get(Color.PINK).add(D4PinkDRStudent9);
        diningRoomStudentsD4.get(Color.PINK).add(D4PinkDRStudent10);
        //Blue students
        diningRoomStudentsD4.put(Color.BLUE, new ArrayList<>());
        diningRoomStudentsD4.get(Color.BLUE).add(D4BlueDRStudent1);
        diningRoomStudentsD4.get(Color.BLUE).add(D4BlueDRStudent2);
        diningRoomStudentsD4.get(Color.BLUE).add(D4BlueDRStudent3);
        diningRoomStudentsD4.get(Color.BLUE).add(D4BlueDRStudent4);
        diningRoomStudentsD4.get(Color.BLUE).add(D4BlueDRStudent5);
        diningRoomStudentsD4.get(Color.BLUE).add(D4BlueDRStudent6);
        diningRoomStudentsD4.get(Color.BLUE).add(D4BlueDRStudent7);
        diningRoomStudentsD4.get(Color.BLUE).add(D4BlueDRStudent8);
        diningRoomStudentsD4.get(Color.BLUE).add(D4BlueDRStudent9);
        diningRoomStudentsD4.get(Color.BLUE).add(D4BlueDRStudent10);

        for ( Color c: Color.values() ) {
            for ( ImageView i : diningRoomStudentsD4.get(c) )
                i.setVisible(false);
        }

        return diningRoomStudentsD4;
    }

    /**Puts all ImageView of masters of dashboard 4 into a Map and set them invisible
     */
    private Map<Color, ImageView> initializeMastersD4() {
        Map<Color, ImageView> masters = new HashMap<>();

        masters.put(Color.GREEN, D4GreenMaster);
        masters.put(Color.RED, D4RedMaster);
        masters.put(Color.YELLOW, D4YellowMaster);
        masters.put(Color.PINK, D4PinkMaster);
        masters.put(Color.BLUE, D4BlueMaster);

        D4GreenMaster.setVisible(false);
        D4RedMaster.setVisible(false);
        D4YellowMaster.setVisible(false);
        D4PinkMaster.setVisible(false);
        D4BlueMaster.setVisible(false);

        return masters;
    }

    /**Put all towers image of dashboard 4 in an ArrayList
     */
    private ArrayList<ImageView> initializeTowersD4( TowerColor towerColor ) {
        ArrayList<ImageView> towersList = new ArrayList<>();
        towersList.add(D4BlackTower1);
        towersList.add(D4BlackTower2);
        towersList.add(D4BlackTower3);
        towersList.add(D4BlackTower4);
        towersList.add(D4BlackTower5);
        towersList.add(D4BlackTower6);
        towersList.add(D4BlackTower7);
        towersList.add(D4BlackTower8);

        for ( ImageView tower : towersList ) {
            tower.setImage(towersImage.get(towerColor));
        }

        return towersList;
    }

    /**
     * Initializes each DashboardView with the images of its Entrance, DiningRoom, Masters and Towers.
     * Puts each one of this DashboardView into the Map playersDashboardView with the player nickname as key.
     * @param clientPlayer
     * The Player who has started the client
     * @param otherPlayers
     * All others players
     */
    public void inizializeAllDasboards( Player clientPlayer, List<Player> otherPlayers ) {

        TowerColor playerTowerColor;
        DashboardView playerDashboard;

        playerTowerColor = client.getMatchView().showAllDashboards().get(clientPlayer.getNickname()).getTowerColor();
        playerDashboard = new DashboardView(initializeEntranceStudentsD1(), initializeDiningRoomStudentsD1(), initializeMastersD1(), initializeTowersD1(playerTowerColor));
       playersDashboardView.put(clientPlayer.getNickname(), playerDashboard);
        playersDashboardView.get(clientPlayer.getNickname()).setCurrentCard(cardDb1);


        playerTowerColor = client.getMatchView().showAllDashboards().get(otherPlayers.get(0).getNickname()).getTowerColor();
        playerDashboard = new DashboardView(initializeEntranceStudentsD2(), initializeDiningRoomStudentsD2(), initializeMastersD2(), initializeTowersD2(playerTowerColor));
        playersDashboardView.put(otherPlayers.get(0).getNickname(), playerDashboard);
        playersDashboardView.get(otherPlayers.get(0).getNickname()).setNicknameText(nicknameDB2);
        playersDashboardView.get(otherPlayers.get(0).getNickname()).setCurrentCard(cardDb2);
        playersDashboardView.get(otherPlayers.get(0).getNickname()).setNickname(otherPlayers.get(0).getNickname());

        if ( client.getMatchView().showAllPlayers().size()>=3 ) {
            playerTowerColor = client.getMatchView().showAllDashboards().get(otherPlayers.get(1).getNickname()).getTowerColor();
            playerDashboard = new DashboardView(initializeEntranceStudentsD3(), initializeDiningRoomStudentsD3(), initializeMastersD3(), initializeTowersD3(playerTowerColor));
            playersDashboardView.put(otherPlayers.get(1).getNickname(), playerDashboard);
            playersDashboardView.get(otherPlayers.get(1).getNickname()).setNicknameText(nicknameDB3);
            playersDashboardView.get(otherPlayers.get(1).getNickname()).setCurrentCard(cardDb3);
            playersDashboardView.get(otherPlayers.get(1).getNickname()).setNickname(otherPlayers.get(1).getNickname());
        }

        if ( client.getMatchView().showAllPlayers().size()==4 ) {
            playerTowerColor = client.getMatchView().showAllDashboards().get(otherPlayers.get(2).getNickname()).getTowerColor();
            playerDashboard = new DashboardView(initializeEntranceStudentsD4(), initializeDiningRoomStudentsD4(), initializeMastersD4(), initializeTowersD4(playerTowerColor));
            playersDashboardView.put(otherPlayers.get(2).getNickname(), playerDashboard);
            playersDashboardView.get(otherPlayers.get(2).getNickname()).setNicknameText(nicknameDB4);
            playersDashboardView.get(otherPlayers.get(2).getNickname()).setCurrentCard(cardDb4);
            playersDashboardView.get(otherPlayers.get(2).getNickname()).setNickname(otherPlayers.get(2).getNickname());
        }


    }

    /**Update all dashboards in the GUI
     */
    public void updateDashboard() {
        Map<String, Dashboard> dashboardsData = client.getMatchView().showAllDashboards();
        for ( Player player: client.getMatchView().showAllPlayers() ) {
            String playerNickname = player.getNickname();
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
        private Text nickname;

        private ImageView currentCard;


        public DashboardView(ArrayList<ImageView> entranceStudents, Map<Color, ArrayList<ImageView>> diningRoomStudents, Map<Color, ImageView> master, ArrayList<ImageView> tower ) {
            this.entranceStudents=entranceStudents;
            this.diningRoomStudents=diningRoomStudents;
            this.master=master;
            this.tower=tower;
        }

        public void setNicknameText(Text nicknameBox) {
            this.nickname=nicknameBox;
        }

        public void setNickname(String nickname) {
            this.nickname.setText(nickname);
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
            entranceStudents.get(studentNumber).setOpacity(1);
        }

        public void setDRStudentsNumber(int studentsNumber, Color drColor) {
            int i;
            for ( i=0; i<studentsNumber; i++ ) {
                diningRoomStudents.get(drColor).get(i).setVisible(true);
                diningRoomStudents.get(drColor).get(i).setOpacity(1);
            }
            while (i<10) {
                diningRoomStudents.get(drColor).get(i).setOpacity(1);
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

    public void showMessage(String message) {
        messagePane.setVisible(true);
        messageLabel.setText(message);
    }

    public void closeMessageBox() {
        messagePane.setVisible(false);
        Platform.exit();
    }
    //ZAMBO END


    public void updateGameView() {
        updateDashboard();
        updateMessagePhase();
        updateIslands();
        updateClouds();
        updateCurrentCards();

        if(client.getMatchView() instanceof ExpertMatch) {
            updateFigureCards();
            if(client.getActualToDoChoice() instanceof MinstrelChoice && client.getMatchView().showCurrentPlayer().equals(client.getPlayer())){
                int countStudentsDr = 0;
                for(Color c : Color.values()){
                    try {
                        countStudentsDr += client.getMatchView().showCurrentPlayerDashboard().getStudentsNumInDR(c);
                    } catch (WrongColorException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(countStudentsDr == 0){
                    ((MinstrelChoice)client.getActualToDoChoice()).setCompleted(true);
                    synchronized (client.getOutputStreamLock()) {
                        client.getOutputStreamLock().notifyAll();
                    }
                }
                else {
                    if(countStudentsDr == 1)
                        maxNumMinistrelStudents = countStudentsDr;
                    else
                        maxNumMinistrelStudents = 2;
                }
            }
        }

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
                for(int z = 0; z<4; z++){
                    studentsOcClouds.get(i).get(z).setVisible(false);
                }
            }
            j=0;
            i++;
        }
        for(int z = client.getMatchView().getClouds().size(); z < clouds.size();z++){
            clouds.get(z).setVisible(false);
            for(int p = 0; p<studentsOcClouds.get(z).size(); p++){
                studentsOcClouds.get(z).get(p).setVisible(false);
            }
        }
            if(client.getMatchView().getClouds().size() == 2 || client.getMatchView().getClouds().size() == 4){
                for(int z = 0; z < clouds.size();z++){
                        studentsOcClouds.get(z).get(3).setVisible(false);
                }
            }
    }

    private void updateIslands() {
        for (Island island : client.getMatchView().getIslands()){
            if(island.checkIsMotherNature()){
                motherNatureList.get(island.getPosition()).setVisible(true);
            }
            else{
                motherNatureList.get(island.getPosition()).setVisible(false);
            }
            if(!client.getMatchView().getIslandPositions().contains(Integer.valueOf(island.getPosition()))){
                islandsImages.get(island.getPosition()).setVisible(false);
                for(Color c : Color.values()){
                    studentImagesOnIslands.get(island.getPosition()).get(c).setVisible(false);
                    numStudentsOnIsland.get(island.getPosition()).get(c).setVisible(false);
                }
                towerOnIslands.get(island.getPosition()).setVisible(false);
                towerNumOnIslands.get(island.getPosition()).setVisible(false);
            }
            else{
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


}