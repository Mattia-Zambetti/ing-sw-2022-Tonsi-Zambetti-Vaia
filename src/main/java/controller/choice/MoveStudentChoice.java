package controller.choice;

import model.Match;
import model.Student;
import model.exception.*;

import java.util.*;

public class MoveStudentChoice extends Choice{
    private int chosenStudent;
    private int islandID;
    private int whereToMove;
    private int numChoice=0;
    private List<Student> studentsOnEntrance;
    private final String[] possiblePlace={"DINING ROOM", "ISLAND"};




    public MoveStudentChoice(Set<Student> studentsOnEntrance){
        this.studentsOnEntrance=new ArrayList<>();
        this.studentsOnEntrance.addAll(studentsOnEntrance);

    }


    public void setStudentsOnEntrance(Set<Student> studentsOnEntrance) {
        this.studentsOnEntrance = new ArrayList<>(studentsOnEntrance);
    }

    public void choiceplus(){
        numChoice++;
    }

    public int getNumChoice() {
        return numChoice;
    }

    public boolean setChosenStudent(int chosenStudent) {
        try{
            studentsOnEntrance.get(chosenStudent-1);
            this.chosenStudent = chosenStudent;
            return true;
        }catch (IndexOutOfBoundsException e) {
            return false;
        }
    }


    public Student getChosenStudent() {
        return studentsOnEntrance.get(chosenStudent-1);
    }

    @Override
    public String toString() {
        switch (numChoice){
            case 0:
                StringBuilder stringBuilder=new StringBuilder("Insert which student you want to take:\n");
                for (int i=0; i< studentsOnEntrance.size(); i++) {
                    stringBuilder.append((i+1)+". "+studentsOnEntrance.get(i).toString()+"\n");
                }
                return stringBuilder.toString();
            case 1:
                return toStringWhereToMove();
            case 2:
                return "Insert on which island you want to put the "+ studentsOnEntrance.get(chosenStudent-1);
        }
        return "error";
    }


    public int getIslandID() {
        return islandID;
    }

    public void setIslandID(int islandID) {
        this.islandID = islandID;
    }

    public boolean setWhereToMove(int whereToMove) {
        try {
            String string=possiblePlace[whereToMove-1];
            this.whereToMove=whereToMove;
            return true;
        }catch (IndexOutOfBoundsException e){
            System.out.println("You must choose one of these options, "+retryMessage());
            return false;
        }
    }

    public String getWhereToMove() {
        return possiblePlace[whereToMove-1];
    }

    @Override
    public boolean setChoiceParam(String input) {
        if(isItAnInt(input)) {
            switch (getNumChoice()) {
                case 0:
                    if (setChosenStudent(Integer.parseInt(input))) {
                        choiceplus();
                    } else System.out.println("You must choose one of these options, " + retryMessage());
                    break;
                case 1:
                    if (setWhereToMove(Integer.parseInt(input))) {
                        choiceplus();
                        if (possiblePlace[0].equals(getWhereToMove())) {
                            System.out.println("You choose to move in " + possiblePlace[whereToMove - 1] + " the " + studentsOnEntrance.get(chosenStudent - 1));
                            return false;
                        }

                    }
                    break;

                case 2:
                    setIslandID(Integer.parseInt(input));
                    return false;
            }
        }
        return true;
    }

    @Override
    public void manageUpdate(Match match) throws CardNotFoundException, WrongCloudNumberException, MaxNumberException, NoMasterException, WrongColorException, NoIslandException {
        switch (this.getWhereToMove().toUpperCase()) {
            case "DINING ROOM":
                match.moveStudentFromEntranceToDR(this.getChosenStudent());
                break;
            case "ISLAND":
                match.moveStudentFromEntranceToIsland(this.getChosenStudent(), this.getIslandID());
                /*try {
                    match.moveStudentFromEntranceToIsland(this.getChosenStudent(), this.getIslandID());
                } catch (NoIslandException e) {
                    remoteView.sendError(e.getMessage());
                    Choice choice=new MoveStudentChoice(match.showCurrentPlayerDashboard().showEntrance());
                    remoteView.choiceUser(choice);
                }*/
                break;
            /*default:
                remoteView.sendError("Choose between Island or Dining Room");
                Choice choice=new MoveStudentChoice(match.showCurrentPlayerDashboard().showEntrance());
                remoteView.choiceUser(choice);*/
        }
    }

    public String toStringWhereToMove(){
        System.out.println("Where do you want to put it?");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i< possiblePlace.length; i++) {
            stringBuilder.append(i + 1).append(". ").append(possiblePlace[i]).append("\n");
        }
        return stringBuilder.toString();
    }



}
