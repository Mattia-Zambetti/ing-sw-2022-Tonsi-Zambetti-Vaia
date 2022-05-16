package controller.choice;

import model.ExpertMatch;
import model.Match;
import model.Student;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.Merchant;
import model.figureCards.Princess;

import java.util.List;

public class PrincessChoice extends FigureCardWithStudentsChoice{
    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)){
            chosenStudent = Integer.parseInt(input) -1;
            if(chosenStudent < 0 && chosenStudent >= Princess.getStudentsOnCard().size())
                return false;
            else
                System.out.println("Student not found, please try again:");
            return true;
        }
        return false;
    }

    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException {
        ((ExpertMatch)match).takeStudentsOnFigureCard(this.getChosenStudent());
    }

    public String toString(){
        StringBuilder tmp = new StringBuilder();
        int counter = 1;
                tmp.append("Choose the student you want to move to your Dining Room: ");
                for (Student s : Princess.getStudentsOnCard())
                    tmp.append("\n" +counter + ") "+ s.toString());
        return tmp.toString();
    }
}
