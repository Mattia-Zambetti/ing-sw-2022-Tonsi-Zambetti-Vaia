package view.choice;

public class MoveMotherNatureChoice extends Choice{
    private int movement;

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    @Override
    public boolean setChoiceParam(String input) {
        setMovement(Integer.parseInt(input));
        return false;
    }
}
