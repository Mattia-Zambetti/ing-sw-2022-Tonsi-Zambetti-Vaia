//Tonsi
package model;

public class Player{

    private final String nickname;

    private final int playerNumber;

    private Player buddy;

    public Player(String nickname, int playerNumber) {
        this.nickname = nickname;
        this.playerNumber = playerNumber;
    }


    public void setBuddy(Player buddy){
        this.buddy= buddy;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getNicknameBuddy(){
        return buddy.getNickname();
    }

    public int getPlayerNumberBuddy() {
        return buddy.getPlayerNumber();
    }
}

