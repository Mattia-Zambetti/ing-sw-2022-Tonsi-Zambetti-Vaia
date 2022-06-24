package model;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {

    private final String nickname;
    private Player buddy;


    public Player(String nickname) {
        this.nickname = nickname;
    }

    /**It sets a player as a buddy of this player in a 4 players match*/
    public void setBuddy(Player buddy){
        this.buddy= buddy;
    }

    /**It returns the nickname of this player*/
    public String getNickname() {
        return nickname;
    }

    /**It returns the buddy nickname of this player in a 4 players match*/
    public String getNicknameBuddy(){
        return buddy.getNickname();
    }

    /**it returns true if the Object o is a player and if the players has
     *the same nickname*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(nickname, player.nickname);
    }

}

