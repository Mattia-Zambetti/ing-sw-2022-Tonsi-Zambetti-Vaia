//Tonsi
package model;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(nickname, player.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }


}

