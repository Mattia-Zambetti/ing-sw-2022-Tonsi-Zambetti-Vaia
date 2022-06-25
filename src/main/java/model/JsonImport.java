package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class JsonImport implements Serializable {
    private final String filename;

    public JsonImport(String filename){
        this.filename=filename;
    }

    /**It allows to import and create cards from a file json. It throws FileNotFoundException if there's no file with "filename" name
     * in the project*/
    public Set<Card> createCards() {
       /* HashSet<Card> res = new HashSet<>();
        Card tmpCard;
        int value, movementValue, id;
        try {
            JsonArray reader = JsonParser.parseReader(new FileReader(filename)).getAsJsonArray();
            for (JsonElement j : reader) {
                JsonObject tmp = (JsonObject) j;
                value = tmp.get("value").getAsInt();
                movementValue = tmp.get("movement value").getAsInt();
                id = tmp.get("id").getAsInt();
                tmpCard = new Card(value, movementValue, id);
                res.add(tmpCard);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        return null;
    }

}
