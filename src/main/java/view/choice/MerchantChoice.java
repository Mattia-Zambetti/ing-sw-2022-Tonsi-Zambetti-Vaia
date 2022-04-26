package view.choice;

public class MerchantChoice extends FigureCardWithStudentsChoice {
    private int chosenIslandID;

    public int getChosenIslandID() {
        return chosenIslandID;
    }

    public void setChosenIslandID(int chosenIslandID) {
        this.chosenIslandID = chosenIslandID;
    }

    @Override
    public boolean setChoiceParam(String input) {
        return false;
    }
}
