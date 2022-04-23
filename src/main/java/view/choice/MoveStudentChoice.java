package view.choice;

import model.Student;

public abstract class MoveStudentChoice implements Choice{
    private Student chosenStudent;

    public void setChosenStudent(Student chosenStudent) {
        this.chosenStudent = chosenStudent;
    }

    public Student getChosenStudent() {
        return chosenStudent;
    }
}
