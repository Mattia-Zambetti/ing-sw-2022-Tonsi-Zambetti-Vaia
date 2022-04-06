//Tonsi
package model;

public class Player{
    private String nickname;

    private static int totplayersnumber= 0;

    private int playerNumber;

    private Player buddy; //assumo che, in caso, gli venga passato valore null dal controller se non c'è



    public Player(String nickname, Player buddy){
        totplayersnumber++;
        this.nickname=nickname;
        this.playerNumber= totplayersnumber;
        this.buddy= new Player(buddy);
    }

    //TODO
    public Player (Player p){
        this.nickname=p.nickname;
        this.playerNumber=p.playerNumber;
    }

    public void setBuddy(Player buddy){
        this.buddy= new Player(buddy);
    }

    public String getNickname() {
        return nickname;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Player getBuddy(){
        return new Player(buddy);
    }
}

