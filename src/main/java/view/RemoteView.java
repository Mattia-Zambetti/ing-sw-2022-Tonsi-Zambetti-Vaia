package view;

import model.FigureCards.FigureCardWithStudents;
import model.Player;
import utils.Observable;
import utils.Observer;
import view.choice.Choice;

public class RemoteView extends Observable<Choice> implements Observer {
    Player player;


    @Override
    public void update(Object message) {
        if(message instanceof FigureCardWithStudents){
            studentsRequest((FigureCardWithStudents) message);
        }
    }

    private void studentsRequest(FigureCardWithStudents message){

    }
}
