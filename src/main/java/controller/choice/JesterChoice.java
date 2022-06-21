package controller.choice;

import model.ExpertMatch;
import model.Match;
import model.MatchDataInterface;
import model.Student;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.FigureCardWithStudents;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JesterChoice extends FigureCardWithStudentsChoice {

    int numStudentsToMove, numStudentsToMove1, studentToMove;
    public int numChoice = 0;

    List<Student> studentsInEntrance = new ArrayList<>();
    private Set<Student> studentsFromEntrance;

    public JesterChoice(FigureCardWithStudents figureCardWithStudents) {
        super(figureCardWithStudents);
        studentsFromEntrance = new HashSet<>();
    }

    public Set<Student> getStudentsFromEntrance() {
        return new HashSet<>(studentsFromEntrance);
    }

    public void setStudentsFromEntrance(Set<Student> studentsFromEntrance) {
        this.studentsFromEntrance = new HashSet<>(studentsFromEntrance);
    }

    public int getNumChoice() {
        return numChoice;
    }

    @Override
    public boolean setChoiceParam(String input)
        {
            switch (numChoice){
                case 0:
                    if(isItAnInt(input)){
                        numStudentsToMove1 = Integer.parseInt(input);
                        numStudentsToMove = Integer.parseInt(input);
                        if(numStudentsToMove > 0 && numStudentsToMove <= 3){
                            numChoice++;
                        }
                        else
                            System.out.println(getRedString("The number must be between 1 and 3, please try again:"));
                    }
                    return true;

                case 1:
                    if(isItAnInt(input)){
                        studentToMove = Integer.parseInt(input) - 1;
                        try{
                            if(!chosenStudents.contains(figureCardWithStudents.getStudentsOnCardByInt(studentToMove))){
                                chosenStudents.add(figureCardWithStudents.getStudentsOnCardByInt(studentToMove));
                                numStudentsToMove --;
                            }
                            else
                                System.out.println(getRedString("Student already chosen, try again with another student:"));
                        }
                        catch (IndexOutOfBoundsException e) {
                            System.out.println(getRedString("No student found at that index, try again:"));
                            return true;
                        }
                        if(numStudentsToMove == 0)
                            numChoice++;
                    }
                    return true;
                case 2:
                    if(isItAnInt(input)){
                        studentToMove = Integer.parseInt(input) - 1;
                        try{
                            if(!studentsFromEntrance.contains(studentsInEntrance.get(studentToMove))){
                                studentsFromEntrance.add(studentsInEntrance.get(studentToMove));
                                numStudentsToMove1 --;
                            }
                            else
                                System.out.println(getRedString("Student already chosen, try again with another student:"));
                        }
                        catch (IndexOutOfBoundsException e) {
                            System.out.println(getRedString("No student found at that index, try again:"));
                            return true;
                        }
                        if(numStudentsToMove1 == 0){
                            completed = true;
                            return false;
                        }

                    }
                    return true;
            }
            completed = true;
            return false;
        }


    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException {
        ((ExpertMatch)match).takeStudentsOnFigureCard( this.getChosenStudent(), this.getStudentsFromEntrance());
    }

    public void setStudentsInEntrance(List<Student> studentsInEntrance) {
        this.studentsInEntrance = studentsInEntrance;
    }

    public String toString(MatchDataInterface match){
        StringBuilder tmp = new StringBuilder();
        this.studentsInEntrance.addAll(match.showCurrentPlayerDashboard().showEntrance());
        int counter = 1;
        switch (numChoice){
            case 0:
                tmp.append("Choose how many students you want to move (from 1 to 3): ");
                break;
            case 1:
                tmp.append("Choose the student you want to move: ");
                for (Student s : figureCardWithStudents.getStudentsOnCard()){
                    if(!chosenStudents.contains(s))
                        tmp.append("\n" + counter + ") "+ s.toString());
                    counter++;
                }
                break;
            case 2:
                tmp.append("Choose the student you want to take from your entrance: ");
                for (Student s : studentsInEntrance){
                    if(!studentsFromEntrance.contains(s))
                        tmp.append("\n" + counter + ") "+ s.toString());
                    counter++;
                }
        }
        return tmp.toString();
    }

    public String whichChoicePhase(){
        return "Jester played from the current player";
    }

    @Override
    public void setChosenStudent() {
        super.setChosenStudent();
    }
}

