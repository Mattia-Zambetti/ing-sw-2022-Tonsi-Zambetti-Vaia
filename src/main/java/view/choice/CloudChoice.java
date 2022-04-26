package view.choice;

import model.Cloud;

import java.util.ArrayList;
import java.util.List;

public class CloudChoice extends Choice{
    private int chosenCloudID;
    private final List<Cloud> actualClouds;
    public CloudChoice(List<Cloud> clouds){
        actualClouds=new ArrayList<>(clouds);
    }
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

    @Override
    public boolean setChoiceParam(String input) {
        setChosenCloudID(Integer.parseInt(input));
        return false;
    }
}
