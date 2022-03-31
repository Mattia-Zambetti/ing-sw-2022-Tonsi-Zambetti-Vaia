//Tonsi
package model;

public class Player implements Cloneable{
    private String nickname;

    private static int totplayersnumber= 0;

    private int playerNumber;

    private Player buddy; //assumo che, in caso, gli venga passato valore null dal controller se non c'è

    //private int coin; //inizialmente sempre a 1

    //private boolean isknight; //per effetto carta personaggio

    public Player(String nickname, Player buddy){
        totplayersnumber++;
        this.nickname=nickname;
        this.playerNumber= totplayersnumber;
        this.buddy= new Player(buddy);
        //coin=1;
        //isknight=false;
    }

    public Player (Player p){
        this.buddy.nickname=p.nickname;
        this.buddy.playerNumber=p.playerNumber;
    }

    public void setBuddy(Player buddy){
        this.buddy= new Player(buddy);
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

    /*public void addCoin(){
        coin++;
    }
    public int getCoins() {
        return coin;
    }*/

    public String getNickname() {
        return nickname;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    /*public void setKnight(boolean setValue){
        isknight=setValue;
    }
    public boolean isknight() {
        return isknight;
    }*/

    public Player getBuddy(){
            return new Player(buddy);
    }
}

