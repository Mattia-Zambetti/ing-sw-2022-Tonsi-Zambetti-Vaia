package controller.choice;

import model.ExpertMatch;
import model.Match;
import model.MatchDataInterface;
import model.Student;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.FigureCardWithStudents;

public class MerchantChoice extends FigureCardWithStudentsChoice {
    private int chosenIslandID, numChoice = 0, islandPositionSize;

    public MerchantChoice(FigureCardWithStudents figureCardWithStudents) {
        super(figureCardWithStudents);
    }

    public int getChosenIslandID() {
        return chosenIslandID;
    }

    /*public void setChosenIslandID(int chosenIslandID) {
        this.chosenIslandID = chosenIslandID;
    }*/ //NEVER USED

    @Override
    public boolean setChoiceParam(String input) {
        switch (numChoice){
            case 0:
                if(isItAnInt(input)){
                    chosenStudent = Integer.parseInt(input) - 1;
                    try{
                        setChosenStudent();
                    }catch (IndexOutOfBoundsException e) {
                        System.out.println(getRedString("No student found at that index try again:"));
                        return true;
                    }
                    numChoice++;
                    System.out.println("Insert the island position where you want to put it on:");
                }
                return true;

            case 1:
                if(isItAnInt(input)){
                    chosenIslandID = Integer.parseInt(input);
                    if(chosenIslandID < 0 && chosenIslandID >= islandPositionSize) {
                        System.out.println(getRedString("Island not found, please try again:"));
                        return true;
                    }
                    completed = true;
                    return false;
                }
                return true;
        }
        completed = true;
        return false;
    }

    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoIslandException {
        ((ExpertMatch)match).takeStudentsOnFigureCard(this.getChosenStudent(),this.getChosenIslandID());
    }

    public String toString(){return "";}
    public String toString(MatchDataInterface match){
        StringBuilder tmp = new StringBuilder();
        islandPositionSize = match.getIslandPositions().size();
        int counter = 1;
        switch (numChoice){
            case 0:
                tmp.append("Choose the student you want to move: ");
                for (Student s : figureCardWithStudents.getStudentsOnCard()){
                    tmp.append("\n" +counter + ") "+ s.toString());
                    counter++;
                }
                break;
            case 1:
                tmp.append("Choose the island where you want to put it on:");
                for (int i = 0; i < match.getIslandPositions().size(); i++)
                    tmp.append("\n" + i + ") Island");
        }
        return tmp.toString();
    }

    public String whichChoicePhase(){
        return "Merchant played from the current player";
    }

    public int getNumChoice() {
        return numChoice;
    }

    public void setIslandPositionSize(int islandPositionSize) {
        this.islandPositionSize = islandPositionSize;
    }
}
