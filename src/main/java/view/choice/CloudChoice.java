package view.choice;

public class CloudChoice implements Choice{
    private int chosenCloudID;

    public int getChosenCloudID() {
        return chosenCloudID;
    }

    public void setChosenCloudID(int chosenCloudID) {
        this.chosenCloudID = chosenCloudID;
    }

    @Override
    public String toString() {
        return "Insert cloud number you want to take: ";
    }
}
