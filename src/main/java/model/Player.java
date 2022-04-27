//Tonsi
package model;

public class Player{

    private final String nickname;


    private Player buddy;

    public Player(String nickname) {
        this.nickname = nickname;
    }


    public void setBuddy(Player buddy){
        this.buddy= buddy;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNicknameBuddy(){
        return buddy.getNickname();
    }

}

