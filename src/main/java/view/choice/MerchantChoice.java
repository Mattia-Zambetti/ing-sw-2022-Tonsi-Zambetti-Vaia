package view.choice;

import model.ExpertMatch;
import model.Match;
import model.Student;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.Merchant;

public class MerchantChoice extends FigureCardWithStudentsChoice {
    private int chosenIslandID, numChoice = 0;

    public int getChosenIslandID() {
        return chosenIslandID;
    }

    public void setChosenIslandID(int chosenIslandID) {
        this.chosenIslandID = chosenIslandID;
    }

    @Override
    public boolean setChoiceParam(String input) {
        switch (numChoice){
            case 0:
                if(isItAnInt(input)){
                    chosenStudent = Integer.parseInt(input) - 1;
                    try{
                        setChosenStudent();
                    }catch (IndexOutOfBoundsException e) {
                        System.out.println("No student found at that index, try again:");
                        return true;
                    }
                    numChoice++;
                    System.out.println("Insert the island position where you want to put it on:");
                }
                return true;

            case 1:
                if(isItAnInt(input)){
                    chosenIslandID = Integer.parseInt(input);
                    //numChoice++;
                    return false;
                }
                return true;
        }

        return false;
    }

    @Override
    public void manageUpdate(Match match) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoIslandException {
        ((ExpertMatch)match).takeStudentsOnFigureCard(this.getChosenStudent(), (Merchant) this.getFigureCardPlayed(),this.getChosenIslandID());
    }

    public String toString(){
        StringBuilder tmp = new StringBuilder();
        int counter = 1;
        tmp.append("Choose the student you want to move: ");
        for (Student s : Merchant.getStudentsOnCard())
            tmp.append("\n" +counter + ") "+ s.toString());
        return tmp.toString();
    }
}
