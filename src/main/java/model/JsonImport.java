package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class JsonImport {
    private JsonParser parser;
    private String filename;

    public JsonImport(String filename){
        parser=new JsonParser();
        this.filename=filename;
    }

    public Set<Card> createCards() {
        HashSet<Card> res = new HashSet<>();
        Card tmpCard;
        int value, movementValue, id;
        try {
            JsonArray reader = (JsonArray) parser.parseReader(new FileReader(filename)).getAsJsonArray();
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
        }
        return res;
    }

}
