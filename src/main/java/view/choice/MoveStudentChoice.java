package view.choice;

public class MoveStudentChoice implements Choice{
    private int chosenStudent;
    private int islandID;
    private String whereToMove;
    private int numChoice=0;

    public void choiceplus(){
        numChoice++;
    }

    public int getNumChoice() {
        return numChoice;
    }

    public void setChosenStudent(int chosenStudent) {
        this.chosenStudent = chosenStudent;
    }


    public int getChosenStudent() {
        return chosenStudent;
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
}
