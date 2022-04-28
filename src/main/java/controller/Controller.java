package controller;

import model.ExpertMatch;
import model.Match;
import model.Player;
import model.exception.*;
import model.figureCards.*;
import view.RemoteView;
import view.choice.*;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
    private final Match match;
    private final HashMap<Player,RemoteView> remoteViewMap;
    private static final int NUMSTUDENTSMOVE=3;
    private final boolean isExpertMatch;

    public Controller(Match match, List<RemoteView> remoteViewList, boolean isExpertMatch){
        this.match=match;
        this.remoteViewMap=new HashMap<>();
        for (RemoteView view: remoteViewList) {
            remoteViewMap.put(view.getPlayer(),view);
        }
        this.isExpertMatch=isExpertMatch;
    }

    public void startMatch(){
        Choice actualChoice;
        try{
            while(true){
                for (Player p: remoteViewMap.keySet()) {
                    actualChoice = new DataPlayerChoice(match.getTotalPlayersNum(), p.getNickname());
                    remoteViewMap.get(p).choiceUser(actualChoice);

                }


                match.refillClouds();
                do{
                    actualChoice=new CardChoice(match.showCards());
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);
                }while(match.setNextCurrDashboard());
                do{
                    for(int i=0; i<NUMSTUDENTSMOVE; i++) {
                        actualChoice = new MoveStudentChoice(match.showCurrentPlayerDashboard().showEntrance());
                        remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);
                    }

                    match.checkAndMoveMasters();

                    if(isExpertMatch) {
                        actualChoice = new FigureCardPlayedChoice(((ExpertMatch) match).showFigureCardsInGame());
                        remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);
                    }

                    actualChoice=new MoveMotherNatureChoice();
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);

                    actualChoice=new CloudChoice();
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(actualChoice);
                }while(match.setNextCurrDashboard());
            }
        } catch (NoMasterException e) {
            e.printStackTrace();//TODO Penso funzioni così...?
            System.out.println(e.getMessage());
        } catch (WrongColorException e) {
            e.printStackTrace();
        } catch (NoMoreStudentsException e) {
            remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(e.getMessage())));
            //e.printStackTrace();//TODO CONDIZIONE END MATCH
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RemoteView) {
            try {
                ((Choice) arg).manageUpdate(match, remoteViewMap.get(match.showCurrentPlayer()));
            } catch (CardNotFoundException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                remoteViewMap.get(match.showCurrentPlayer()).choiceUser((Choice) arg);
            } catch (NoMoreCardException e) {
                remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(e.getMessage())));
            } catch (WrongCloudNumberException e) {
                e.printStackTrace();
            } catch (MaxNumberException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                //remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                //remoteViewMap.get(match.showCurrentPlayer()).choiceUser((Choice)arg);

            } catch (InsufficientCoinException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError("You don't have enough coins");
            } catch (InexistentStudentException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                remoteViewMap.get(match.showCurrentPlayer()).choiceUser((Choice) arg);//TODO FUNZIONERA'?
            } catch (StudentIDAlreadyExistingException e) {
                e.printStackTrace();
                //remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                //Choice choice=new JesterChoice();
                //remoteViewMap.get(match.showCurrentPlayer()).choiceUser(choice);
            } catch (WrongColorException e) {//TODO
                e.printStackTrace();
            } catch (NoMoreStudentsException e) {//TODO
                remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(e.getMessage())));
                //e.printStackTrace();
            } catch (NoIslandException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                remoteViewMap.get(match.showCurrentPlayer()).choiceUser((Choice) arg);
                //e.printStackTrace();//TODO
            } catch (SameInfluenceException e) {
                remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
            } catch (WrongDataplayerException e) {
                e.printStackTrace();
            } catch (NoMoreBlockCardsException e) {
                e.printStackTrace();
            } catch (FigureCardAlreadyPlayedInThisTurnException e) {
                e.printStackTrace();
            }
        }
    }
}


