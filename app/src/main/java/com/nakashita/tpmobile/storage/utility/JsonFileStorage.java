package com.nakashita.tpmobile.storage.utility;

import android.content.Context;

import org.json.*;

import java.util.*;

abstract public class JsonFileStorage<T> extends FileStorage<T>{

    private final static String EXTENSION = ".json";
    private final static String LIST = "list";
    private final static String NEXT_ID = "next_id";

    protected JSONObject json;

    public JsonFileStorage(Context context, String name) {
        super(context, name, EXTENSION);
    }

    protected abstract JSONObject objectToJsonObject(int id, T object);

    protected abstract T jsonObjectToObject(JSONObject jsonObject);

    @Override
    protected void create() {
        json = new JSONObject();
        try {
            json.put(LIST, new JSONObject());
            json.put(NEXT_ID, 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initialize(String value) {
        try {
            json = new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getValue() {
        return json.toString();
    }

    @Override
    public void insert(T object) {
        int nextId = json.optInt(NEXT_ID);
        try {
            json.getJSONObject(LIST).put("" + nextId, objectToJsonObject(nextId, object));
            json.put(NEXT_ID, nextId + 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        write();
    }

    @Override
    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        try {
            Iterator<String> iterator = json.getJSONObject(LIST).keys();
            while (iterator.hasNext()) {
                list.add(jsonObjectToObject(json.getJSONObject(LIST).getJSONObject(iterator.next())));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public T find(int id) {
        T object = null;
        try {
            object = jsonObjectToObject(json.getJSONObject(LIST).getJSONObject("" + id));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public int size() {
        int size = 0;
        try {
            size = json.getJSONObject(LIST).length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return size;
    }

    @Override
    public void update(int id, T object) {
        try {
            json.getJSONObject(LIST).put("" + id, objectToJsonObject(id, object));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        write();
    }

    @Override
    public void delete(int id) {
        try {
            json.getJSONObject(LIST).remove("" + id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        write();
    }
}
