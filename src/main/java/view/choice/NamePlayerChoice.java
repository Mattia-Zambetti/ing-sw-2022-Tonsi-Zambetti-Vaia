package view.choice;

import model.Match;
import model.Player;
import model.exception.*;
import model.figureCards.*;
import view.RemoteView;

import java.util.HashSet;
import java.util.Set;

public class NamePlayerChoice extends Choice{

    private String name;
    Set<String> nicknamesAlreadyChosen;

    public NamePlayerChoice(Set<String> nicknames){
        nicknamesAlreadyChosen=new HashSet<>(nicknames);
    }
    @Override
    public boolean setChoiceParam(String input) {
        if (nicknamesAlreadyChosen.contains(input)) {
            System.out.println("Nickname already chosen, please try again:");
            return true;
        }
        name = input;
        return false;
    }

    public Player getPlayer(){
        return new Player(name);
    }

    @Override
    public void manageUpdate(Match match, RemoteView remoteView) throws NoMoreCardException, CardNotFoundException, WrongCloudNumberException, MaxNumberException, InsufficientCoinException, NoMoreStudentsException, StudentIDAlreadyExistingException, InexistentStudentException, WrongColorException, NoIslandException, SameInfluenceException {
        //NON UTILIZZATO
    }

    @Override
    public String toString(){
        return  "Insert your name: ";
    }
}