/*
}else if(arg instanceof MoveStudentChoice){
                switch (((MoveStudentChoice) arg).getWhereToMove().toUpperCase()) {
                    case "DINING ROOM":
                        match.moveStudentFromEntranceToDR(((MoveStudentChoice) arg).getChosenStudent());
                        break;
                    case "ISLAND":
                        try {
                            match.moveStudentFromEntranceToIsland(((MoveStudentChoice) arg).getChosenStudent(), ((MoveStudentChoice) arg).getIslandID());
                        } catch (NoIslandException e) {
                            remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                            Choice choice=new MoveStudentChoice(match.showCurrentPlayerDashboard().showEntrance());
                            remoteViewMap.get(match.showCurrentPlayer()).choiceUser(choice);
                        }
                        break;
                    default:
                        remoteViewMap.get(match.showCurrentPlayer()).sendError("Choose between Island or Dining Room");
                        Choice choice=new MoveStudentChoice(match.showCurrentPlayerDashboard().showEntrance());
                        remoteViewMap.get(match.showCurrentPlayer()).choiceUser(choice);
                }
            }

            //ONLY EXPERT MATCH:
            else if(arg instanceof FigureCardPlayedChoice){
                try {
                    ((ExpertMatch)match).playFigureCard(((FigureCardPlayedChoice) arg).getChosenFigureCard());
                } catch (CardNotFoundException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError("This card isn't in this match, try again");
                } catch (FigureCardAlreadyPlayedInThisTurnException e) {//TODO siamo sicuri che serva?
                    remoteViewMap.get(match.showCurrentPlayer()).sendError("You have already played this card in this turn");
                } catch (InsufficientCoinException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError("You don't have enough coins");
                }
            }else if(arg instanceof MerchantChoice){
                try {
                    ((ExpertMatch)match).takeStudentsOnFigureCard(((MerchantChoice) arg).getChosenStudents(), (Merchant) ((MerchantChoice) arg).getFigureCardPlayed(),((MerchantChoice) arg).getChosenIslandID());
                } catch (MaxNumberException | InexistentStudentException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                    Choice choice=new MerchantChoice();
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(choice);
                } catch (StudentIDAlreadyExistingException e) {
                    e.printStackTrace();
                } catch (WrongColorException e) {//TODO
                    e.printStackTrace();
                } catch (NoMoreStudentsException e) {//TODO
                    remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(e.getMessage())));
                    //e.printStackTrace();
                }
            }else if(arg instanceof JesterChoice){
                try {
                    ((ExpertMatch)match).takeStudentsOnFigureCard(((JesterChoice) arg).getChosenStudents(),(Jester) ((JesterChoice) arg).getFigureCardPlayed(), ((JesterChoice) arg).getStudentsFromEntrance());
                } catch (MaxNumberException |StudentIDAlreadyExistingException |InexistentStudentException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                    Choice choice=new JesterChoice();
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(choice);
                } catch (NoMoreStudentsException e) {//TODO
                    remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(e.getMessage())));
                    //e.printStackTrace();
                }
            }else if(arg instanceof PrincessChoice){
                try {
                    ((ExpertMatch)match).takeStudentsOnFigureCard(((PrincessChoice) arg).getChosenStudents(),(Princess) ((PrincessChoice) arg).getFigureCardPlayed());
                } catch (MaxNumberException |StudentIDAlreadyExistingException |InexistentStudentException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                    Choice choice=new JesterChoice();
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(choice);
                } catch (NoMoreStudentsException e) {//TODO
                    remoteViewMap.forEach(((player, remoteView) -> remoteView.sendError(e.getMessage())));
                    //e.printStackTrace();
                } catch (WrongColorException e) {//TODO QUI?
                    e.printStackTrace();
                }
            }else if(arg instanceof MushroomCollectorChoice){
                ((ExpertMatch)match).blockColorForInfluence(((MushroomCollectorChoice) arg).getBlockedColor());
            }else if(arg instanceof GrannyGrassChoice){
                try {
                    ((ExpertMatch)match).placeForbiddenCards(((GrannyGrassChoice) arg).getBlockedIslanID());
                } catch (NoIslandException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                    Choice choice=new GrannyGrassChoice();
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(choice);
                } catch (NoMoreBlockCardsException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                    //e.printStackTrace();//TODO
                }
            }
            //END EXPERT MATCH

            else if(arg instanceof MoveMotherNatureChoice)
            {
                try {
                    match.moveMotherNature(((MoveMotherNatureChoice) arg).getMovement());
                } catch (NoIslandException | MaxNumberException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                    Choice newChoice=new MoveMotherNatureChoice();
                    remoteViewMap.get(match.showCurrentPlayer()).choiceUser(newChoice);
                } catch (SameInfluenceException | NoMoreBlockCardsException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                }
            }else if(arg instanceof CloudChoice){
                try {
                    match.moveStudentsFromCloudToEntrance(((CloudChoice) arg).getChosenCloudID());
                } catch (WrongCloudNumberException e) {
                    e.printStackTrace();
                } catch (MaxNumberException e) {
                    remoteViewMap.get(match.showCurrentPlayer()).sendError(e.getMessage());
                }
            }

 */