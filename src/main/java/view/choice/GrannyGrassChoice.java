package view.choice;

public class GrannyGrassChoice extends FigureCardActionChoice{
    private int blockedIslanID;

    public int getBlockedIslanID() {
        return blockedIslanID;
    }

    public void setBlockedIslanID(int blockedIslanID) {
        this.blockedIslanID = blockedIslanID;
    }

    @Override
    public boolean setChoiceParam(String input) {
        return false;
    }
}
