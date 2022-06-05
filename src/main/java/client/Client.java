package client;

import model.Player;

import java.io.IOException;

public interface Client {
    Thread readingFromSocket() throws IOException;

    void printToScreen(String s );

    void closeConnection();

    Player getPlayer();

    void setPlayer(Player player);

    int getIdThis();

    void setIdThis(int id);
}
