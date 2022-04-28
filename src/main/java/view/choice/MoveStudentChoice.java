package view.choice;

import model.Match;
import model.Student;
import model.exception.*;
import view.RemoteView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MoveStudentChoice extends Choice{
    private int chosenStudent;
    private int islandID;
    private String whereToMove;
    private int numChoice=0;
    private Map<Integer,Student> studentsOnEntrance;

    public MoveStudentChoice(Set<Student> studentsOnEntrance){
        this.studentsOnEntrance=new HashMap<>();
        for (Student s: studentsOnEntrance) {
            this.studentsOnEntrance.put(s.getID(),s);
        }
    }

    public void choiceplus(){
        numChoice++;
    }

    public int getNumChoice() {
        return numChoice;
    }

    public void setChosenStudent(int chosenStudent) {
        this.chosenStudent = chosenStudent;
    }


    public Student getChosenStudent() {
        return studentsOnEntrance.get(chosenStudent);
    }

    @Override
    public String toString() {
        return "insert what student you want to take and where do you want to put it: ";
    }


    public int getIslandID() {
        return islandID;
    }

    public void setIslandID(int islandID) {
        this.islandID = islandID;
    }

    public void setWhereToMove(String whereToMove) {
        this.whereToMove = whereToMove;
    }

    public String getWhereToMove() {
        return whereToMove;
    }

    @Override
    public boolean setChoiceParam(String input) {
        switch (getNumChoice()) {
            case 0:
                setWhereToMove(input);
                choiceplus();
                break;
            case 1:
                if(isItAnInt(input)) {
                    setChosenStudent(Integer.parseInt(input));
                    choiceplus();
                    if (getWhereToMove().equals("dining room")) {
                        return false;
                    }
                }
                break;

            case 2:
                if(isItAnInt(input)) {
                    setIslandID(Integer.parseInt(input));
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match, RemoteView remoteView) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException {
        switch (this.getWhereToMove().toUpperCase()) {
            case "DINING ROOM":
                match.moveStudentFromEntranceToDR(this.getChosenStudent());
                break;
            case "ISLAND":
                try {
                    match.moveStudentFromEntranceToIsland(this.getChosenStudent(), this.getIslandID());
                } catch (NoIslandException e) {
                    remoteView.sendError(e.getMessage());
                    Choice choice=new MoveStudentChoice(match.showCurrentPlayerDashboard().showEntrance());
                    remoteView.choiceUser(choice);
                }
                break;
            default:
                remoteView.sendError("Choose between Island or Dining Room");
                Choice choice=new MoveStudentChoice(match.showCurrentPlayerDashboard().showEntrance());
                remoteView.choiceUser(choice);
        }
    }
}
