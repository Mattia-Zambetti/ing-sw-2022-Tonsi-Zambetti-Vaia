package view.choice;

import model.ExpertMatch;
import model.Match;
import model.Student;
import model.exception.*;
import model.figureCards.FigureCardAlreadyPlayedInThisTurnException;
import model.figureCards.Jester;

import java.util.HashSet;
import java.util.Set;

public class JesterChoice extends FigureCardWithStudentsChoice {

    private Set<Student> studentsFromEntrance;

    public Set<Student> getStudentsFromEntrance() {
        return new HashSet<>(studentsFromEntrance);
    }

    public void setStudentsFromEntrance(Set<Student> studentsFromEntrance) {
        this.studentsFromEntrance = new HashSet<>(studentsFromEntrance);
    }

    @Override
    public boolean setChoiceParam(String input) {
        //TODO
        return false;
    }

    @Override
    public void manageUpdate(Match match) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, FigureCardAlreadyPlayedInThisTurnException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException {
        ((ExpertMatch)match).takeStudentsOnFigureCard( this.getChosenStudent(),(Jester) this.getFigureCardPlayed(), this.getStudentsFromEntrance());
    }
}

