package controller.choice;

import model.ExpertMatch;
import model.Match;
import model.MatchDataInterface;
import model.Student;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MinstrelChoice extends Choice {

    int numStudentsToMove, numStudentsToMove1, studentToMove;
    public int numChoice = 0, numStudentsDr;

    private List<Student> studentsFromDr = new ArrayList<>();
    private List<Student> studentsFromEntrance = new ArrayList<>();
    private List<Student> studentsinDr;
    private List<Student> studentsInEntrance;

    public Set<Student> getStudentsFromEntrance() {
        return new HashSet<>(studentsFromEntrance);
    }

    @Override
    public boolean setChoiceParam(String input)
    {
        switch (numChoice){
            case 0:
                if(isItAnInt(input)){
                    numStudentsToMove1 = Integer.parseInt(input);
                    numStudentsToMove = Integer.parseInt(input);
                    if(numStudentsToMove > 0 && numStudentsToMove <= 2){
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
                        if(!studentsFromEntrance.contains(studentsInEntrance.get(studentToMove))){
                            studentsFromEntrance.add(studentsInEntrance.get(studentToMove));
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
                        if(!studentsFromDr.contains(studentsinDr.get(studentToMove))){
                            studentsFromDr.add(studentsinDr.get(studentToMove));
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
            case 3:
                completed = true;
                return false;
        }
        completed = true;
        return false;
    }


    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoMasterException {
        ((ExpertMatch)match).switchStudents(studentsFromEntrance,studentsFromDr);
    }

    public String toString(MatchDataInterface match){
        StringBuilder tmp = new StringBuilder();
        this.studentsInEntrance = match.showCurrentPlayerDashboard().showEntrance().stream().toList();
        studentsinDr =match.showCurrentPlayerDashboard().showDiningRoom();
        numStudentsDr = match.showCurrentPlayerDashboard().showDiningRoom().size();
        if(numStudentsDr == 0){
            tmp.append("You don't have any student in you dining room, i'm sorry");
            numChoice = 3;
        }
        int counter = 1;
        switch (numChoice){
            case 0:
                tmp.append("Choose how many students you want to switch (from 1 to 2): ");
                break;
            case 1:
                tmp.append("Choose the student you want to switch from your Entrance: ");
                for (Student s : studentsInEntrance){
                    if(!studentsFromEntrance.contains(s))
                        tmp.append("\n" + counter + ") "+ s.toString());
                    counter++;
                }
                break;
            case 2:
                tmp.append("Choose the student you want to switch from your Dining Room: ");
                for (Student s : studentsinDr){
                    if(!studentsFromDr.contains(s))
                        tmp.append("\n" + counter + ") "+ s.toString());
                    counter++;
                }
            case 3:
                break;
        }
        return tmp.toString();
    }

    public String whichChoicePhase(){
        return "Minstrel played from the current player";
    }
}

