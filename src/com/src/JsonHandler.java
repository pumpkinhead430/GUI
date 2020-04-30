package com.src;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class JsonHandler {
    private final String fileName;
    private JSONObject jsonObject;

    public JsonHandler(String fileName){
        this.fileName = fileName;
        JSONParser parser = new JSONParser();
        try{
            System.out.println(this.fileName);
            Reader reader = new FileReader(this.fileName);

            this.jsonObject = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void Write(){
        try (FileWriter file = new FileWriter(this.fileName)) {
            file.write(this.jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void SetObject(JSONObject object){
        this.jsonObject = object;
    }
    public JSONObject GetObject(){
        return jsonObject;
    }
}
