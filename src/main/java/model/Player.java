//Tonsi
package model;

public class Player implements Cloneable{
    private String nickname;

    private int playerNumber;

    private Player buddy; //assumo che, in caso, gli venga passato valore null dal controller se non c'è

    private int coin; //inizialmente sempre a 1

    private boolean isknight; //per effetto carta personaggio

    public Player(String nickname, int playerNumber, Player buddy){
        this.nickname=nickname;
        this.playerNumber= playerNumber;
        this.buddy= buddy;
        coin=1;
        isknight=false;


    }

    public Object clone(){
        try{
            return (Player)super.clone();
        }
        catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }

    public void addCoin(){
        coin++;
    }
    public int getCoins() {
        return coin;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setKnight(boolean setValue){
        isknight=setValue;
    }
    public boolean isknight() {
        return isknight;
    }

    public Player getBuddy(){
            return (Player)buddy.clone();
    }
}

