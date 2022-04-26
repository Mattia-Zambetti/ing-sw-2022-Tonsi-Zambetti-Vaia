package view.choice;

public class StartingMatchChoice extends Choice{
    private int totalPlayersNumMatch;
    private int matchType;
    private int numChoice=0;

    public int getMatchType() {
        return matchType;
    }

    public int getNumChoice() {
        return numChoice;
    }

    public void choicePlusPlus(){
        numChoice++;
    }

    public void setMatchType(int matchType) {
        this.matchType = matchType;
    }

    public int getTotalPlayersNumMatch() {
        return totalPlayersNumMatch;
    }

    public void setTotalPlayersNumMatch(int totalPlayersNumMatch) {
        this.totalPlayersNumMatch = totalPlayersNumMatch;
    }

    @Override
    public String toString() {
        return "You are the first player, choose the number of players(from 2 to 4) and the type of match(1 for normal match, 2 for expert match): ";
    }

    @Override
    public boolean setChoiceParam(String input) {
        switch (getNumChoice()) {
            case 0:
                setTotalPlayersNumMatch(Integer.parseInt(input));
                choicePlusPlus();
                System.out.println("Insert the second parameter: ");
                return true;

            case 1:
                setMatchType(Integer.parseInt(input));
                System.out.println("Waiting other players... ");
                return false;

        }
        return false;
    }
}
