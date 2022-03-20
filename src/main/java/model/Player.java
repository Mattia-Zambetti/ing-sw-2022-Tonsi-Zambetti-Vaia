//Tonsi
package model;

import java.util.HashSet;
import java.util.Set;

public class Player{
    private String nickname;

    private int playerNumber;

    private Player buddy; //assumo che, in caso, gli venga passato valore null dal controller se non c'è

    private Set<Professor> professors; //scelto di implementare set in quanto non ci interessa l'ordine dei professori,
                                        // è possibile scorrerli con un iterator

    private int coin; //inizialmente sempre a 1

    private Dashboard dashboard;

    private Deck deck;

    private boolean isknight; //per effetto carta personaggio

    public Player(String nickname, int playerNumber, Player buddy, Deck deck){
        this.nickname=nickname;
        this.playerNumber= playerNumber;
        this.buddy= buddy;
        professors=new HashSet<>(); //pare essere il tipo di implementazione più efficiente
        coin=1;
        dashboard= new Dashboard(); //da correggere nel momento dell'effettiva creazione della classe Dashboard
        isknight=false;
        this.deck= deck;
    }

    //passo copia in quanto dispongo addProfessor e removeProfessor per aggiungerne uno, e non sono
    // necessarie altre modifiche
    public Set<Professor> getProfessors() {
        return new HashSet<>(professors);
    }

    //In questo metodo, se è già posseduto, rimuovo il professor dal possessore, metto l'owner in professor pari al nuovo
    // possessore(questo stesso oggetto) e aggiungo il professor al set
    public void moveProfessor(Professor professor){

        if(!professor.getIfFree()) {
            professor.getOwner().removeProfessor(professor);
            professor.setOwner(this);
        }
        else
            professor.setIsFree(false);

        professors.add(professor);



    }

    //per ora io penso che non sia possibile che un professore, una volta posseduto da
    // qualcuno in un match, possa tornare ad essere libero
    private void removeProfessor(Professor professor){
        professors.remove(professor);
    }

    public void addCoin(){
        coin++;
    }
    public int getCoins() {
        return coin;
    }

    public Dashboard getDashboard() {
        return dashboard;
    } //gli passo apposta il riferimento, in modo che Match possa
        // interagire e modificare la Dashboard

    public String getNickname() {
        return nickname;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Player getBuddy() {
        return buddy;
    }

    public void setKnight(boolean setValue){
        isknight=setValue;
    }
    public boolean isknight() {
        return isknight;
    }

    public Deck getDeck() {
        return deck;
    }
}

